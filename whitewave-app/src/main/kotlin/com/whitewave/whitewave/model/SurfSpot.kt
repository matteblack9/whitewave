package com.whitewave.whitewave.model

import jakarta.persistence.*


@Entity
@Table(name = "surf_spot")
class SurfSpot (
    @Id
    @Column(name = "spot_name")
    var spotName: String,

    @Column(name = "latitude")
    var latitude: Double,

    @Column(name = "longitude")
    var longitude: Double
)