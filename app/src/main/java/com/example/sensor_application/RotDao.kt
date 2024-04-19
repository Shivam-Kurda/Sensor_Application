package com.example.sensor_application

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RotDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertdata(inst:rot_entity)

    @Query("Delete from rot_entity ")
    suspend fun deletedb()

    @Query("SELECT * FROM rot_entity")
    suspend fun getAllRotEntities(): List<rot_entity>

    @Query("SELECT x_coor from rot_entity")
    suspend fun getxdata() : List<Float>

    @Query("SELECT y_coor from rot_entity")
    suspend fun getydata() : List<Float>

    @Query("SELECT z_coor from rot_entity")
     suspend fun getzdata() : List<Float>

    @Query("SELECT time from rot_entity")
    suspend fun gettime() : List<String>
}