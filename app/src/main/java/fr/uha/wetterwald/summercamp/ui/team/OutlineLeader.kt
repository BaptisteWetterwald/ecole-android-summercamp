package fr.uha.wetterwald.summercamp.ui.team

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import fr.uha.hassenforder.android.R
import fr.uha.hassenforder.android.ui.field.FieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedDecorator
import fr.uha.wetterwald.summercamp.model.Person

@Composable
fun OutlineLeaderField(
    value: Person?,
    onValueChange: (Person?) -> Unit,
    modifier: Modifier,
    labelId: Int?,
    errorId: Int?
) {
    val showDialog =  remember { mutableStateOf(false) }

    if (showDialog.value) {
        PersonPicker (
            titleId = R.string.title_day,
        ) { showDialog.value = false; if (it != null) onValueChange(it) }
    }

    OutlinedDecorator (
        modifier = modifier,
        labelId = labelId,
        errorId = errorId,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = { showDialog.value = true }
                )
        ) {
            if (value != null)
                TeamPerson(value, modifier = Modifier.weight(1.0F))
            IconButton(
                onClick = { onValueChange(null) }
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "delete")
            }
        }
    }
}

@Composable
fun OutlinedLeaderFieldWrapper (
    field : FieldWrapper<Person?>,
    onValueChange : (Person?) -> Unit,
    modifier : Modifier = Modifier,
    @StringRes labelId : Int? = null,
) {
    OutlineLeaderField(
        value = field.value,
        onValueChange = onValueChange,
        modifier = modifier,
        labelId = labelId,
        errorId = field.errorId,
    )
}

