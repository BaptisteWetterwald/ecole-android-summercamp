package fr.uha.wetterwald.summercamp.ui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.hassenforder.android.viewmodel.Result
import fr.uha.wetterwald.summercamp.model.Activity
import fr.uha.wetterwald.summercamp.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListActivitiesViewModel @Inject constructor (
    private val repository: ActivityRepository
) : ViewModel() {

    private val _activities : Flow<List<Activity>> = repository.getAll()

    data class UIState (
        val activities : List<Activity>
    )

    val uiState : StateFlow<Result<UIState>> = _activities
        .map { list : List<Activity> ->
            Result.Success(UIState(list))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    sealed class UIEvent {
        data class OnDelete(val activity: Activity) : UIEvent()
    }

    fun send (uiEvent : UIEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is UIEvent.OnDelete -> onDelete(uiEvent.activity)
            }
        }
    }

    fun onDelete (activity: Activity) = viewModelScope.launch {
        repository.delete(activity)
    }

}