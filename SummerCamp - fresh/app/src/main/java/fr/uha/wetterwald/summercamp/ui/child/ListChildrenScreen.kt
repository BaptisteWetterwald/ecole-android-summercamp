package fr.uha.wetterwald.summercamp.ui.child

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
import androidx.compose.material.icons.outlined.Person
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
import com.ramcosta.composedestinations.generated.destinations.CreateChildScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditChildScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.uha.hassenforder.android.ui.StateScreen
import fr.uha.hassenforder.android.ui.SwipeableItem
import fr.uha.hassenforder.android.ui.app.AppTopBar
import fr.uha.hassenforder.android.ui.app.UITitleState
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Child
import fr.uha.wetterwald.summercamp.model.Gender

@Destination<RootGraph>
@Composable
fun ListChildrenScreen(
    vm: ListChildrenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { AppTopBar(UITitleState(screenNameId = R.string.list_children)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(CreateChildScreenDestination)
                },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            StateScreen(state = uiState) { content ->
                SuccessListChildrenScreen(
                    content,
                    navigator,
                    { vm.send(it) },
                    Modifier.fillMaxHeight()
                )
            }
        }
    }
}

@Composable
fun SuccessListChildrenScreen(
    uiState: ListChildrenViewModel.UIState,
    navigator: DestinationsNavigator,
    send: (ListChildrenViewModel.UIEvent) -> Unit,
    modifier: Modifier
) {
    LazyColumn {
        items(
            items = uiState.children,
            key = { item -> item.childId }
        ) { item ->
            SwipeableItem(
                onEdit = { navigator.navigate(EditChildScreenDestination(item.childId)) },
                onDelete = { send(ListChildrenViewModel.UIEvent.OnDelete(item)) }
            ) {
                ChildItem(item)
            }
        }
    }
}

@Composable
fun ChildItem(child: Child) {
    val gender: ImageVector =
        when (child.gender) {
            Gender.FEMALE -> Icons.Outlined.Female
            Gender.MALE -> Icons.Outlined.Male
        }
    ListItem(
        headlineContent = {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(child.firstname)
                Text(child.lastname)
            }
        },
        supportingContent = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = "person")
                Text(child.age.toString() + " y/o", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        trailingContent = {
            Icon(
                imageVector = gender,
                contentDescription = "gender",
                modifier = Modifier.size(32.dp)
            )
        }
    )
}