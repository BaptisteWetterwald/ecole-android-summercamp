//package fr.uha.wetterwald.summercamp.ui.person.old
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import fr.uha.hassenforder.android.viewmodel.Result
//import fr.uha.wetterwald.summercamp.model.*
//import fr.uha.wetterwald.summercamp.repository.ChildRepository
//import fr.uha.wetterwald.summercamp.repository.PersonRepository
//import fr.uha.wetterwald.summercamp.repository.SupervisorRepository
//import kotlinx.coroutines.flow.*
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class PersonViewModel @Inject constructor(
//    private val personRepository: PersonRepository,
//    private val childRepository: ChildRepository,
//    private val supervisorRepository: SupervisorRepository
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow<Result<UIState>>(Result.Loading)
//    val uiState: StateFlow<Result<UIState>> = _uiState.asStateFlow()
//
//    val allPersons: StateFlow<List<Person>> = personRepository.getAll()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = emptyList()
//        )
//
//    data class UIState(
//        val person: Person,
//        val child: FullChild?, // Utilise FullChild pour inclure Person + Child
//        val supervisor: FullSupervisor? // Utilise FullSupervisor pour inclure Person + Supervisor
//    )
//
//    sealed class UIEvent {
//        data class UpdatePerson(val person: Person) : UIEvent()
//        data class UpdateChild(val child: FullChild?) : UIEvent()
//        data class UpdateSupervisor(val supervisor: FullSupervisor?) : UIEvent()
//    }
//
//    fun edit(personId: Long) {
//        viewModelScope.launch {
//            val person = personRepository.getById(personId).firstOrNull()
//            val child = childRepository.getById(personId).firstOrNull()
//            val supervisor = supervisorRepository.getById(personId).firstOrNull()
//
//            Log.d("DEBUG", "Person = $person, Child = $child, Supervisor = $supervisor")
//
//            if (person != null) {
//                _uiState.value = Result.Success(UIState(person, child, supervisor))
//            } else {
//                _uiState.value = Result.Error()
//            }
//        }
//    }
//
//    fun send(event: UIEvent) {
//        viewModelScope.launch {
//            val currentState = (_uiState.value as? Result.Success)?.content
//            if (currentState == null) {
//                _uiState.value = Result.Error()
//                return@launch
//            }
//
//            when (event) {
//                is UIEvent.UpdatePerson -> {
//                    personRepository.update(event.person)
//                    _uiState.value = Result.Success(
//                        UIState(
//                            person = event.person,
//                            child = currentState.child,
//                            supervisor = currentState.supervisor
//                        )
//                    )
//                }
//
//                is UIEvent.UpdateChild -> {
//                    if (event.child != null) {
//                        childRepository.update(event.child.child)
//                        _uiState.value = Result.Success(
//                            currentState.copy(child = event.child)
//                        )
//                    } else {
//                        _uiState.value = Result.Success(
//                            currentState.copy(child = null)
//                        )
//                    }
//                }
//
//                is UIEvent.UpdateSupervisor -> {
//                    if (event.supervisor != null) {
//                        supervisorRepository.update(event.supervisor.supervisor)
//                        _uiState.value = Result.Success(
//                            currentState.copy(supervisor = event.supervisor)
//                        )
//                    } else {
//                        _uiState.value = Result.Success(
//                            currentState.copy(supervisor = null)
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    // Nouvelle méthode pour créer une Person avec ses rôles éventuels
//    fun create(person: Person, child: Child?, supervisor: Supervisor?) {
//        viewModelScope.launch {
//            try {
//                // Création de la Person
//                val personId = personRepository.create(person)
//
//                // Association des rôles si présents
//                child?.let {
//                    childRepository.create(it.copy(personRef = personId))
//                }
//                supervisor?.let {
//                    supervisorRepository.create(it.copy(personRef = personId))
//                }
//
//                Log.d("DEBUG", "Person créée avec ID $personId")
//                _uiState.value = Result.Success(
//                    UIState(
//                        person = person.copy(personId = personId),
//                        child = child?.let { FullChild(person.copy(personId = personId), it) },
//                        supervisor = supervisor?.let { FullSupervisor(person.copy(personId = personId), it) }
//                    )
//                )
//            } catch (e: Exception) {
//                Log.e("DEBUG", "Erreur lors de la création de la Person : ${e.message}")
//                _uiState.value = Result.Error()
//            }
//        }
//    }
//}
//
//
