package fr.uha.wetterwald.summercamp.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.hassenforder.android.ui.field.FieldWrapper
import fr.uha.hassenforder.android.viewmodel.Result
import fr.uha.wetterwald.summercamp.model.*
import fr.uha.wetterwald.summercamp.repository.ActivityRepository
import fr.uha.wetterwald.summercamp.repository.ChildRepository
import fr.uha.wetterwald.summercamp.repository.SupervisorRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val childRepository: ChildRepository,
    private val supervisorRepository: SupervisorRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {

    private val _personId = MutableStateFlow<Long?>(null)
    private val _activityId = MutableStateFlow<Long?>(null)

    data class UIState(
        val person: FieldWrapper<Person>,
        val child: FieldWrapper<Child?>?,
        val supervisor: FieldWrapper<Supervisor?>?,
        val isInActivity: Boolean = false
    ) {
        companion object {
            fun create(person: Person, child: Child?, supervisor: Supervisor?, isInActivity: Boolean): UIState {
                return UIState(
                    person = FieldWrapper(person, null),
                    child = child?.let { FieldWrapper(it, null) },
                    supervisor = supervisor?.let { FieldWrapper(it, null) },
                    isInActivity = isInActivity
                )
            }
        }
    }

    private val _uiState: MutableStateFlow<Result<UIState>> = MutableStateFlow(Result.Loading)
    val uiState: StateFlow<Result<UIState>> = _uiState.asStateFlow()

    sealed class UIEvent {
        data class LoadPerson(val personId: Long, val activityId: Long?) : UIEvent()
        data class CreatePerson(val person: Person, val isChild: Boolean) : UIEvent()
        data class UpdatePerson(val person: Person) : UIEvent()
        data class DeletePerson(val personId: Long, val isChild: Boolean) : UIEvent()
        object AddToActivity : UIEvent()
        object RemoveFromActivity : UIEvent()
    }

    fun send(event: UIEvent) {
        viewModelScope.launch {
            when (event) {
                is UIEvent.LoadPerson -> loadPerson(event.personId, event.activityId)
                is UIEvent.CreatePerson -> createPerson(event.person, event.isChild)
                is UIEvent.UpdatePerson -> updatePerson(event.person)
                is UIEvent.DeletePerson -> deletePerson(event.personId, event.isChild)
                UIEvent.AddToActivity -> addToActivity()
                UIEvent.RemoveFromActivity -> removeFromActivity()
            }
        }
    }

    private suspend fun loadPerson(personId: Long, activityId: Long?) {
        _personId.value = personId
        _activityId.value = activityId

        val child = childRepository.getById(personId).firstOrNull()?.child
        val supervisor = supervisorRepository.getById(personId).firstOrNull()?.supervisor

        val person = when {
            child != null -> Person(
                personId = child.personRef,
                firstname = "Child FirstName", // Remplace par des données réelles si possible
                lastname = "Child LastName",
                age = 10,
                gender = Gender.MALE,
                picture = null
            )
            supervisor != null -> Person(
                personId = supervisor.personRef,
                firstname = "Supervisor FirstName", // Remplace par des données réelles si possible
                lastname = "Supervisor LastName",
                age = 30,
                gender = Gender.FEMALE,
                picture = null
            )
            else -> null
        }

        if (person != null) {
            _uiState.value = Result.Success(
                UIState.create(person, child, supervisor, activityId != null)
            )
        } else {
            _uiState.value = Result.Error()
        }
    }

    private suspend fun createPerson(person: Person, isChild: Boolean) {
        val personId = if (isChild) {
            childRepository.create(Child(0, person.personId, ""))
        } else {
            supervisorRepository.create(Supervisor(0, person.personId, "", emptyList(), ""))
        }
        _personId.value = personId
        loadPerson(personId, _activityId.value)
    }

    private suspend fun updatePerson(person: Person) {
        if (_uiState.value !is Result.Success) return
        val currentState = (_uiState.value as Result.Success<UIState>).content

        if (currentState.child != null) {
            childRepository.update(currentState.child.value!!)
        } else if (currentState.supervisor != null) {
            supervisorRepository.update(currentState.supervisor.value!!)
        }

        _uiState.value = Result.Success(currentState.copy(person = FieldWrapper(person, null)))
    }

    private suspend fun deletePerson(personId: Long, isChild: Boolean) {
        if (isChild) {
            childRepository.delete(Child(personId, 0, ""))
        } else {
            supervisorRepository.delete(Supervisor(personId, 0, "", emptyList(), ""))
        }
        _uiState.value = Result.Loading
    }

    private suspend fun addToActivity() {
        val activityId = _activityId.value ?: return
        val personId = _personId.value ?: return

        activityRepository.addMember(activityId, Person(personId, "", "", 0, Gender.MALE, null))
        loadPerson(personId, activityId)
    }

    private suspend fun removeFromActivity() {
        val activityId = _activityId.value ?: return
        val personId = _personId.value ?: return

        activityRepository.removeMember(activityId, Person(personId, "", "", 0, Gender.MALE, null))
        loadPerson(personId, null)
    }
}
