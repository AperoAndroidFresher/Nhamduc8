package com.example.nhamngocduc.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.domain.model.AlbumDomain

@Composable
fun TopAlbumsSection(
    modifier: Modifier = Modifier,
    list: List<AlbumDomain>,
    onSeeAllClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionHeader(
            modifier = Modifier.fillMaxWidth(),
            title = "Top Albums",
            onSeeAllClick = onSeeAllClick
        )
        Spacer(modifier = Modifier.height(12.dp))

        AlbumGrid(
            modifier = Modifier.fillMaxWidth(),
            albums = list
        )
    }
}

@Composable
fun AlbumGrid(
    modifier: Modifier = Modifier,
    albums: List<AlbumDomain>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        albums.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { album ->
                    AlbumCard(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        album = album
                    )
                }
            }
        }
    }
}