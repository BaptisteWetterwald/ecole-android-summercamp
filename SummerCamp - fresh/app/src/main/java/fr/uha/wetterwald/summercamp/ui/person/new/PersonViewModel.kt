//package fr.uha.wetterwald.summercamp.ui.person.new
//
//import android.net.Uri
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import fr.uha.hassenforder.android.ui.app.UITitleBuilder
//import fr.uha.hassenforder.android.ui.app.UITitleState
//import fr.uha.hassenforder.android.ui.field.FieldWrapper
//import fr.uha.hassenforder.android.viewmodel.Result
//import fr.uha.wetterwald.summercamp.database.PersonUpdateDTO
//import fr.uha.wetterwald.summercamp.model.Child
//import fr.uha.wetterwald.summercamp.model.FullPerson
//import fr.uha.wetterwald.summercamp.model.Specialty
//import fr.uha.wetterwald.summercamp.model.Supervisor
//import fr.uha.wetterwald.summercamp.repository.PersonRepository
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.*
//import kotlinx.coroutines.launch
//import java.util.Date
//import javax.inject.Inject
//
////@HiltViewModel
////class PersonViewModel @Inject constructor(
////    private val repository: PersonRepository
////) : ViewModel() {
////
////    private val _id: MutableStateFlow<Long> = MutableStateFlow(0)
////
////    data class UIState(
////        val personId: Long,
////        val firstname: FieldWrapper<String>,
////        val lastname: FieldWrapper<String>,
////        val age: FieldWrapper<Int>,
////        val gender: FieldWrapper<Gender>,
////        val picture: FieldWrapper<Uri?>,
////        val isChild: Boolean,
////        val parentPhone: FieldWrapper<String>?, // Specific to Child
////        val phone: FieldWrapper<String>?, // Specific to Supervisor
////        val specialties: FieldWrapper<List<Specialty>>?, // Specific to Supervisor
////        val availability: FieldWrapper<String>? // Specific to Supervisor
////    ) {
////        companion object {
////            fun create(fullPerson: FullPerson): UIState {
////                val validator = PersonUIValidator(fullPerson)
////                val person = fullPerson.person
////                return UIState(
////                    personId = person.personId,
////                    firstname = FieldWrapper(person.firstname, validator.validateFirstname(person.firstname)),
////                    lastname = FieldWrapper(person.lastname, validator.validateLastname(person.lastname)),
////                    age = FieldWrapper(person.age, validator.validateAge(person.age)),
////                    gender = FieldWrapper(person.gender, validator.validateGender(person.gender)),
////                    picture = FieldWrapper(person.picture),
////                    isChild = fullPerson.child != null,
////                    parentPhone = fullPerson.child?.let { FieldWrapper(it.parentPhone, validator.validateParentPhone(it.parentPhone)) },
////                    phone = fullPerson.supervisor?.let { FieldWrapper(it.phone, validator.validatePhone(it.phone)) },
////                    specialties = fullPerson.supervisor?.let { FieldWrapper(it.specialties, validator.validateSpecialties(it.specialties)) },
////                    availability = fullPerson.supervisor?.let { FieldWrapper(it.availability, validator.validateAvailability(it.availability)) }
////                )
////            }
////        }
////    }
////
////
////    @OptIn(ExperimentalCoroutinesApi::class)
////    val uiState: StateFlow<Result<UIState>> = _id
////        .flatMapLatest { id ->
////            repository.getFullPersonById(id)
//////            if (id == 0L) flowOf(null) else repository.getFullPersonById(id)
////        }
////        .map { fullPerson ->
//////            if (fullPerson != null) {
//////                Result.Success(UIState.create(fullPerson))
//////            } else Result.Error()
////            if (fullPerson != null) {
////                Result.Success(UIState.create(fullPerson))
////            } else Result.Error()
////        }
////        .stateIn(
////            scope = viewModelScope,
////            started = SharingStarted.WhileSubscribed(5000),
////            initialValue = Result.Loading
////        )
////
////    val titleBuilder = UITitleBuilder()
////
////    sealed class UIEvent {
////        // Common fields
////        data class FirstnameChanged(val newValue: String) : UIEvent()
////        data class LastnameChanged(val newValue: String) : UIEvent()
////        data class AgeChanged(val newValue: Int) : UIEvent()
////        data class GenderChanged(val newValue: Gender) : UIEvent()
////        data class PictureChanged(val newValue: Uri?) : UIEvent()
////
////        // Specific fields to Child
////        data class ParentPhoneChanged(val newValue: String) : UIEvent()
////
////        // Specific fields to Supervisor
////        data class PhoneChanged(val newValue: String) : UIEvent()
////        data class SpecialtiesChanged(val newValue: List<Specialty>) : UIEvent()
////        data class AvailabilityChanged(val newValue: String) : UIEvent()
////
////        // Other events
////        data class IsChildChanged(val isChild: Boolean) : UIEvent()
////        data class DeletePerson(val personId: Long) : UIEvent()
////    }
////
////    fun send(uiEvent: UIEvent) {
////        viewModelScope.launch {
////            if (uiState.value !is Result.Success) return@launch
////            val currentState = (uiState.value as Result.Success<UIState>).content
////            val personId = currentState.personId
////            when (uiEvent) {
////                is UIEvent.FirstnameChanged -> repository.update(PersonUpdateDTO.Firstname(personId, uiEvent.newValue))
////                is UIEvent.LastnameChanged -> repository.update(PersonUpdateDTO.Lastname(personId, uiEvent.newValue))
////                is UIEvent.AgeChanged -> repository.update(PersonUpdateDTO.Age(personId, uiEvent.newValue))
////                is UIEvent.GenderChanged -> repository.update(PersonUpdateDTO.Gender(personId, uiEvent.newValue))
////                is UIEvent.PictureChanged -> repository.update(PersonUpdateDTO.Picture(personId, uiEvent.newValue))
////
////                // Champs spécifiques à Child
////                is UIEvent.ParentPhoneChanged -> {
////                    if (currentState.isChild) {
////                        repository.update(PersonUpdateDTO.ParentPhone(personId, uiEvent.newValue))
////                    }
////                }
////
////                // Champs spécifiques à Supervisor
////                is UIEvent.PhoneChanged -> {
////                    if (!currentState.isChild) {
////                        repository.update(PersonUpdateDTO.Phone(personId, uiEvent.newValue))
////                    }
////                }
////                is UIEvent.SpecialtiesChanged -> {
////                    if (!currentState.isChild) {
////                        repository.update(PersonUpdateDTO.Specialties(personId, uiEvent.newValue))
////                    }
////                }
////                is UIEvent.AvailabilityChanged -> {
////                    if (!currentState.isChild) {
////                        repository.update(PersonUpdateDTO.Availability(personId, uiEvent.newValue))
////                    }
////                }
////
////                // Événement pour basculer entre Child et Supervisor
////                is UIEvent.IsChildChanged -> {
////                    if (uiEvent.isChild != currentState.isChild) {
////                        if (uiEvent.isChild) {
////                            // Passer à Child : supprimer Supervisor et créer un Child
////                            repository.deleteSupervisor(personId)
////                            repository.insertChild(Child(personRef = personId, parentPhone = ""))
////                        } else {
////                            // Passer à Supervisor : supprimer Child et créer un Supervisor
////                            repository.deleteChild(personId)
////                            repository.insertSupervisor(Supervisor(personRef = personId, phone = "", specialties = emptyList(), availability = ""))
////                        }
////                    }
////                }
////
////                // Suppression complète de la personne
////                is UIEvent.DeletePerson -> {
////                    repository.deletePerson(personId)
////                }
////
////                else -> {}
////            }
////        }
////    }
////
////    fun savePerson() {
////        viewModelScope.launch {
////            if (uiState.value !is Result.Success) return@launch
////            val currentState = (uiState.value as Result.Success<UIState>).content
////            val person = Person(
////                firstname = currentState.firstname.value ?: "Firstname",
////                lastname = currentState.lastname.value ?: "Lastname",
////                age = currentState.age.value ?: 10,
////                gender = currentState.gender.value ?: Gender.MALE, // Par défaut
////                picture = currentState.picture.value
////            )
////
////            val personId = if (_id.value == 0L) {
////                repository.insertPerson(person)
////            } else {
////                repository.updatePerson(person)
////                person.personId
////            }
////
////            if (currentState.isChild) {
////                val child = Child(
////                    personRef = personId,
////                    parentPhone = currentState.parentPhone?.value ?: ""
////                )
////                repository.saveChild(child)
////            } else {
////                val supervisor = Supervisor(
////                    personRef = personId,
////                    phone = currentState.phone?.value ?: "",
////                    specialties = currentState.specialties?.value ?: emptyList(),
////                    availability = currentState.availability?.value ?: ""
////                )
////                repository.saveSupervisor(supervisor)
////            }
////        }
////    }
////
////    fun edit(id: Long) = viewModelScope.launch {
////        _id.value = id
////    }
////
////    fun create() = viewModelScope.launch {
////        _id.value = 0L
////    }
////}
//
//@HiltViewModel
//class PersonViewModel @Inject constructor(
//    private val repository: PersonRepository
//) : ViewModel() {
//
//    private val _id: MutableStateFlow<Long> = MutableStateFlow(0)
//
//    data class UIState(
//        val personId: Long,
//        val firstname: FieldWrapper<String>,
//        val lastname: FieldWrapper<String>,
//        val age: FieldWrapper<Int>,
//        val gender: FieldWrapper<fr.uha.wetterwald.summercamp.model.Gender>,
//        val picture: FieldWrapper<Uri?>,
//        val isChild: Boolean,
////        val parentPhone: FieldWrapper<String>?, // Spécifique aux enfants
////        val phone: FieldWrapper<String>?, // Spécifique aux superviseurs
////        val specialties: FieldWrapper<List<Specialty>>?, // Spécifique aux superviseurs
////        val availability: FieldWrapper<String>? // Spécifique aux superviseurs
//    ) {
//        companion object {
//            fun create(fullPerson: FullPerson): UIState {
//                val validator = PersonUIValidator(fullPerson)
//                val person = fullPerson.person
//                return UIState(
//                    personId = person.personId,
//                    firstname = FieldWrapper(person.firstname, validator.validateFirstname(person.firstname)),
//                    lastname = FieldWrapper(person.lastname, validator.validateLastname(person.lastname)),
//                    age = FieldWrapper(person.age, validator.validateAge(person.age)),
//                    gender = FieldWrapper(person.gender, validator.validateGender(person.gender)),
//                    picture = FieldWrapper(person.picture),
//                    isChild = fullPerson.child != null,
////                    parentPhone = fullPerson.child?.let { FieldWrapper(it.parentPhone, validator.validateParentPhone(it.parentPhone)) },
////                    phone = fullPerson.supervisor?.let { FieldWrapper(it.phone, validator.validatePhone(it.phone)) },
////                    specialties = fullPerson.supervisor?.let { FieldWrapper(it.specialties, validator.validateSpecialties(it.specialties)) },
////                    availability = fullPerson.supervisor?.let { FieldWrapper(it.availability, validator.validateAvailability(it.availability)) }
//                )
//            }
//        }
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    val uiState: StateFlow<Result<UIState>> = _id
//        .flatMapLatest { id ->
//            repository.getFullPersonById(id)
//        }
//        .map { fullPerson ->
//            if (fullPerson != null) {
//                Result.Success(UIState.create(fullPerson))
//            } else {
//                Result.Error()
//            }
//        }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = Result.Loading
//        )
//
//    val titleBuilder = UITitleBuilder()
//
//    sealed class UIEvent {
//        data class FirstnameChanged(val newValue: String) : UIEvent()
//        data class LastnameChanged(val newValue: String) : UIEvent()
//        data class AgeChanged(val newValue: Int) : UIEvent()
//        data class GenderChanged(val newValue: fr.uha.wetterwald.summercamp.model.Gender) : UIEvent()
//        data class PictureChanged(val newValue: Uri?) : UIEvent()
//        data class ParentPhoneChanged(val newValue: String) : UIEvent()
//        data class PhoneChanged(val newValue: String) : UIEvent()
//        data class SpecialtiesChanged(val newValue: List<fr.uha.wetterwald.summercamp.model.Specialty>) : UIEvent()
//        data class AvailabilityChanged(val newValue: String) : UIEvent()
//        data class IsChildChanged(val isChild: Boolean) : UIEvent()
//        data class DeletePerson(val personId: Long) : UIEvent()
//    }
//
//    fun send(uiEvent: UIEvent) {
//        viewModelScope.launch {
//            if (uiState.value !is Result.Success) return@launch
//            val currentState = (uiState.value as Result.Success<UIState>).content
//            val personId = currentState.personId
//            when (uiEvent) {
//                is UIEvent.FirstnameChanged ->
//                    repository.update(PersonUpdateDTO.Firstname(personId, uiEvent.newValue))
//                is UIEvent.LastnameChanged ->
//                    repository.update(PersonUpdateDTO.Lastname(personId, uiEvent.newValue))
//                is UIEvent.AgeChanged ->
//                    repository.update(PersonUpdateDTO.Age(personId, uiEvent.newValue))
//                is UIEvent.GenderChanged ->
//                    repository.update(PersonUpdateDTO.Gender(personId, uiEvent.newValue))
//                is UIEvent.PictureChanged ->
//                    repository.update(PersonUpdateDTO.Picture(personId, uiEvent.newValue))
//                is UIEvent.ParentPhoneChanged -> {
//                    if (currentState.isChild) {
//                        repository.update(PersonUpdateDTO.ParentPhone(personId, uiEvent.newValue))
//                    }
//                }
//                is UIEvent.PhoneChanged -> {
//                    if (!currentState.isChild) {
//                        repository.update(PersonUpdateDTO.Phone(personId, uiEvent.newValue))
//                    }
//                }
////                is UIEvent.SpecialtiesChanged -> {
////                    if (!currentState.isChild) {
////                        repository.update(PersonUpdateDTO.Specialties(personId, uiEvent.newValue))
////                    }
////                }
////                is UIEvent.AvailabilityChanged -> {
////                    if (!currentState.isChild) {
////                        repository.update(PersonUpdateDTO.Availability(personId, uiEvent.newValue))
////                    }
////                }
////                is UIEvent.IsChildChanged -> {
////                    if (uiEvent.isChild != currentState.isChild) {
////                        if (uiEvent.isChild) {
////                            repository.deleteSupervisor(personId)
////                            repository.saveChild(Child(personRef = personId, parentPhone = ""))
////                        } else {
////                            repository.deleteChild(personId)
////                            repository.saveSupervisor(Supervisor(personRef = personId, phone = "", specialties = emptyList(), availability = ""))
////                        }
////                    }
////                }
//                is UIEvent.DeletePerson -> {
//                    repository.deletePerson(personId)
//                }
//                else -> {}
//            }
//        }
//    }
//
//    fun edit(id: Long) = viewModelScope.launch {
//        _id.value = id
//    }
//
//    fun create() = viewModelScope.launch {
//        _id.value = 0L
//    }
//}
//
//
