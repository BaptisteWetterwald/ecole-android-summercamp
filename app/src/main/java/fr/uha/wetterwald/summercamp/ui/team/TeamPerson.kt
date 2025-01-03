package fr.uha.wetterwald.summercamp.ui.team

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DoNotDisturb
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uha.wetterwald.summercamp.model.Gender
import fr.uha.wetterwald.summercamp.model.Person

@Composable
fun TeamPerson (
    person : Person,
    modifier: Modifier = Modifier,
) {
    val gender : ImageVector =
        when(person.gender) {
            Gender.NO -> Icons.Outlined.DoNotDisturb
            Gender.GIRL -> Icons.Outlined.Female
            Gender.BOY -> Icons.Outlined.Male
        }
    ListItem (
        headlineContent = {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(person.firstname)
                Text(person.lastname)
            }
        },
        supportingContent = {
            Row (
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.Phone, contentDescription = "phone")
                Text(person.phone, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        trailingContent = {
            Icon(imageVector = gender, contentDescription = "gender", modifier = Modifier.size(48.dp))
        }
    )
}
