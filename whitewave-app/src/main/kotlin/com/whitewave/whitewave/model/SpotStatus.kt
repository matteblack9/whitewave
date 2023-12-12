package com.whitewave.whitewave.model

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import java.util.Date

@Entity
@Table(name = "spot_status")
class SpotStatus (
    @EmbeddedId
    var spotStatusId: SpotStatusId,

    @Column(name = "waves_direction")
    var waves_direction: Double,

    @Column(name = "waves_period")
    var waves_period: Double,

    @Column(name = "waves_height")
    var waves_height: Double,

    @Column(name = "wind_waves_direction")
    var wind_waves_direction: Double,

    @Column(name = "wind_waves_period")
    var wind_waves_period: Double,

    @Column(name = "wind_waves_height")
    var wind_waves_height: Double,

    @Column(name = "swell1_direction")
    var swell1_direction: Double,

    @Column(name = "swell1_period")
    var swell1_period: Double,

    @Column(name = "swell1_height")
    var swell1_height: Double,

    @Column(name = "swell2_direction")
    var swell2_direction: Double,

    @Column(name = "swell2_period")
    var swell2_period: Double,

    @Column(name = "swell2_height")
    var swell2_height: Double,

    @Column(name = "so2sm")
    var so2sm: Double,

    @Column(name = "dustsm")
    var dustsm: Double
)

@Embeddable
class SpotStatusId (
        @Column(name = "spot_name")
        var spotName: String,

        @Column(name = "time_stamp")
        var timeStamp: Date
) : Serializable