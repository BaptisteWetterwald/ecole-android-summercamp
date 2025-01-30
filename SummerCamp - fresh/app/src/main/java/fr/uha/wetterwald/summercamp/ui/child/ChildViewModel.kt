package fr.uha.wetterwald.summercamp.ui.child

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.hassenforder.android.ui.app.UITitleBuilder
import fr.uha.hassenforder.android.ui.app.UITitleState
import fr.uha.hassenforder.android.ui.field.FieldWrapper
import fr.uha.hassenforder.android.viewmodel.Result
import fr.uha.wetterwald.summercamp.model.Child
import fr.uha.wetterwald.summercamp.model.Gender
import fr.uha.wetterwald.summercamp.model.Specialty
import fr.uha.wetterwald.summercamp.repository.ChildRepository
import fr.uha.wetterwald.summercamp.ui.supervisor.SupervisorUIValidator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildViewModel @Inject constructor (
    private val repository: ChildRepository
): ViewModel() {

    private val _id: MutableStateFlow<Long> = MutableStateFlow(0)

    private val _firstnameState = MutableStateFlow(FieldWrapper<String>())
    private val _lastnameState = MutableStateFlow(FieldWrapper<String>())
    private val _ageState = MutableStateFlow(FieldWrapper<Int>())
    private val _genderState = MutableStateFlow(FieldWrapper<Gender>())
    private val _pictureState = MutableStateFlow(FieldWrapper<Uri>())
    private val _parentPhoneState = MutableStateFlow(FieldWrapper<String>())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _initialChildState: StateFlow<Result<Child>> = _id
        .flatMapLatest { id -> repository.getChildById(id) }
        .map {
                child -> if (child != null) {
            _firstnameState.value = fieldBuilder.buildFirstname(child.firstname)
            _lastnameState.value = fieldBuilder.buildLastname(child.lastname)
            _ageState.value = fieldBuilder.buildAge(child.age)
            _genderState.value = fieldBuilder.buildGender(child.gender)
            _pictureState.value = fieldBuilder.buildPicture(child.picture)
            _parentPhoneState.value = fieldBuilder.buildParentPhone(child.parentPhone)
            Result.Success(content = child)
        } else {
            Result.Error()
        }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Result.Loading)

    @Suppress("UNCHECKED_CAST")
    data class UIState(
        val args: Array<FieldWrapper<out Any>>,
    ) {
        val firstnameState : FieldWrapper<String> = args[0] as FieldWrapper<String>
        val lastnameState : FieldWrapper<String> = args[1] as FieldWrapper<String>
        val ageState : FieldWrapper<Int> = args[2] as FieldWrapper<Int>
        val genderState : FieldWrapper<Gender> = args[3] as FieldWrapper<Gender>
        val pictureState : FieldWrapper<Uri> = args[4] as FieldWrapper<Uri>
        val parentPhoneState : FieldWrapper<String> = args[5] as FieldWrapper<String>
    }

    val uiState : StateFlow<Result<UIState>> = combine(
        _firstnameState, _lastnameState, _ageState, _genderState, _pictureState, _parentPhoneState
    ) { args : Array<FieldWrapper<out Any>> -> Result.Success(UIState(args)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    private class FieldBuilder (private val validator : ChildUIValidator) {
        fun buildFirstname(newValue: String): FieldWrapper<String> {
            val errorId : Int? = validator.validateFirstname(newValue)
            return FieldWrapper(newValue, errorId)
        }
        fun buildLastname(newValue: String): FieldWrapper<String> {
            val errorId : Int? = validator.validateLastname(newValue)
            return FieldWrapper(newValue, errorId)
        }
        fun buildAge(newValue: Int): FieldWrapper<Int> {
            val errorId : Int? = validator.validateAge(newValue)
            return FieldWrapper(newValue, errorId)
        }
        fun buildGender(newValue: Gender): FieldWrapper<Gender> {
            return FieldWrapper(newValue, null)
        }
        fun buildPicture(newValue: Uri?): FieldWrapper<Uri> {
            val errorId : Int? = validator.validatePictureChange(newValue)
            return FieldWrapper(newValue, errorId)
        }
        fun buildParentPhone(newValue: String): FieldWrapper<String> {
            val errorId : Int? = validator.validateParentPhone(newValue)
            return FieldWrapper(newValue, errorId)
        }
    }

    private val fieldBuilder = FieldBuilder(ChildUIValidator(uiState))

    val titleBuilder = UITitleBuilder()

    val uiTitleState : StateFlow<UITitleState> = titleBuilder.uiTitleState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UITitleState()
    )

    private fun isModified (initial: Result<Child>, fields : Result<UIState>): Boolean? {
        if (initial !is Result.Success) return null
        if (fields !is Result.Success) return null
        if (fields.content.firstnameState.value != initial.content.firstname) return true
        if (fields.content.lastnameState.value != initial.content.lastname) return true
        if (fields.content.ageState.value != initial.content.age) return true
        if (fields.content.genderState.value != initial.content.gender) return true
        if (fields.content.pictureState.value != initial.content.picture) return true
        if (fields.content.parentPhoneState.value != initial.content.parentPhone) return true
        return false
    }

    private fun hasError (fields : Result<UIState>): Boolean? {
        if (fields !is Result.Success) return null
        return fields.content.args.any { it.errorId != null }
    }

    init {
        combine(_initialChildState, uiState) { i, s ->
            titleBuilder.setModified(isModified(i, s))
            titleBuilder.setError(hasError(s))
        }.launchIn(viewModelScope)
    }

    sealed class UIEvent {
        data class FirstnameChanged(val newValue: String): UIEvent()
        data class LastnameChanged(val newValue: String): UIEvent()
        data class AgeChanged(val newValue: Int): UIEvent()
        data class GenderChanged(val newValue: Gender): UIEvent()
        data class PictureChanged(val newValue: Uri?): UIEvent()
        data class ParentPhoneChanged(val newValue: String): UIEvent()
    }

    fun send (uiEvent : UIEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is UIEvent.FirstnameChanged -> _firstnameState.value =
                    fieldBuilder.buildFirstname(uiEvent.newValue)

                is UIEvent.LastnameChanged -> _lastnameState.value =
                    fieldBuilder.buildLastname(uiEvent.newValue)

                is UIEvent.AgeChanged -> _ageState.value =
                    fieldBuilder.buildAge(uiEvent.newValue)

                is UIEvent.GenderChanged -> _genderState.value =
                    fieldBuilder.buildGender(uiEvent.newValue)

                is UIEvent.PictureChanged -> _pictureState.value =
                    fieldBuilder.buildPicture(uiEvent.newValue)

                is UIEvent.ParentPhoneChanged -> _parentPhoneState.value =
                    fieldBuilder.buildParentPhone(uiEvent.newValue)

                else -> {}
            }
        }
    }

    fun edit (pid : Long) = viewModelScope.launch {
        _id.value = pid
    }

    fun create(child: Child) = viewModelScope.launch {
        val pid : Long = repository.create(child)
        _id.value = pid
    }

    fun save() = viewModelScope.launch {
        if (_initialChildState.value !is Result.Success) return@launch
        if (uiState.value !is Result.Success) return@launch
        _initialChildState.value as Result.Success
        val child = Child (
            _id.value,
            _firstnameState.value.value!!,
            _lastnameState.value.value!!,
            _ageState.value.value!!,
            _genderState.value.value!!,
            _pictureState.value.value,
            _parentPhoneState.value.value!!
        )
        repository.update(child)
    }

}
