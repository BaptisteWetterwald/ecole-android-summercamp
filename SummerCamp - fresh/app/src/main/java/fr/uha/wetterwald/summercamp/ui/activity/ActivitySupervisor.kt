package fr.uha.wetterwald.summercamp.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
import fr.uha.wetterwald.summercamp.model.Supervisor

@Composable
fun ActivitySupervisor (
    supervisor: Supervisor,
    modifier: Modifier = Modifier,
) {
    val gender : ImageVector =
        when(supervisor.gender) {
            Gender.FEMALE -> Icons.Outlined.Female
            Gender.MALE -> Icons.Outlined.Male
        }
    ListItem (
        headlineContent = {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(supervisor.firstname)
                Text(supervisor.lastname)
            }
        },
        supportingContent = {
            Row (
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.Phone, contentDescription = "phone")
                Text(supervisor.phone, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        },
        trailingContent = {
            Icon(imageVector = gender, contentDescription = "gender", modifier = Modifier.size(32.dp))
        }
    )
}
