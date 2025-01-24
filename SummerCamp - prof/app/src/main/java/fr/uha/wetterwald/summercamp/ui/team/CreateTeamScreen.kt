package fr.uha.wetterwald.summercamp.ui.team

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
import fr.uha.wetterwald.summercamp.model.Team
import java.util.Date

@Destination<RootGraph>
@Composable
fun CreateTeamScreen (
    vm : TeamViewModel = hiltViewModel(),
    navigator : DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.create(
            Team(0, "jouer", Date(), 5, 0)
        )
        vm.titleBuilder.setScreenNameId(R.string.create_team)
    }
    StateScreen(state = uiState) {
        content ->
        SuccessTeamScreen(content, { vm.send(it) })
    }

}