package com.example.sensor_application

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import android.provider.Settings.System
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class Main_View : ViewModel() {

     val x_mut = MutableLiveData<Float>(0f)
     val y_mut = MutableLiveData<Float>(0f)
     val z_mut = MutableLiveData<Float>(0f)


    private var db: database?=null
    fun update_coord(x_new: Float,y_new:Float,z_new:Float,cont:Context){
        x_mut.value=x_new
        y_mut.value = y_new
        z_mut.value = z_new
        updatedb(x_new,y_new,z_new,cont)

    }

    private fun updatedb(x: Float, y: Float, z: Float,cont: Context) {
        if(db==null){
            db=database.getinst(cont.applicationContext)

        }

        val rotdao= db!!.getRotDao()
        val time=Calendar.getInstance().timeInMillis
        GlobalScope.launch {
            rotdao.insertdata(rot_entity(time.toString(),x,y,z))

        }

    }
}