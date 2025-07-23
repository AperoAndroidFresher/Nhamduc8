package com.example.nhamngocduc.ui.editor.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.nhamngocduc.R

@Composable
fun EditScreenDialog(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    visibleDialogContent: Boolean
) {
    if (showDialog) {
        // Dialog
        Dialog(
            onDismissRequest = {}
        ) {
            AnimatedVisibility(
                visible = visibleDialogContent,
                enter = scaleIn(animationSpec = tween(500)),
                exit = scaleOut(animationSpec = tween(500))
            ) {
                Surface(
                    modifier = modifier,
                    shape = MaterialTheme.shapes.large,
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(128.dp),
                            painter = painterResource(R.drawable.ic_check),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Success!",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = Color(0xFF25AE88),
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Your information has been updated!",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}