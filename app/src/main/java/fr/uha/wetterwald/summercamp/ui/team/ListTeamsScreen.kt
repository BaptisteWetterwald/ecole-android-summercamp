package fr.uha.wetterwald.summercamp.ui.team

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CreateTeamScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditTeamScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.uha.hassenforder.android.icons.TimeEnd
import fr.uha.hassenforder.android.icons.TimeStart
import fr.uha.hassenforder.android.ui.Converter
import fr.uha.hassenforder.android.ui.StateScreen
import fr.uha.hassenforder.android.ui.SwipeableItem
import fr.uha.hassenforder.android.ui.app.AppTopBar
import fr.uha.hassenforder.android.ui.app.UITitleState
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Team

@Destination<RootGraph>
@Composable
fun ListTeamsScreen (
    vm : ListTeamsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold (
        topBar = { AppTopBar(UITitleState(screenNameId = R.string.list_team)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigator.navigate(CreateTeamScreenDestination) },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "add")
            }
        }
    ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                StateScreen(state = uiState) { content ->
                    SuccessListTeamsScreen(content, navigator, { vm.send (it) })
                }
            }
        }
}

@Composable
fun SuccessListTeamsScreen (
    uiState: ListTeamsViewModel.UIState,
    navigator : DestinationsNavigator,
    send : (ListTeamsViewModel.UIEvent) -> Unit
) {
    LazyColumn () {
        items(
            items = uiState.teams,
            key = { item -> item.tid }
        ) { item ->
            SwipeableItem (
                onEdit = { navigator.navigate(EditTeamScreenDestination(item.tid)) },
                onDelete = { send(ListTeamsViewModel.UIEvent.OnDelete(item)) }
            ) {
                TeamItem (item)
            }
        }
    }
}

@Composable
fun TeamItem (team: Team) {
    ListItem (
        headlineContent = {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(team.name)
            }
        },
        supportingContent = {
            Row (
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.TimeStart, contentDescription = "phone")
                Text(Converter.convert(team.startDay), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(",")
                Text(team.duration.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("days")
                Icon(imageVector = Icons.TimeEnd, contentDescription = "phone")
            }
        },
    )
}