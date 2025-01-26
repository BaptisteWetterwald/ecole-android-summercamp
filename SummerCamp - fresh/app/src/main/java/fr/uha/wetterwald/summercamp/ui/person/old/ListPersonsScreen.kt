//package fr.uha.wetterwald.summercamp.ui.person.old
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.ramcosta.composedestinations.annotation.Destination
//import com.ramcosta.composedestinations.annotation.RootGraph
//import fr.uha.wetterwald.summercamp.model.Person
//
//@Destination<RootGraph>
//@Composable
//fun ListPersonsScreen(
//    viewModel: PersonViewModel = hiltViewModel(),
//    onPersonClick: (Person) -> Unit = {}
//) {
//    val persons by viewModel.allPersons.collectAsState()
//
//    Column(Modifier.fillMaxSize().padding(16.dp)) {
//        Text(
//            text = "Liste des personnes",
//            style = MaterialTheme.typography.h5,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        if (persons.isEmpty()) {
//            Text("Aucune personne trouvée.", style = MaterialTheme.typography.body1)
//        } else {
//            persons.forEach { person ->
//                PersonRow(person = person, onClick = { onPersonClick(person) })
//            }
//        }
//    }
//}
//
//@Composable
//fun PersonRow(person: Person, onClick: () -> Unit) {
//    Row(
//        Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .clickable { onClick() }) {
//        Column(Modifier.weight(1f)) {
//            Text(text = "${person.firstname} ${person.lastname}", style = MaterialTheme.typography.body1)
//            Text(text = "Âge : ${person.age}", style = MaterialTheme.typography.body2)
//        }
//    }
//}