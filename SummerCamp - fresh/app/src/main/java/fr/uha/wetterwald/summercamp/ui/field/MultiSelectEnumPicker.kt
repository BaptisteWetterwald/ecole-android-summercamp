package fr.uha.wetterwald.summercamp.ui.field

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T : Enum<T>> MultiSelectEnumPicker(
    currentValues: List<T>,
    enumValues: Array<T>,
    onValueChange: (List<T>) -> Unit,
    label: String = "Choose options",
    onDismiss: () -> Unit
) {
    var selectedValues by remember { mutableStateOf(currentValues.toMutableSet()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(label) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                enumValues.forEach { value ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .toggleable(
                                value = selectedValues.contains(value),
                                onValueChange = {
                                    if (selectedValues.contains(value)) {
                                        selectedValues.remove(value)
                                    } else {
                                        selectedValues.add(value)
                                    }
                                }
                            )
                    ) {
                        val isChecked = selectedValues.contains(value)
                        androidx.compose.material3.Checkbox(
                            checked = isChecked,
                            onCheckedChange = null // Toggleable handles state
                        )
                        Text(
                            text = value.name,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onValueChange(selectedValues.toList())
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
