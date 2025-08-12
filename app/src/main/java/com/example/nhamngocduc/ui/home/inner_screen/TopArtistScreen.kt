package com.example.nhamngocduc.ui.home.inner_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.domain.model.ArtistDomain
import com.example.nhamngocduc.ui.home.components.ArtistCard
import com.example.nhamngocduc.ui.home.components.TopNavBar

@Composable
fun TopArtistScreen(
    modifier: Modifier = Modifier,
    artists: List<ArtistDomain>,
    onNavigateBack: () -> Unit
) {
    Column(modifier = modifier) {
        TopNavBar(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            title = "Top Album",
            onClickBack = onNavigateBack,
        )
        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(artists) { artist ->
                ArtistCard(
                    modifier = Modifier.fillMaxWidth(),
                    artist = artist,
                )
            }
            item {
                Spacer(modifier = Modifier.height(128.dp))
            }
        }
    }
}