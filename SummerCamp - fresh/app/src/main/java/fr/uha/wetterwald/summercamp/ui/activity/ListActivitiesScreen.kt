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
import com.ramcosta.composedestinations.generated.destinations.CreateActivityScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditActivityScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.uha.hassenforder.android.icons.TimeEnd
import fr.uha.hassenforder.android.icons.TimeStart
import fr.uha.hassenforder.android.ui.StateScreen
import fr.uha.hassenforder.android.ui.SwipeableItem
import fr.uha.hassenforder.android.ui.app.AppTopBar
import fr.uha.hassenforder.android.ui.app.UITitleState
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Activity

@Destination<RootGraph>
@Composable
fun ListActivitiesScreen (
    vm : ListActivitiesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold (
        topBar = { AppTopBar(UITitleState(screenNameId = R.string.list_activities)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigator.navigate(CreateActivityScreenDestination)}
            ) {
                Icon(Icons.Filled.Add, contentDescription = "add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            StateScreen(state = uiState) { content ->
                SuccessListActivitiesScreen(content, navigator, { vm.send (it) })
            }
        }
    }
}

@Composable
fun SuccessListActivitiesScreen (
    uiState: ListActivitiesViewModel.UIState,
    navigator : DestinationsNavigator,
    send : (ListActivitiesViewModel.UIEvent) -> Unit
) {
    LazyColumn () {
        items(
            items = uiState.activities,
            key = { item -> item.activityId }
        ) { item ->
            SwipeableItem (
                onEdit = { navigator.navigate(EditActivityScreenDestination(item.activityId))},
                onDelete = { send(ListActivitiesViewModel.UIEvent.OnDelete(item)) }
            ) {
                ActivityItem(item)
            }
        }
    }
}

@Composable
fun ActivityItem(activity: Activity) {
    ListItem (
        headlineContent = {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(activity.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("(${activity.period.replace("T", " ")})", fontSize = 15.sp)
            }
        },
        supportingContent = {
            Row (
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.TimeStart, contentDescription = "time start")
                val description = activity.description
                val maxParticipants = activity.maxParticipants
                val location = activity.location

                val title = if (description.length > 20) description.substring(0, 20) + "..." else description
                val sub = "Max: $maxParticipants. At $location"

                Text(title, fontSize = 16.sp)
                Text(sub, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Icon(imageVector = Icons.TimeEnd, contentDescription = "time end")
            }
        },
    )
}

