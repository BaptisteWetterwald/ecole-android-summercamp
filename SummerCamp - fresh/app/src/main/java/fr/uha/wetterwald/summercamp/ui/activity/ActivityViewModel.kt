package fr.uha.wetterwald.summercamp.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.hassenforder.android.ui.app.UITitleBuilder
import fr.uha.hassenforder.android.ui.field.FieldWrapper
import fr.uha.hassenforder.android.viewmodel.Result
import fr.uha.wetterwald.summercamp.database.ActivityUpdateDTO
import fr.uha.wetterwald.summercamp.database.ActivityUpdateDTO.Specialty
import fr.uha.wetterwald.summercamp.model.Activity
import fr.uha.wetterwald.summercamp.model.Child
import fr.uha.wetterwald.summercamp.model.FullActivity
import fr.uha.wetterwald.summercamp.model.Supervisor
import fr.uha.wetterwald.summercamp.repository.ActivityRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor (
    private val repository: ActivityRepository
): ViewModel() {

    private val _id: MutableStateFlow<Long> = MutableStateFlow(0)

    data class UIState(
        val name: FieldWrapper<String>,
        val description: FieldWrapper<String>,
        val maxParticipants: FieldWrapper<Int>,
        val location: FieldWrapper<String>,
        val period: FieldWrapper<String>,
        val specialty: FieldWrapper<fr.uha.wetterwald.summercamp.model.Specialty>,
        val children: FieldWrapper<List<Child>>,
        val supervisors: FieldWrapper<List<Supervisor>>,
        val activity: FullActivity,
    ) {
        companion object {
            fun create (activity : FullActivity) : UIState {
                val validator = ActivityUIValidator(activity)
                val name = FieldWrapper(activity.activity.name, validator.validateName(activity.activity.name))
                val description = FieldWrapper(activity.activity.description, validator.validateDescription(activity.activity.description))
                val maxParticipants = FieldWrapper(activity.activity.maxParticipants, validator.validateMaxParticipants(activity.activity.maxParticipants))
                val location = FieldWrapper(activity.activity.location, validator.validateLocation(activity.activity.location))
                val period = FieldWrapper(activity.activity.period, validator.validatePeriod(activity.activity.period))
                val specialty = FieldWrapper(activity.activity.specialty, validator.validateSpecialty(activity.activity.specialty))
                val children = FieldWrapper(activity.children, validator.validateChildren(activity.children))
                val supervisors = FieldWrapper(activity.supervisors, validator.validateSupervisors(activity.supervisors))
                return UIState(name, description, maxParticipants, location, period, specialty, children, supervisors, activity)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState : StateFlow<Result<UIState>> = _id
        .flatMapLatest { id -> repository.getActivityById(id) }
        .map { activity ->
            if (activity != null)
                Result.Success(UIState.create(activity))
            else Result.Error()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    val titleBuilder = UITitleBuilder()

    sealed class UIEvent {
        data class NameChanged(val newValue: String): UIEvent()
        data class DescriptionChanged(val newValue: String): UIEvent()
        data class MaxParticipantsChanged(val newValue: Int): UIEvent()
        data class LocationChanged(val newValue: String): UIEvent()
        data class PeriodChanged(val newValue: String): UIEvent()
        data class SpecialtyChanged(val newValue: fr.uha.wetterwald.summercamp.model.Specialty): UIEvent()
        data class AddChild(val newValue: Child): UIEvent()
        data class RemoveChild(val newValue: Child): UIEvent()
        data class AddSupervisor(val newValue: Supervisor): UIEvent()
        data class RemoveSupervisor(val newValue: Supervisor): UIEvent()
    }

    fun send (uiEvent : UIEvent) {
        viewModelScope.launch {
            if (uiState.value !is Result.Success) return@launch
            val activityId = (uiState.value as Result.Success<UIState>).content.activity.activity.activityId
            when (uiEvent) {
                is UIEvent.NameChanged ->
                    repository.update(ActivityUpdateDTO.Name(activityId, uiEvent.newValue))
                is UIEvent.DescriptionChanged ->
                    repository.update(ActivityUpdateDTO.Description(activityId, uiEvent.newValue))
                is UIEvent.MaxParticipantsChanged ->
                    repository.update(ActivityUpdateDTO.MaxParticipants(activityId, uiEvent.newValue))
                is UIEvent.LocationChanged ->
                    repository.update(ActivityUpdateDTO.Location(activityId, uiEvent.newValue))
                is UIEvent.PeriodChanged ->
                    repository.update(ActivityUpdateDTO.Period(activityId, uiEvent.newValue))
                is UIEvent.SpecialtyChanged ->
                    repository.update(Specialty(activityId, fr.uha.wetterwald.summercamp.model.Specialty.valueOf(uiEvent.newValue.name)))
                is UIEvent.AddChild ->
                    repository.addChild(activityId, uiEvent.newValue)
                is UIEvent.RemoveChild ->
                    repository.removeChild(activityId, uiEvent.newValue)
                is UIEvent.AddSupervisor ->
                    repository.addSupervisor(activityId, uiEvent.newValue)
                is UIEvent.RemoveSupervisor ->
                    repository.removeSupervisor(activityId, uiEvent.newValue)
                else -> {}
            }
        }
    }

    fun edit (id : Long) = viewModelScope.launch {
        _id.value = id
    }

    fun create(activity: Activity) = viewModelScope.launch {
        val id : Long = repository.create(activity)
        _id.value = id
    }

}
