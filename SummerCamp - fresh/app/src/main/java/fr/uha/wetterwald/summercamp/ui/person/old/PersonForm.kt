//package fr.uha.wetterwald.summercamp.ui.person.old
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import fr.uha.hassenforder.android.ui.field.FieldWrapper
//import fr.uha.hassenforder.android.ui.field.OutlinedTextFieldWrapper
//import fr.uha.wetterwald.summercamp.model.Child
//import fr.uha.wetterwald.summercamp.model.FullChild
//import fr.uha.wetterwald.summercamp.model.FullSupervisor
//import fr.uha.wetterwald.summercamp.model.Person
//import fr.uha.wetterwald.summercamp.model.Supervisor
//
//@Composable
//fun PersonForm(
//    person: Person,
//    child: FullChild?,
//    supervisor: FullSupervisor?,
//    onPersonChange: (Person) -> Unit,
//    onChildChange: (Child?) -> Unit,
//    onSupervisorChange: (Supervisor?) -> Unit,
//    onSave: () -> Unit,
//    isNewPerson: Boolean = false // Ajout d'un indicateur pour différencier création et modification
//) {
//    var firstname by remember { mutableStateOf(person.firstname) }
//    var lastname by remember { mutableStateOf(person.lastname) }
//    var age by remember { mutableStateOf(person.age.toString()) }
//    var parentPhone by remember { mutableStateOf(child?.child?.parentPhone ?: "") }
//    var phone by remember { mutableStateOf(supervisor?.supervisor?.phone ?: "") }
//    var availability by remember { mutableStateOf(supervisor?.supervisor?.availability ?: "") }
//    var isChild by remember { mutableStateOf(child != null) }
//    var isSupervisor by remember { mutableStateOf(supervisor != null) }
//
//    Column(Modifier.padding(16.dp)) {
//        Text(
//            text = if (isNewPerson) "Créer une nouvelle personne" else "Modifier une personne",
//            style = MaterialTheme.typography.h2
//        )
//
//        OutlinedTextFieldWrapper(
//            field = FieldWrapper(firstname),
//            onValueChange = {
//                firstname = it
//                onPersonChange(person.copy(firstname = it))
//            }
//        )
//
//        OutlinedTextField(
//            value = lastname,
//            onValueChange = {
//                lastname = it
//                onPersonChange(person.copy(lastname = it))
//            },
//            label = { Text("Nom") },
//            modifier = Modifier.fillMaxWidth().padding(8.dp)
//        )
//
//        OutlinedTextField(
//            value = age,
//            onValueChange = {
//                age = it
//                onPersonChange(person.copy(age = it.toIntOrNull() ?: person.age))
//            },
//            label = { Text("Âge") },
//            modifier = Modifier.fillMaxWidth().padding(8.dp)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Option pour ajouter un rôle "Child"
//        OutlinedTextField(
//            value = parentPhone,
//            onValueChange = {
//                parentPhone = it
//                if (isChild) onChildChange(Child(personRef = person.personId, parentPhone = it))
//            },
//            label = { Text("Téléphone du parent (si Enfant)") },
//            modifier = Modifier.fillMaxWidth().padding(8.dp),
//            enabled = isChild
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Option pour ajouter un rôle "Supervisor"
//        OutlinedTextField(
//            value = phone,
//            onValueChange = {
//                phone = it
//                if (isSupervisor) onSupervisorChange(
//                    Supervisor(
//                        personRef = person.personId,
//                        phone = it,
//                        specialties = emptyList(),
//                        availability = availability
//                    )
//                )
//            },
//            label = { Text("Téléphone (si Superviseur)") },
//            modifier = Modifier.fillMaxWidth().padding(8.dp),
//            enabled = isSupervisor
//        )
//
//        OutlinedTextField(
//            value = availability,
//            onValueChange = {
//                availability = it
//                if (isSupervisor) onSupervisorChange(
//                    Supervisor(
//                        personRef = person.personId,
//                        phone = phone,
//                        specialties = emptyList(),
//                        availability = it
//                    )
//                )
//            },
//            label = { Text("Disponibilité") },
//            modifier = Modifier.fillMaxWidth().padding(8.dp),
//            enabled = isSupervisor
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Bouton Sauvegarder
//        Button(onClick = onSave, modifier = Modifier.fillMaxWidth()) {
//            Text(if (isNewPerson) "Créer" else "Sauvegarder")
//        }
//    }
//}
//
