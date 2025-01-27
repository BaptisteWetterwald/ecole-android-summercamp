package fr.uha.wetterwald.summercamp.ui.field

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.uha.hassenforder.android.ui.field.FieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedDecorator
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Specialty

@Composable
fun OutlinedSpecialtiesFieldWrapper(
    field: FieldWrapper<List<Specialty>>,
    onValueChange: ((List<Specialty>) -> Unit)?,
    modifier: Modifier = Modifier,
    @StringRes labelId: Int? = null,
    @StringRes hintId: Int? = null,
) {
    var showDialog by remember { mutableStateOf(false) }

    OutlinedDecorator(
        modifier = modifier,
        labelId = labelId,
        errorId = field.errorId,
        hintId = hintId
    ) {
        Row(
            modifier = Modifier
                .clickable { showDialog = true } // Open dialog
                .padding(8.dp)
        ) {
            Text(
                text = field.value?.joinToString { it.name } ?: stringResource(id = R.string.choose_specialty),
                modifier = Modifier.weight(1.0f)
            )

            Icon(
                imageVector = androidx.compose.material.icons.Icons.Outlined.List,
                contentDescription = stringResource(id = R.string.choose_specialty),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (showDialog) {
            MultiSelectEnumPicker(
                currentValues = field.value ?: emptyList(),
                enumValues = Specialty.values(),
                onValueChange = {
                    showDialog = false
                    onValueChange?.invoke(it)
                },
                label = stringResource(id = R.string.choose_specialty),
                onDismiss = { showDialog = false }
            )
        }
    }
}