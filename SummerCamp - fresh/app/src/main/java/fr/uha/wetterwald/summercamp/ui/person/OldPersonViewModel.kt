//package fr.uha.wetterwald.summercamp.ui.person
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import fr.uha.hassenforder.android.ui.field.FieldWrapper
//import fr.uha.hassenforder.android.viewmodel.Result
//import fr.uha.wetterwald.summercamp.model.*
//import fr.uha.wetterwald.summercamp.repository.ChildRepository
//import fr.uha.wetterwald.summercamp.repository.SupervisorRepository
//import kotlinx.coroutines.flow.*
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class PersonViewModel @Inject constructor(
//    private val childRepository: ChildRepository,
//    private val supervisorRepository: SupervisorRepository
//) : ViewModel() {
//
//    // Liste des enfants et superviseurs
//    private val _children: MutableStateFlow<Result<List<Child>>> = MutableStateFlow(Result.Loading)
//    private val _supervisors: MutableStateFlow<Result<List<Supervisor>>> = MutableStateFlow(Result.Loading)
//
//    val children: StateFlow<Result<List<Child>>> = _children.asStateFlow()
//    val supervisors: StateFlow<Result<List<Supervisor>>> = _supervisors.asStateFlow()
//
//    // État pour la création ou l'édition d'une Person
//    private val _currentPersonState: MutableStateFlow<Result<UIState>> =
//        MutableStateFlow(Result.Loading)
//    val currentPersonState: StateFlow<Result<UIState>> = _currentPersonState.asStateFlow()
//
//    data class UIState(
//        val person: FieldWrapper<Person>,
//        val child: FieldWrapper<Child?>? = null,
//        val supervisor: FieldWrapper<Supervisor?>? = null,
//    ) {
//        companion object {
//            fun create(person: Person, child: Child?, supervisor: Supervisor?): UIState {
//                return UIState(
//                    FieldWrapper(person, null),
//                    child?.let { FieldWrapper(it, null) },
//                    supervisor?.let { FieldWrapper(it, null) }
//                )
//            }
//        }
//    }
//
//    sealed class UIEvent {
//        data class CreatePerson(val person: Person, val isChild: Boolean) : UIEvent()
//        data class UpdatePerson(val person: Person) : UIEvent()
//        data class DeletePerson(val personId: Long, val isChild: Boolean) : UIEvent()
//    }
//
//    init {
//        loadPersons()
//    }
//
//    // Charger les listes d'enfants et de superviseurs
//    private fun loadPersons() {
//        viewModelScope.launch {
//            childRepository.getAll()
//                .catch { _children.value = Result.Error() }
//                .collect { _children.value = Result.Success(it) }
//        }
//
//        viewModelScope.launch {
//            supervisorRepository.getAll()
//                .catch { _supervisors.value = Result.Error() }
//                .collect { _supervisors.value = Result.Success(it) }
//        }
//    }
//
//    // Gérer les événements UI
//    fun send(event: UIEvent) {
//        viewModelScope.launch {
//            when (event) {
//                is UIEvent.CreatePerson -> {
//                    val personId = if (event.isChild) {
//                        childRepository.create(Child(0, event.person.personId, ""))
//                    } else {
//                        supervisorRepository.create(Supervisor(0, event.person.personId, "", emptyList(), ""))
//                    }
//                    loadPersons() // Recharger les données après création
//                }
//
//                is UIEvent.UpdatePerson -> {
//                    if (event.person is Child) {
//                        childRepository.update(event.person)
//                    } else if (event.person is Supervisor) {
//                        supervisorRepository.update(event.person)
//                    }
//                    loadPersons()
//                }
//
//                is UIEvent.DeletePerson -> {
//                    if (event.isChild) {
//                        childRepository.delete(Child(event.personId, 0, ""))
//                    } else {
//                        supervisorRepository.delete(Supervisor(event.personId, 0, "", emptyList(), ""))
//                    }
//                    loadPersons()
//                }
//            }
//        }
//    }
//}
