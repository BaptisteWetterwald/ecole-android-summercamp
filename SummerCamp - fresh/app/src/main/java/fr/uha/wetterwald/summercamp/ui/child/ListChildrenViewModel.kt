package fr.uha.wetterwald.summercamp.ui.child

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.hassenforder.android.viewmodel.Result
import fr.uha.wetterwald.summercamp.model.Child
import fr.uha.wetterwald.summercamp.repository.ChildRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListChildrenViewModel @Inject constructor(
    private val repository: ChildRepository
) : ViewModel() {

    private val _children: Flow<List<Child>> = repository.getAll()

    data class UIState(
        val children: List<Child>
    )

    val uiState: StateFlow<Result<UIState>> = _children
        .map { list: List<Child> ->
            Result.Success(UIState(list))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    sealed class UIEvent {
        data class OnDelete(val child: Child) : UIEvent()
    }

    fun send(uiEvent: UIEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is UIEvent.OnDelete -> onDelete(uiEvent.child)
            }
        }
    }

    fun onDelete(child: Child) = viewModelScope.launch {
        repository.delete(child)
    }

}