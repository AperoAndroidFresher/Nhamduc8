package com.example.nhamngocduc.ui.editor.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.ScaledIconButton

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    editable: Boolean,
    size: Dp,
    borderColor: Color
) {
    var selectedImageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri
        } else {

        }
    }

    Row(
        modifier = modifier.fillMaxWidth().height(size + size / 4),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(size)
                    .clip(RoundedCornerShape(percent = 50))
                    .border(2.dp, borderColor, shape = RoundedCornerShape(percent = 50)),
                model = ImageRequest.Builder(context)
                    .data(selectedImageUri)
                    .size(300, 300)
                    .crossfade(true)
                    .error(R.drawable.model)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            ScaledIconButton(
                modifier = Modifier.padding(top = size / 2 + size / 4),
                enabled = editable,
                resId = R.drawable.ic_camera,
                buttonColor = MaterialTheme.colorScheme.background.copy(0.8f),
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        }
    }
}