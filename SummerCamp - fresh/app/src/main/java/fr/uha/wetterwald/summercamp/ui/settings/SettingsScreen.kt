package fr.uha.wetterwald.summercamp.ui.settings

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DoNotDisturb
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.uha.hassenforder.android.ui.SwipeableItem
import fr.uha.hassenforder.android.ui.app.AppTopBar
import fr.uha.hassenforder.android.ui.app.UITitleState
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Gender
import fr.uha.wetterwald.summercamp.model.Person
import fr.uha.wetterwald.summercamp.ui.person.ListPersonsViewModel

@Destination<RootGraph>
@Composable
fun SettingsScreen (
    vm : SettingsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    Scaffold (
        topBar = { AppTopBar(UITitleState(screenNameId = R.string.settings)) },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Button(
                onClick = { vm.onClear() }
            ) {
                Text("Clear")
            }
            Button(
                onClick = {
                    vm.onFill()
                    Log.d("SettingsScreen", "onFill")
                }
            ) {
                Text("Fill")
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
            key = { item -> item.id }
        ) { item ->
            SwipeableItem (
                //onEdit = { navigator.navigate(EditPersonScreenDestination(item.pid)) },
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
            Gender.FEMALE -> Icons.Outlined.Female
            Gender.MALE -> Icons.Outlined.Male
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
                //Icon(imageVector = Icons.Outlined.Phone, contentDescription = "phone")
                //Text(person.phone, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        trailingContent = {
            Icon(imageVector = gender, contentDescription = "gender", modifier = Modifier.size(48.dp))
        }
    )
}