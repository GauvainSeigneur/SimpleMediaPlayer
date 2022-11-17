package com.rcudev.player_service.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import com.rcudev.player_service.data.controller.MediaStateController
import com.rcudev.player_service.data.controller.PlayerControlEventController
import com.rcudev.player_service.data.controller.PlayerMediaController
import com.rcudev.player_service.data.controller.PlayerServiceController
import com.rcudev.player_service.data.handler.PlayerStateHandler
import com.rcudev.player_service.domain.repository.MediaStateRepository
import com.rcudev.player_service.domain.repository.PlayerControlEventRepository
import com.rcudev.player_service.domain.repository.PlayerMediaRepository
import com.rcudev.player_service.domain.repository.PlayerServiceRepository
import com.rcudev.player_service.domain.repository.PlayerStateRepository
import com.rcudev.player_service.data.mapper.MediaItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SimpleMediaModule {

    @Provides
    @Singleton
    fun provideAudioAttributes(): AudioAttributes =
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
            .setUsage(C.USAGE_MEDIA)
            .build()

    @Provides
    @Singleton
    fun providePlayer(
        @ApplicationContext context: Context,
        audioAttributes: AudioAttributes
    ): ExoPlayer =
        ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .setTrackSelector(DefaultTrackSelector(context))
            .build()

    @Provides
    @Singleton
    fun provideMediaSession(
        @ApplicationContext context: Context,
        player: ExoPlayer
    ): MediaSession =
        MediaSession.Builder(context, player).build()

    @Provides
    fun provideMediaStateRepository(
        player: ExoPlayer
    ): MediaStateRepository = MediaStateController(player)

    @Provides
    fun provideControlEventRepository(
        player: ExoPlayer
    ): PlayerControlEventRepository = PlayerControlEventController(player)


    @Provides
    fun providePlayerMediaRepository(
        player: ExoPlayer,
        mediaItemMapper: MediaItemMapper
    ): PlayerMediaRepository = PlayerMediaController(player, mediaItemMapper)


    @Provides
    fun providePlayerServiceRepository(
        @ApplicationContext context: Context,
    ): PlayerServiceRepository = PlayerServiceController(context)


    @Provides
    fun providePlayerStateRepository(
        player: ExoPlayer,
        mediaStateRepository: MediaStateRepository,
    ): PlayerStateRepository = PlayerStateHandler(player, mediaStateRepository)

}