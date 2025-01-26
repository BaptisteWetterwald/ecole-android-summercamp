//package fr.uha.wetterwald.summercamp.ui.person.new
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import fr.uha.hassenforder.android.viewmodel.Result
//import fr.uha.wetterwald.summercamp.model.Person
//import fr.uha.wetterwald.summercamp.repository.PersonRepository
//import kotlinx.coroutines.flow.*
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class ListPersonsViewModel @Inject constructor(
//    private val repository: PersonRepository
//) : ViewModel() {
//
//    private val _persons: Flow<List<Person>> = repository.getAllPersons()
//
//    data class UIState(
//        val persons: List<Person>
//    )
//
//    val uiState: StateFlow<Result<UIState>> = _persons
//        .map { list: List<Person> ->
//            Result.Success(UIState(list))
//        }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = Result.Loading
//        )
//
//    sealed class UIEvent {
//        data class OnDelete(val person: Person) : UIEvent()
//    }
//
//    fun send(uiEvent: UIEvent) {
//        viewModelScope.launch {
//            when (uiEvent) {
//                is UIEvent.OnDelete -> onDelete(uiEvent.person)
//            }
//        }
//    }
//
//    private fun onDelete(person: Person) = viewModelScope.launch {
//        repository.deletePerson(person.personId)
//    }
//}
