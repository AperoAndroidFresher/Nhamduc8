package com.example.nhamngocduc.ui.home.inner_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.domain.model.AlbumDomain
import com.example.nhamngocduc.ui.home.components.AlbumCard
import com.example.nhamngocduc.ui.home.components.TopNavBar

@Composable
fun TopAlbumScreen(
    albums: List<AlbumDomain>,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
) {
    Column(modifier = modifier) {
        TopNavBar(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            title = "Top Album",
            onClickBack = onNavigateBack,
        )
        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(albums) { album ->
                AlbumCard(
                    modifier = Modifier.fillMaxWidth(),
                    album = album,
                )
            }
            item {
                Spacer(modifier = Modifier.height(128.dp))
            }
        }
    }
}