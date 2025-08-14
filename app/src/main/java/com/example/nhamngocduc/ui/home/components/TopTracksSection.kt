package com.example.nhamngocduc.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.domain.model.TrackDomain

@Composable
fun TopTracksSection(
    modifier: Modifier = Modifier,
    list: List<TrackDomain>,
    onSeeAllClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionHeader(
            modifier = Modifier.fillMaxWidth(),
            title = "Top Tracks",
            onSeeAllClick = onSeeAllClick
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(list) { index, track ->
                TrackCard(
                    modifier = Modifier
                        .width(180.dp)
                        .height(196.dp),
                    track = track,
                    index = index
                )
            }
        }
    }
}