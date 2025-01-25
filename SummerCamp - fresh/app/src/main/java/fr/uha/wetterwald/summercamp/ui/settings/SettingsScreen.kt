package fr.uha.wetterwald.summercamp.ui.settings

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.uha.hassenforder.android.ui.app.AppTopBar
import fr.uha.hassenforder.android.ui.app.UITitleState
import fr.uha.wetterwald.summercamp.R

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
                onClick = {
                    vm.onClear()
                }
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