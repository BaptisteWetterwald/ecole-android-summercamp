//package fr.uha.wetterwald.summercamp.ui.person.old
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.ramcosta.composedestinations.annotation.Destination
//import com.ramcosta.composedestinations.annotation.RootGraph
//import fr.uha.wetterwald.summercamp.model.Child
//import fr.uha.wetterwald.summercamp.model.FullChild
//import fr.uha.wetterwald.summercamp.model.FullSupervisor
//import fr.uha.wetterwald.summercamp.model.Gender
//import fr.uha.wetterwald.summercamp.model.Person
//import fr.uha.wetterwald.summercamp.model.Supervisor
//import androidx.compose.runtime.*
//
//@Destination<RootGraph>
//@Composable
//fun CreatePersonScreen(
//    viewModel: PersonViewModel = hiltViewModel(),
//    onPersonCreated: () -> Unit = {}
//) {
//    // Initialisation d'une nouvelle Person
//    val person = remember {
//        Person(
//            firstname = "",
//            lastname = "",
//            age = 0,
//            gender = Gender.MALE, // Par défaut
//            picture = null
//        )
//    }
//
//    var child by remember { mutableStateOf<Child?>(null) }
//    var supervisor by remember { mutableStateOf<Supervisor?>(null) }
//
//    PersonForm(
//        person = person,
//        child = child?.let { FullChild(person, it) },
//        supervisor = supervisor?.let { FullSupervisor(person, it) },
//        onPersonChange = { updatedPerson -> person.copy(firstname = updatedPerson.firstname, lastname = updatedPerson.lastname, age = updatedPerson.age) },
//        onChildChange = { updatedChild -> child = updatedChild },
//        onSupervisorChange = { updatedSupervisor -> supervisor = updatedSupervisor },
//        isNewPerson = true,
//        onSave = {
//            viewModel.create(person, child, supervisor) // Appel à la méthode create
//            onPersonCreated()
//        }
//    )
//}
