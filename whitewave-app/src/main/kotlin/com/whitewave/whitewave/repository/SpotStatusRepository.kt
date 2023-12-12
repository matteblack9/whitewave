package com.whitewave.whitewave.repository

import com.whitewave.whitewave.model.SpotStatus
import com.whitewave.whitewave.model.SpotStatusId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpotStatusRepository : JpaRepository<SpotStatus, SpotStatusId> {
    fun findBySpotStatusId(spotStatusId: SpotStatusId): SpotStatus
}