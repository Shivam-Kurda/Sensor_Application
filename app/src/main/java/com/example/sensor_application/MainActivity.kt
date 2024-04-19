package com.example.sensor_application

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sensor_application.ui.theme.Sensor_ApplicationTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(),SensorEventListener {
    lateinit var  viewModel:Main_View
    var appcontext:Context?=null
    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel=ViewModelProvider(this)[Main_View :: class.java]
        var sens_Manag=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        var accl=sens_Manag.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        accl.also {
            sens_Manag.registerListener(this,it,SensorManager.SENSOR_DELAY_FASTEST)
        }
        super.onCreate(savedInstanceState)
        setContent {
            appcontext= LocalContext.current
            Sensor_ApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android", viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()


    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val x_new= p0?.values?.get(0)!!
        val y_new=p0?.values?.get(1)!!
        val z_new=p0?.values?.get(2)!!
        if(appcontext!=null){
            viewModel.update_coord(x_new,y_new,z_new,appcontext !!)

        }

        Log.d("sensor","Postion changes")
//        viewModel.x=x_new
//        viewModel.y=y_new
//        viewModel.z=z_new


    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}

@Composable
fun Greeting(name: String,viewModel:Main_View) {

    val x_cor by viewModel.x_mut.observeAsState()
    val y_cor by viewModel.y_mut.observeAsState()
    val z_cor by viewModel.z_mut.observeAsState()




    val cont= LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "x is $x_cor")
        Text(text = "y is $y_cor")
        Text(text = "z is $z_cor")

        Button(onClick = {
            val int= Intent(cont,MainActivity2 :: class.java)
            int.putExtra("button","x")
            cont.startActivity(int)
        }) {
            Text(text = "Get x-orientation plot")
        }

        Button(onClick = {
            val int= Intent(cont,MainActivity2 :: class.java)
            int.putExtra("button","y")
            cont.startActivity(int)
        }) {
            Text(text = "Get y-orientation plot")
        }

        Button(onClick = {
            val int= Intent(cont,MainActivity2 :: class.java)
            int.putExtra("button","z")
            cont.startActivity(int)
        }) {
            Text(text = "Get z-orientation plot")
        }
        Button(onClick = {
            val db=database.getinst(cont.applicationContext)
            val rotdao=db.getRotDao()
            GlobalScope.launch {
                rotdao.deletedb()
            }
        }) {
            Text(text = "Delete Data")
        }

    }



}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    Sensor_ApplicationTheme {
//        Greeting("Android")
//    }
//}