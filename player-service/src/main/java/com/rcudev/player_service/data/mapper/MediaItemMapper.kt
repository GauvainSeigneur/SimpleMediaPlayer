package com.rcudev.player_service.data.mapper

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.rcudev.player_service.domain.models.RfMedia

class MediaItemMapper {

    fun mapToMediaItem(rfMedia: RfMedia): MediaItem {
        return MediaItem.Builder()
            .setMediaId(rfMedia.id)
            .setUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                    .setArtworkUri(Uri.parse("https://i.pinimg.com/736x/4b/02/1f/4b021f002b90ab163ef41aaaaa17c7a4.jpg"))
                    .setAlbumTitle(rfMedia.albumTitle)
                    .setDisplayTitle("Song 1")
                    .build()
            ).build()
    }

}