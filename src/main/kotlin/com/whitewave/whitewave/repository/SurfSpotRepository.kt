package com.whitewave.whitewave.repository

import com.whitewave.whitewave.model.SurfSpot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SurfSpotRepository : JpaRepository<SurfSpot, String> {
    fun findBySpotName(spotName: String) : SurfSpot
}