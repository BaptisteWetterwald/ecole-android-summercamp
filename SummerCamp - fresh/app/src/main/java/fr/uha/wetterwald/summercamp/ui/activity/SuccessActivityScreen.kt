package fr.uha.wetterwald.summercamp.ui.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.uha.hassenforder.android.ui.field.OutlinedIntFieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedTextFieldWrapper
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.ui.field.OutlinedSpecialtyFieldWrapper

@Composable
fun SuccessActivityScreen(
    activity: ActivityViewModel.UIState,
    send : (ActivityViewModel.UIEvent) -> Unit,
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
                field = activity.name,
                onValueChange = { send(ActivityViewModel.UIEvent.NameChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.name,
            )

            OutlinedTextFieldWrapper(
                field = activity.description,
                onValueChange = { send(ActivityViewModel.UIEvent.DescriptionChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.description,
            )

            OutlinedIntFieldWrapper(
                field = activity.maxParticipants,
                onValueChange = { send(ActivityViewModel.UIEvent.MaxParticipantsChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.max_participants
            )

            // location
            OutlinedTextFieldWrapper(
                field = activity.location,
                onValueChange = { send(ActivityViewModel.UIEvent.LocationChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.location,
            )

            // period
            OutlinedTextFieldWrapper(
                field = activity.period,
                onValueChange = { send(ActivityViewModel.UIEvent.PeriodChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.period,
            )

            // specialty
            OutlinedSpecialtyFieldWrapper(
                field = activity.specialty,
                onValueChange = { send(ActivityViewModel.UIEvent.SpecialtyChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.specialty,
            )

            /*
            // members
            OutlinedMembersFieldWrapper(
                field = activity.members,
                onAddMember = { send(ActivityViewModel.UIEvent.AddMember(it)) },
                onRemoveMember = { send(ActivityViewModel.UIEvent.RemoveMember(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelId = R.string.members,
            )
            */
        }
    }
}
