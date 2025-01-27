package fr.uha.wetterwald.summercamp.ui.person

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.uha.hassenforder.android.ui.StateScreen
import fr.uha.hassenforder.android.ui.app.AppMenuEntry
import fr.uha.hassenforder.android.ui.app.AppTopBar
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Gender
import fr.uha.wetterwald.summercamp.model.Specialty
import fr.uha.wetterwald.summercamp.model.Supervisor

@Destination<RootGraph>
@Composable
fun CreateSupervisorScreen (
    vm : SupervisorViewModel = hiltViewModel(),
    navigator : DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val uiTitleState by vm.uiTitleState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.create(
            Supervisor(0, "Baptiste", "Wetterwald", 24, Gender.MALE, null, "0612345678", listOf<Specialty>(Specialty.SPORTS, Specialty.GAMES, Specialty.SCIENCE), "01/01/2025 10:00-12:00")
        )
        vm.titleBuilder.setScreenNameId(R.string.create_supervisor)
    }

    val menuEntries = listOf(
        AppMenuEntry.ActionEntry(
            titleId = R.string.save,
            icon = Icons.Filled.Save,
            enabled = { uiTitleState.isSavable ?: false},
            listener = { vm.save(); navigator.popBackStack() }
        )
    )

    Scaffold(
        modifier = Modifier.padding(top = 30.dp),
        topBar = {
            AppTopBar(uiTitleState = uiTitleState, navigator, menuEntries = menuEntries)
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            StateScreen(state = uiState) {
                    content ->
                SuccessSupervisorScreen(content, { vm.send(it) })
            }
        }
    }
}