package fr.uha.wetterwald.summercamp.ui.supervisor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.hassenforder.android.viewmodel.Result
import fr.uha.wetterwald.summercamp.model.Supervisor
import fr.uha.wetterwald.summercamp.repository.SupervisorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListSupervisorsViewModel @Inject constructor (
    private val repository: SupervisorRepository
) : ViewModel() {

    private val _supervisors : Flow<List<Supervisor>> = repository.getAll()

    data class UIState (
        val supervisors : List<Supervisor>
    )

    val uiState : StateFlow<Result<UIState>> = _supervisors
        .map { list : List<Supervisor> ->
            Result.Success(UIState(list))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    sealed class UIEvent {
        data class OnDelete(val supervisor: Supervisor) : UIEvent()
    }

    fun send (uiEvent : UIEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is UIEvent.OnDelete -> onDelete(uiEvent.supervisor)
            }
        }
    }

    fun onDelete (supervisor: Supervisor) = viewModelScope.launch {
        repository.delete(supervisor)
    }

}