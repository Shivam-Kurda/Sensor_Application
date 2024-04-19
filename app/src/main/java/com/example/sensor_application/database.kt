package com.example.sensor_application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [rot_entity :: class], version = 1)

abstract class database : RoomDatabase() {
    abstract fun getRotDao() : RotDao

    companion object{


        private var inst : database?=null
        fun getinst(cont: Context) : database{
            synchronized(this) {


                if (inst == null) {
                    inst = Room.databaseBuilder(cont.applicationContext,database::class.java,"database").build()





                }
                return inst!!
            }

        }

    }

}