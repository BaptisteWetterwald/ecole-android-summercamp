package fr.uha.wetterwald.summercamp.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.wetterwald.summercamp.model.Person
import fr.uha.wetterwald.summercamp.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import fr.uha.hassenforder.android.viewmodel.Result
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ListPersonsViewModel @Inject constructor (
    private val repository: PersonRepository
) : ViewModel() {

    private val _persons : Flow<List<Person>> = repository.getAll()

    data class UIState (
        val persons : List<Person>
    )

    val uiState : StateFlow<Result<UIState>> = _persons
        .map { list : List<Person> ->
            Result.Success(UIState(list))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    sealed class UIEvent {
        data class OnDelete(val person: Person) : UIEvent()
    }

    fun send (uiEvent : UIEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is ListPersonsViewModel.UIEvent.OnDelete -> onDelete(uiEvent.person)
            }
        }
    }

    fun onDelete (person: Person) = viewModelScope.launch {
        repository.delete(person)
    }

}