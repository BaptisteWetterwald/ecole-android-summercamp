package fr.uha.wetterwald.summercamp.ui.field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T : Enum<T>> EnumPicker(
    currentValue: T?,
    enumValues: Array<T>,
    onValueChange: (T) -> Unit,
    showDialog: Boolean,
    label: String = "Choose an option",
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(label) },
            text = {
                Column {
                    enumValues.forEach { value ->
                        Text(
                            text = value.name,
                            modifier = Modifier
                                .clickable {
                                    onValueChange(value)
                                    onDismiss()
                                }
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}
