package fr.uha.wetterwald.summercamp.ui.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.hassenforder.android.ui.app.AppTitle
import fr.uha.wetterwald.summercamp.database.ChildDao
import fr.uha.wetterwald.summercamp.model.Child
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ChildPickerViewModel @Inject constructor(private val dao: ChildDao) : ViewModel() {
    val children: Flow<List<Child>> = dao.getAll()
}

@Composable
fun ChildPicker(
    vm: ChildPickerViewModel = hiltViewModel(),
    titleId: Int,
    onSelect: (Child?) -> Unit,
) {
    val children = vm.children.collectAsStateWithLifecycle(initialValue = emptyList())
    Dialog(onDismissRequest = { onSelect(null) }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { AppTitle(screenTitleId = titleId, color = Color.White) },
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                items(
                    items = children.value,
                    key = { child -> child.childId }
                ) { item ->
                    Box(
                        modifier = Modifier.clickable(
                            onClick = { onSelect(item) }
                        )
                    ) {
                        ActivityChild(item)
                    }
                }
            }
        }
    }
}

