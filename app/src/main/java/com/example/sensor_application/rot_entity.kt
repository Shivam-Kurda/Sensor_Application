package com.example.sensor_application

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class rot_entity(
    @PrimaryKey
    val time:String,
    val x_coor:Float,
    val y_coor:Float,
    val z_coor:Float
)
