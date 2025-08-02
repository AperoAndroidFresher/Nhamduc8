package com.example.nhamngocduc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.util.DropDownOption

@Composable
fun DropDownOptions(
    modifier: Modifier = Modifier,
    isDropdownMenuVisible: Boolean,
    dropDownItems: List<DropDownOption>,
    dpOffset: DpOffset = DpOffset(0.dp, 0.dp),
    onDropDownMenuVisibilityChange: () -> Unit,
    onOptionSelected: (DropDownOption) -> Unit
) {
    DropdownMenu(
        expanded = isDropdownMenuVisible,
        onDismissRequest = onDropDownMenuVisibilityChange,
        offset = dpOffset,
        shape = RoundedCornerShape(12.dp),
        containerColor = Color(0xFF292929)
    ) {
        dropDownItems.forEachIndexed { i, dropDownOption ->
            DropdownMenuItem(
                onClick = {
                    onDropDownMenuVisibilityChange()
                    onOptionSelected(dropDownOption)
                },
                text = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = dropDownOption.label,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                    )

                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(dropDownOption.iconResId),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            )
            if (i < dropDownItems.size - 1) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Gray,
                                    Color.Transparent,
                                )
                            )
                        )
                )
            }
        }
    }
}