//package fr.uha.wetterwald.summercamp.ui.activity
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.ListItem
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.ramcosta.composedestinations.annotation.Destination
//import com.ramcosta.composedestinations.annotation.RootGraph
//import com.ramcosta.composedestinations.navigation.DestinationsNavigator
//import fr.uha.hassenforder.android.icons.TimeEnd
//import fr.uha.hassenforder.android.icons.TimeStart
//import fr.uha.hassenforder.android.ui.StateScreen
//import fr.uha.hassenforder.android.ui.SwipeableItem
//import fr.uha.hassenforder.android.ui.app.AppTopBar
//import fr.uha.hassenforder.android.ui.app.UITitleState
//import fr.uha.wetterwald.summercamp.R
//import fr.uha.wetterwald.summercamp.model.Activity
//import fr.uha.wetterwald.summercamp.model.EnumConverters
//
//@Destination<RootGraph>
//@Composable
//fun ListActivitiesScreen (
//    vm : ListActivitiesViewModel = hiltViewModel(),
//    navigator: DestinationsNavigator
//) {
//    val uiState by vm.uiState.collectAsStateWithLifecycle()
//
//    Scaffold (
//        topBar = { AppTopBar(UITitleState(screenNameId = R.string.list_team)) },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { /*navigator.navigate(CreateTeamScreenDestination)*/ },
//            ) {
//                Icon(Icons.Filled.Add, contentDescription = "add")
//            }
//        }
//    ) { innerPadding ->
//            Column(
//                modifier = Modifier.padding(innerPadding)
//            ) {
//                StateScreen(state = uiState) { content ->
//                    SuccessListTeamsScreen(content, navigator, { vm.send (it) })
//                }
//            }
//        }
//
//}
//
//@Composable
//fun SuccessListTeamsScreen (
//    uiState: ListActivitiesViewModel.UIState,
//    navigator : DestinationsNavigator,
//    send : (ListActivitiesViewModel.UIEvent) -> Unit
//) {
//    LazyColumn () {
//        items(
//            items = uiState.activities,
//            key = { item -> item.id }
//        ) { item ->
//            SwipeableItem (
//                //onEdit = { navigator.navigate(EditTeamScreenDestination(item.id)) },
//                onDelete = { send(ListActivitiesViewModel.UIEvent.OnDelete(item)) }
//            ) {
//                ActivityItem (item)
//            }
//        }
//    }
//}
//
//@Composable
//fun ActivityItem (activity: Activity) {
//    ListItem (
//        headlineContent = {
//            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
//                Text(activity.name)
//            }
//        },
//        supportingContent = {
//            Row (
//                horizontalArrangement = Arrangement.spacedBy(6.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                /*
//                Icon(imageVector = Icons.TimeStart, contentDescription = "phone")
//                Text(Converter.convert(team.startDay), fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                Text(",")
//                Text(team.duration.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                Text("days")
//                Icon(imageVector = Icons.TimeEnd, contentDescription = "phone")
//                */
//
//                Text(activity.description, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                Text(",")
//                Text(activity.specialty.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                Text(",")
//                Text("Max : ${activity.maxParticipants}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            }
//        },
//    )
//}