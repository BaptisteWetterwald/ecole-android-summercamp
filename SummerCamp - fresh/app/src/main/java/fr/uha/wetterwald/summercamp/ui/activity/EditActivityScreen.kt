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

@Destination<RootGraph>
@Composable
fun EditActivityScreen (
    vm : ActivityViewModel = hiltViewModel(),
    navigator : DestinationsNavigator,
    tid : Long,
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.edit(tid)
        vm.titleBuilder.setScreenNameId(R.string.edit_activity)
    }
    StateScreen(state = uiState) {
            content ->
        SuccessActivityScreen(content, { vm.send(it) })
    }

}