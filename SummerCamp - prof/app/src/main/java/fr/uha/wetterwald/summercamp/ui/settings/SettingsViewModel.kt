package fr.uha.wetterwald.summercamp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.wetterwald.summercamp.database.FeedDatabase
import fr.uha.wetterwald.summercamp.database.TeamDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor (
    private val database: TeamDatabase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    fun onClear () = viewModelScope.launch {
        withContext(dispatcher) {
            FeedDatabase(database).clear()
        }
    }

    fun onFill () = viewModelScope.launch {
        withContext(dispatcher) {
            FeedDatabase(database).populate(0)
        }
    }

}