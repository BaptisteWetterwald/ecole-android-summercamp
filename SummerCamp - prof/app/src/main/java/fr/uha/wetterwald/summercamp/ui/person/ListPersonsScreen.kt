package fr.uha.wetterwald.summercamp.ui.person

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.DoNotDisturb
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
import com.ramcosta.composedestinations.generated.destinations.CreatePersonScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditPersonScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.uha.hassenforder.android.ui.StateScreen
import fr.uha.hassenforder.android.ui.SwipeableItem
import fr.uha.hassenforder.android.ui.app.AppTopBar
import fr.uha.hassenforder.android.ui.app.UITitleState
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Gender
import fr.uha.wetterwald.summercamp.model.Person

@Destination<RootGraph>
@Composable
fun ListPersonsScreen (
    vm : ListPersonsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold (
        topBar = { AppTopBar(UITitleState(screenNameId = R.string.list_person)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigator.navigate(CreatePersonScreenDestination) },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "add")
            }
        }
    ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                StateScreen(state = uiState) { content ->
                    SuccessListPersonsScreen(content, navigator, { vm.send (it) })
                }
            }
        }
}

@Composable
fun SuccessListPersonsScreen (
    uiState: ListPersonsViewModel.UIState,
    navigator : DestinationsNavigator,
    send : (ListPersonsViewModel.UIEvent) -> Unit
) {
    LazyColumn () {
        items(
            items = uiState.persons,
            key = { item -> item.pid }
        ) { item ->
            SwipeableItem (
                onEdit = { navigator.navigate(EditPersonScreenDestination(item.pid)) },
                onDelete = { send(ListPersonsViewModel.UIEvent.OnDelete(item)) }
            ) {
                PersonItem (item)
            }
        }
    }
}

@Composable
fun PersonItem (person : Person) {
    val gender : ImageVector =
        when(person.gender) {
            Gender.NO -> Icons.Outlined.DoNotDisturb
            Gender.GIRL -> Icons.Outlined.Female
            Gender.BOY -> Icons.Outlined.Male
        }
    ListItem (
        headlineContent = {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(person.firstname)
                Text(person.lastname)
            }
        },
        supportingContent = {
            Row (
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.Phone, contentDescription = "phone")
                Text(person.phone, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        trailingContent = {
            Icon(imageVector = gender, contentDescription = "gender", modifier = Modifier.size(48.dp))
        }
    )
}
