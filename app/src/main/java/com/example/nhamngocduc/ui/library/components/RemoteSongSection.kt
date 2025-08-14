package com.example.nhamngocduc.ui.library.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nhamngocduc.R
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.ui.components.animation.LottieAnimation
import com.example.nhamngocduc.ui.library.LibraryContract
import com.example.nhamngocduc.util.DropDownOption

@Composable
fun RemoteSongSection(
    modifier: Modifier = Modifier,
    remoteSongState: LibraryContract.RemoteSongsUiState,
    onRetry: () -> Unit,
    onOptionSelected: (DropDownOption, Song) -> Unit
) {
    when (remoteSongState) {
        is LibraryContract.RemoteSongsUiState.Error ->
            ErrorScreen(modifier = modifier, text = remoteSongState.message) { onRetry() }

        LibraryContract.RemoteSongsUiState.Loading ->
            LottieAnimation(modifier = modifier, resId = R.raw.lottie_remote_item_loading)

        is LibraryContract.RemoteSongsUiState.Error -> ErrorScreen(modifier = modifier, text = remoteSongState.message) { onRetry() }
        LibraryContract.RemoteSongsUiState.Loading -> LottieAnimation(modifier = modifier, resId = R.raw.lottie_remote_item_loading)
    }
}
