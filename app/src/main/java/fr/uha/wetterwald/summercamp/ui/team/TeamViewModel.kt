package fr.uha.wetterwald.summercamp.ui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.hassenforder.android.ui.app.UITitleBuilder
import fr.uha.hassenforder.android.ui.app.UITitleState
import fr.uha.hassenforder.android.ui.field.FieldWrapper
import fr.uha.hassenforder.android.viewmodel.Result
import fr.uha.wetterwald.summercamp.database.TeamUpdateDTO
import fr.uha.wetterwald.summercamp.model.FullTeam
import fr.uha.wetterwald.summercamp.model.Person
import fr.uha.wetterwald.summercamp.model.Team
import fr.uha.wetterwald.summercamp.repository.TeamRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor (
    private val repository: TeamRepository
): ViewModel() {

    private val _id: MutableStateFlow<Long> = MutableStateFlow(0)

    data class UIState(
        val name : FieldWrapper<String>,
        val startDay : FieldWrapper<Date>,
        val duration : FieldWrapper<Int>,
        val leader : FieldWrapper<Person?>,
        val members : FieldWrapper<List<Person>>,
        val team : FullTeam,
    ) {
        companion object {
            fun create (team : FullTeam) : UIState {
                val validator = TeamUIValidator(team)
                val name = FieldWrapper(team.team.name, validator.validateName(team.team.name))
                val startDay = FieldWrapper(team.team.startDay, validator.validateStartDay(team.team.startDay))
                val duration = FieldWrapper(team.team.duration, validator.validateDuration(team.team.duration))
                val leader : FieldWrapper<Person?> = FieldWrapper(team.leader, validator.validateLeader(team.leader))
                val members : FieldWrapper<List<Person>> = FieldWrapper(team.members, validator.validateMembers(team.members))
                return UIState(name, startDay, duration, leader, members, team)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState : StateFlow<Result<UIState>> = _id
        .flatMapLatest { id -> repository.getTeamById(id) }
        .map { team ->
            if (team != null)
                Result.Success(UIState.create(team))
            else Result.Error()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    val titleBuilder = UITitleBuilder()

    val uiTitleState : StateFlow<UITitleState> = titleBuilder.uiTitleState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UITitleState()
    )

    sealed class UIEvent {
        data class NameChanged(val newValue: String): UIEvent()
        data class DateChanged(val newValue: Date): UIEvent()
        data class DurationChanged(val newValue: Int): UIEvent()
        data class LeaderChanged(val newValue: Person?): UIEvent()
        data class AddMember(val newValue: Person): UIEvent()
        data class RemoveMember(val newValue: Person): UIEvent()
    }

    fun send (uiEvent : UIEvent) {
        viewModelScope.launch {
            if (uiState.value !is Result.Success) return@launch
            val tid = (uiState.value as Result.Success<UIState>).content.team.team.tid
            when (uiEvent) {
                is UIEvent.NameChanged ->
                    repository.update(TeamUpdateDTO.Name(tid, uiEvent.newValue))

                is UIEvent.DateChanged ->
                    repository.update(TeamUpdateDTO.StartDay(tid, uiEvent.newValue))

                is UIEvent.DurationChanged ->
                    repository.update(TeamUpdateDTO.Duration(tid, uiEvent.newValue))

                is UIEvent.LeaderChanged ->
                    repository.update(TeamUpdateDTO.Leader(tid, uiEvent.newValue?.pid ?: 0))

                is UIEvent.AddMember ->
                    repository.addMember(tid, uiEvent.newValue)

                is UIEvent.RemoveMember ->
                    repository.removeMember(tid, uiEvent.newValue)

                else -> {}
            }
        }
    }

    fun edit (pid : Long) = viewModelScope.launch {
        _id.value = pid
    }

    fun create(team: Team) = viewModelScope.launch {
        val pid : Long = repository.create(team)
        _id.value = pid
    }

}
