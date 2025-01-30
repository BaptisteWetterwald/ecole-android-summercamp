package fr.uha.wetterwald.summercamp.ui.activity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.uha.hassenforder.android.ui.StateScreen
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Activity
import fr.uha.wetterwald.summercamp.model.Specialty

@Destination<RootGraph>
@Composable
fun CreateActivityScreen(
    vm: ActivityViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.create(
            Activity(
                0,
                "Name",
                "Description",
                5,
                "Location",
                "01/01/2025 10:00-12:00",
                Specialty.OTHER
            )
        )
        vm.titleBuilder.setScreenNameId(R.string.create_activity)
    }
    StateScreen(state = uiState) { content ->
        SuccessActivityScreen(content, { vm.send(it) })
    }

}