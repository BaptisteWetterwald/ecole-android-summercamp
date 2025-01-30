package fr.uha.wetterwald.summercamp.ui.supervisor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CreateSupervisorScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditSupervisorScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.uha.hassenforder.android.ui.StateScreen
import fr.uha.hassenforder.android.ui.SwipeableItem
import fr.uha.hassenforder.android.ui.app.AppTopBar
import fr.uha.hassenforder.android.ui.app.UITitleState
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Gender
import fr.uha.wetterwald.summercamp.model.Supervisor

@Destination<RootGraph>
@Composable
fun ListSupervisorsScreen (
    vm : ListSupervisorsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold (
        topBar = { AppTopBar(UITitleState(screenNameId = R.string.list_supervisors)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigator.navigate(CreateSupervisorScreenDestination) },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            StateScreen(state = uiState) { content ->
                SuccessListSupervisorsScreen(
                    content,
                    navigator,
                    { vm.send (it) },
                    Modifier.fillMaxHeight()
                )
            }
        }
    }
}

@Composable
fun SuccessListSupervisorsScreen (
    uiState: ListSupervisorsViewModel.UIState,
    navigator: DestinationsNavigator,
    send: (ListSupervisorsViewModel.UIEvent) -> Unit,
    modifier: Modifier
) {
    LazyColumn () {
        items(
            items = uiState.supervisors,
            key = { item -> item.supervisorId }
        ) { item ->
            SwipeableItem (
                onEdit = { navigator.navigate(EditSupervisorScreenDestination(item.supervisorId))},
                onDelete = { send(ListSupervisorsViewModel.UIEvent.OnDelete(item)) }
            ) {
                SupervisorItem (item)
            }
        }
    }
}

@Composable
fun SupervisorItem (supervisor: Supervisor) {
    val gender : ImageVector =
        when(supervisor.gender) {
            Gender.FEMALE -> Icons.Outlined.Female
            Gender.MALE -> Icons.Outlined.Male
        }
    ListItem (
        headlineContent = {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(supervisor.firstname)
                Text(supervisor.lastname)
            }
        },
        supportingContent = {
            Row (
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.Phone, contentDescription = "phone")
                Text(supervisor.phone, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        trailingContent = {
            Icon(imageVector = gender, contentDescription = "gender", modifier = Modifier.size(48.dp))
        }
    )
}
