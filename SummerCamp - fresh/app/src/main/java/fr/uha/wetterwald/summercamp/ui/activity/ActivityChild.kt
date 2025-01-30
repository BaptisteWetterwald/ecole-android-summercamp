package fr.uha.wetterwald.summercamp.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Person
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
import fr.uha.wetterwald.summercamp.model.Child
import fr.uha.wetterwald.summercamp.model.Gender

@Composable
fun ActivityChild (
    child: Child,
    modifier: Modifier = Modifier,
) {
    val gender : ImageVector =
        when(child.gender) {
            Gender.FEMALE -> Icons.Outlined.Female
            Gender.MALE -> Icons.Outlined.Male
        }
    ListItem (
        headlineContent = {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(child.firstname, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(child.lastname, fontSize = 12.sp)
            }
        },
        supportingContent = {
            Row (
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(imageVector = Icons.Outlined.Person, contentDescription = "person")
                Text(child.age.toString() + " y/o", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        },
        trailingContent = {
            Icon(imageVector = gender, contentDescription = "gender", modifier = Modifier.size(32.dp))
        }
    )
}
