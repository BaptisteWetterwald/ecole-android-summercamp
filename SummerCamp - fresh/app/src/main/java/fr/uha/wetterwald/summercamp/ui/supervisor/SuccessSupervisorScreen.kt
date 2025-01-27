package fr.uha.wetterwald.summercamp.ui.person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fr.uha.hassenforder.android.ui.field.Orientation
import fr.uha.hassenforder.android.ui.field.OutlinedEnumRadioGroupWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedIntFieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedPictureFieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedTextFieldWrapper
import fr.uha.hassenforder.android.ui.field.PictureFieldConfig
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.TeamFileProvider
import fr.uha.wetterwald.summercamp.model.Gender
import fr.uha.wetterwald.summercamp.ui.field.OutlinedGenderFieldWrapper
import fr.uha.wetterwald.summercamp.ui.field.OutlinedSpecialtiesFieldWrapper

@Composable
fun SuccessSupervisorScreen(
    supervisor: SupervisorViewModel.UIState,
    send : (SupervisorViewModel.UIEvent) -> Unit,
) {
    val context = LocalContext.current

    Column()
    {
        OutlinedTextFieldWrapper(
            field = supervisor.firstnameState,
            onValueChange = { send(SupervisorViewModel.UIEvent.FirstnameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.firstname,
        )
        OutlinedTextFieldWrapper(
            field = supervisor.lastnameState,
            onValueChange = { send(SupervisorViewModel.UIEvent.LastnameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.lastname,
        )
        OutlinedIntFieldWrapper(
            field = supervisor.ageState,
            onValueChange = { send(SupervisorViewModel.UIEvent.AgeChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.age,
        )
        OutlinedGenderFieldWrapper(
            field = supervisor.genderState,
            onValueChange = { send(SupervisorViewModel.UIEvent.GenderChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.gender,
        )
        OutlinedPictureFieldWrapper(
            field = supervisor.pictureState,
            onValueChange = { send(SupervisorViewModel.UIEvent.PictureChanged(it)) },
            config = PictureFieldConfig(
                galleryFilter = "image/*",
                newImageUriProvider = { TeamFileProvider.getImageUri(context) },
            ),
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.picture,
        )
//        OutlinedTextFieldWrapper(
//            field = supervisor.phoneState,
//            onValueChange = { send(SupervisorViewModel.UIEvent.PhoneChanged(it)) },
//            modifier = Modifier.fillMaxWidth(),
//            labelId = R.string.phone,
//        )
//        OutlinedSpecialtiesFieldWrapper(
//            field = supervisor.specialtiesState,
//            onValueChange = { send(SupervisorViewModel.UIEvent.SpecialtiesChanged(it)) },
//            modifier = Modifier.fillMaxWidth(),
//            labelId = R.string.specialties,
//        )
        OutlinedTextFieldWrapper(
            field = supervisor.availabilityState,
            onValueChange = { send(SupervisorViewModel.UIEvent.AvailabilityChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.availability,
        )
    }
}