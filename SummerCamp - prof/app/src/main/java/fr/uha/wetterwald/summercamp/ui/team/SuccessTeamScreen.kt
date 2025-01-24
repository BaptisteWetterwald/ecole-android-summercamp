package fr.uha.wetterwald.summercamp.ui.team

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.uha.hassenforder.android.ui.field.OutlinedDateFieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedIntFieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedTextFieldWrapper
import fr.uha.wetterwald.summercamp.R

@Composable
fun SuccessTeamScreen(
    team: TeamViewModel.UIState,
    send : (TeamViewModel.UIEvent) -> Unit,
) {
    Scaffold(
        topBar = {
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            OutlinedTextFieldWrapper(
                field = team.name,
                onValueChange = { send(TeamViewModel.UIEvent.NameChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.name,
            )
            OutlinedDateFieldWrapper(
                field = team.startDay,
                onValueChange = { send(TeamViewModel.UIEvent.DateChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.start,
            )
            OutlinedIntFieldWrapper(
                field = team.duration,
                onValueChange = { send(TeamViewModel.UIEvent.DurationChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.duration,
            )
            OutlinedLeaderFieldWrapper(
                field = team.leader,
                onValueChange = { send(TeamViewModel.UIEvent.LeaderChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.leader,
            )
            OutlinedMembersFieldWrapper(
                field = team.members,
                onAddMember = { send(TeamViewModel.UIEvent.AddMember(it)) },
                onRemoveMember = { send(TeamViewModel.UIEvent.RemoveMember(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.members,
            )
        }
    }
}
