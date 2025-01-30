package fr.uha.wetterwald.summercamp.ui.activity

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import fr.uha.hassenforder.android.R
import fr.uha.hassenforder.android.ui.field.FieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedDecorator
import fr.uha.wetterwald.summercamp.model.Child

@Composable
fun OutlineChildrenField(
    value: List<Child>,
    onAddMember : (Child) -> Unit,
    onRemoveMember : (Child) -> Unit,
    maxParticipants: Int,
    modifier: Modifier,
    labelId: Int?,
    errorId: Int?
) {
    val showDialog =  remember { mutableStateOf(false) }
    val isFull = value.size >= maxParticipants

    if (showDialog.value) {
        ChildPicker (
            titleId = R.string.select_child,
        ) { showDialog.value = false; if (it != null) onAddMember(it) }
    }

    OutlinedDecorator (
        modifier = modifier,
        labelId = labelId,
        errorId = errorId,
    ) {
        Scaffold (
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { if (!isFull) showDialog.value = true },
                    modifier = Modifier
                        .alpha(if (isFull) 0.3f else 1f) // Réduit l'opacité si désactivé
                        .clickable(enabled = !isFull) {
                            if (!isFull) showDialog.value = true
                        }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "add")
                }
            }
        )
        { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(
                    items = value,
                    key = { child -> child.childId }
                ) {
                        item ->
                    Box (
                        modifier = Modifier.clickable(
                            onClick = { onRemoveMember(item) }
                        )
                    ) {
                        ActivityChild(item)
                    }
                }
            }
        }
    }
}

@Composable
fun OutlinedChildrenFieldWrapper (
    field : FieldWrapper<List<Child>>,
    onAddMember : (Child) -> Unit,
    onRemoveMember : (Child) -> Unit,
    maxParticipants: FieldWrapper<Int>,
    modifier : Modifier = Modifier,
    @StringRes labelId : Int? = null,
) {
    OutlineChildrenField(
        value = field.value ?: emptyList(),
        onAddMember = onAddMember,
        onRemoveMember = onRemoveMember,
        modifier = modifier,
        labelId = labelId,
        errorId = field.errorId,
        maxParticipants = maxParticipants.value ?: Int.MAX_VALUE
    )
}

