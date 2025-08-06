package com.example.nhamngocduc.ui.login_signup.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.button.ScaledIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    topBarState: Boolean,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            ScaledIconButton(
                enabled = topBarState,
                size = 48.dp,
                resId = R.drawable.ic_back,
                onClick = onBackClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}