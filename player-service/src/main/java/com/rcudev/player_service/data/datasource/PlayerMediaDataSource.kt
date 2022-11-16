package com.rcudev.player_service.data.datasource

import com.rcudev.player_service.domain.models.RfMedia

interface PlayerMediaDataSource {
    fun getCurrentItem(): RfMedia?
}