package fr.uha.wetterwald.summercamp.ui.field

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.uha.hassenforder.android.ui.field.FieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedDecorator
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Gender

@Composable
fun OutlinedGenderFieldWrapper(
    field: FieldWrapper<Gender>,
    onValueChange: ((Gender) -> Unit)?,
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
                .clickable { showDialog = true } // Ouvre la boîte de dialogue
                .padding(8.dp)
        ) {
            Text(
                text = field.value?.name ?: stringResource(id = R.string.choose_gender),
                modifier = Modifier.weight(1.0f)
            )

            Icon(
                imageVector = Icons.Outlined.List,
                contentDescription = stringResource(id = R.string.choose_gender),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (showDialog) {
            EnumPicker(
                currentValue = field.value,
                enumValues = Gender.values(),
                onValueChange = {
                    showDialog = false
                    onValueChange?.invoke(it)
                },
                showDialog = showDialog,
                label = stringResource(id = R.string.choose_specialty),
                onDismiss = { showDialog = false }
            )
        }
    }
}
