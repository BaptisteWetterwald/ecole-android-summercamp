package fr.uha.wetterwald.summercamp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.wetterwald.summercamp.database.AppDatabase
//import fr.uha.wetterwald.summercamp.database.DbInitializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor (
    private val database: AppDatabase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

//    fun onClear () = viewModelScope.launch {
//        withContext(dispatcher) {
//            DbInitializer(database).clear()
//        }
//    }
//
//    fun onFill () = viewModelScope.launch {
//        withContext(dispatcher) {
//            DbInitializer(database).populate()
//        }
//    }

}