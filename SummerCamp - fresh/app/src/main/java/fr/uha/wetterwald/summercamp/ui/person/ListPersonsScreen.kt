package fr.uha.wetterwald.summercamp.ui.person

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.ramcosta.composedestinations.generated.destinations.CreateChildScreenDestination
import com.ramcosta.composedestinations.generated.destinations.CreateSupervisorScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.uha.hassenforder.android.ui.StateScreen
import fr.uha.hassenforder.android.ui.app.AppTopBar
import fr.uha.hassenforder.android.ui.app.UITitleState
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.ui.child.ListChildrenViewModel
import fr.uha.wetterwald.summercamp.ui.child.SuccessListChildrenScreen
import fr.uha.wetterwald.summercamp.ui.supervisor.ListSupervisorsViewModel
import fr.uha.wetterwald.summercamp.ui.supervisor.SuccessListSupervisorsScreen

@Destination<RootGraph>
@Composable
fun ListPersonsScreen(
    childrenVm: ListChildrenViewModel = hiltViewModel(),
    supervisorsVm: ListSupervisorsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val childrenState by childrenVm.uiState.collectAsStateWithLifecycle()
    val supervisorsState by supervisorsVm.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { AppTopBar(UITitleState(screenNameId = R.string.list_person)) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Section Children (50% max)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true) // 50% de l'espace dispo
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SectionHeader(
                        text = "Children",
                        onAddClick = { navigator.navigate(CreateChildScreenDestination) }
                    )
                    StateScreen(state = childrenState) { childrenContent ->
                        SuccessListChildrenScreen(
                            childrenContent, navigator, { childrenVm.send(it) },
                            Modifier.fillMaxHeight()
                        )
                    }
                }
            }

            // Section Supervisors (50% max)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true) // 50% de l'espace dispo
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SectionHeader(
                        text = "Supervisors",
                        onAddClick = { navigator.navigate(CreateSupervisorScreenDestination) }
                    )
                    StateScreen(state = supervisorsState) { supervisorsContent ->
                        SuccessListSupervisorsScreen(
                            supervisorsContent, navigator, { supervisorsVm.send(it) },
                            Modifier.fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(text: String, onAddClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add $text")
        }
    }
}
