package com.rcudev.player_service.domain.repository

import com.rcudev.player_service.domain.models.RfMedia

interface PlayerMediaRepository {

    fun addItem(rfMedia: RfMedia)

}