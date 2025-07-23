package com.example.nhamngocduc.ui.editor.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    submitCondition: Boolean,
    onSubmitClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(Color.Black),
        shape = ShapeDefaults.Medium,
        onClick = {
            // Log.d("ONE TWO THREE", "$submitCondition")
            if (submitCondition) {
                onSubmitClick()
            }
        }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = "Submit",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}