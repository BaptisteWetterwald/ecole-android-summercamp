package fr.uha.wetterwald.summercamp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.GreetingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ListActivitiesScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ListPersonsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import fr.uha.hassenforder.android.model.IconPicture
import fr.uha.hassenforder.android.model.IconRender
import fr.uha.hassenforder.android.ui.app.AppBottomBar
import fr.uha.hassenforder.android.ui.app.BottomBarDestination
import fr.uha.wetterwald.summercamp.R

@Composable
fun SummerCampAppScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            AppBottomBar(
                navController,
                NavGraphs.root,
                bottomNavigations
            )
        }
    ) { innerPadding ->
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            modifier = Modifier.padding(innerPadding),
            navController = navController,
        )
    }
}

private val bottomNavigations = arrayOf<BottomBarDestination>(
    BottomBarDestination(
        direction = GreetingScreenDestination,
        icon = IconRender(
            focused = IconPicture(vector = Icons.Filled.Home),
            unfocused = IconPicture(vector = Icons.Outlined.Home)
        ),
        labelId = R.string.home
    ),

    BottomBarDestination(
        direction = ListPersonsScreenDestination,
        icon = IconRender(
            focused = IconPicture(vector = Icons.Filled.Group),
            unfocused = IconPicture(vector = Icons.Outlined.Group)
        ),
        labelId = R.string.list_person
    ),

    BottomBarDestination(
        direction = ListActivitiesScreenDestination,
        icon = IconRender(
            focused = IconPicture(vector = Icons.Filled.Celebration),
            unfocused = IconPicture(vector = Icons.Outlined.Celebration)
        ),
        labelId = R.string.list_activities
    ),

    BottomBarDestination(
        direction = SettingsScreenDestination,
        icon = IconRender(
            focused = IconPicture(vector = Icons.Filled.Settings),
            unfocused = IconPicture(vector = Icons.Outlined.Settings)
        ),
        labelId = R.string.settings
    )
)