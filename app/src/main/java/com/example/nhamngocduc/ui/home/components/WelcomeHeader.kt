package com.example.nhamngocduc.ui.home.components

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.button.CoilImageButton
import com.example.nhamngocduc.ui.components.button.ScaledIconButton

@Composable
fun WelcomeHeader(
    modifier: Modifier = Modifier,
    userName: String,
    profileImageUri: Uri?,
    onProfileImageClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoilImageButton(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(percent = 50))
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(percent = 50)
                ),
            imageUri = profileImageUri,
            onClick = onProfileImageClick
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                "Welcome back !",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                )
            )
            Text(
                userName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                )
            )
        }
        Spacer(Modifier.weight(1f))
        ScaledIconButton(
            modifier = Modifier,
            resId = R.drawable.ic_settings,
            onClick = onSettingsClick
        )
    }
    Spacer(Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.ic_crown),
            contentDescription = null,
            tint = Color(0xFFFFF500)
        )
        Text(
            text = " Rankings",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold
            )
        )
    }
}