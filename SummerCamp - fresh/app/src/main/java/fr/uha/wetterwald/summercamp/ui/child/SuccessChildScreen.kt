package fr.uha.wetterwald.summercamp.ui.child

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fr.uha.hassenforder.android.ui.field.OutlinedIntFieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedPictureFieldWrapper
import fr.uha.hassenforder.android.ui.field.OutlinedTextFieldWrapper
import fr.uha.hassenforder.android.ui.field.PictureFieldConfig
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.TeamFileProvider
import fr.uha.wetterwald.summercamp.ui.field.OutlinedGenderFieldWrapper

@Composable
fun SuccessChildScreen(
    supervisor: ChildViewModel.UIState,
    send: (ChildViewModel.UIEvent) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        OutlinedTextFieldWrapper(
            field = supervisor.firstnameState,
            onValueChange = { send(ChildViewModel.UIEvent.FirstnameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.firstname,
        )
        OutlinedTextFieldWrapper(
            field = supervisor.lastnameState,
            onValueChange = { send(ChildViewModel.UIEvent.LastnameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.lastname,
        )
        OutlinedIntFieldWrapper(
            field = supervisor.ageState,
            onValueChange = { send(ChildViewModel.UIEvent.AgeChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.age,
        )
        OutlinedGenderFieldWrapper(
            field = supervisor.genderState,
            onValueChange = { send(ChildViewModel.UIEvent.GenderChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.gender,
        )
        OutlinedPictureFieldWrapper(
            field = supervisor.pictureState,
            onValueChange = { send(ChildViewModel.UIEvent.PictureChanged(it)) },
            config = PictureFieldConfig(
                galleryFilter = "image/*",
                newImageUriProvider = { TeamFileProvider.getImageUri(context) },
            ),
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.picture,
        )
        OutlinedTextFieldWrapper(
            field = supervisor.parentPhoneState,
            onValueChange = { send(ChildViewModel.UIEvent.ParentPhoneChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            labelId = R.string.phone,
        )
    }
}