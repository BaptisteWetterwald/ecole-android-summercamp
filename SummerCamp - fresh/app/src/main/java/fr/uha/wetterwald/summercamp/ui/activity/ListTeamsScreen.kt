package fr.uha.wetterwald.summercamp.ui.activity

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun ListTeamsScreen (
    //vm : ListTeamsViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    navigator: DestinationsNavigator
) {
    /*val uiState by fr.uha.wetterwald.summercamp.ui.team.ListTeamsViewModel.uiState.collectAsStateWithLifecycle()

    androidx.compose.material3.Scaffold (
        topBar = { fr.uha.hassenforder.android.ui.app.AppTopBar(fr.uha.hassenforder.android.ui.app.UITitleState(fr.uha.hassenforder.android.ui.app.UITitleState.screenNameId = R.string.list_team)) },
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

     */
}

@Composable
fun SuccessListTeamsScreen (
    //uiState: ListTeamsViewModel.UIState,
    navigator : DestinationsNavigator,
    //send : (ListTeamsViewModel.UIEvent) -> Unit
) {
    /*LazyColumn () {
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

     */
}

@Composable
fun TeamItem (/*team: Team*/) {
    /*ListItem (
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

     */
}