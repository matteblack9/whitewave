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

    @Column(name = "waves")
    var waves: Double,

    @Column(name = "swell1")
    var swell1: Double,

    @Column(name = "swell2")
    var swell2: Double,

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
        var timeStamp: String
) : Serializable