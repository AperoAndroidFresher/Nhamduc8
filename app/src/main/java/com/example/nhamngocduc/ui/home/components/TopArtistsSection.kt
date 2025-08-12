package com.example.nhamngocduc.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.domain.model.ArtistDomain

@Composable
fun TopArtistsSection(
    modifier: Modifier = Modifier,
    artists: List<ArtistDomain>,
    onSeeAllClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        SectionHeader(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Top Artist",
            onSeeAllClick = onSeeAllClick
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(artists) { artist ->
                ArtistCard(
                    modifier = Modifier
                        .width(180.dp)
                        .height(196.dp),
                    artist = artist
                )
            }
        }
    }
}