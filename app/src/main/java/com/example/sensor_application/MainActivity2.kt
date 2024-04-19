package com.example.sensor_application

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {
    lateinit var lineGraphView: GraphView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val db=database.getinst(applicationContext)
        val rotdao=db.getRotDao()
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        var tim_series:List<String>?=null;


        var plot_series:List<Float>?=null;
        lineGraphView = findViewById(R.id.idGraphView)
        GlobalScope.launch {
            tim_series=rotdao.gettime()
            Log.d("time_series", tim_series!![0].toString())
            if(intent.getStringExtra("button")=="x"){
                plot_series=rotdao.getxdata()

            }
            else if(intent.getStringExtra("button")=="y"){
                plot_series=rotdao.getydata()
            }

            else{
                plot_series=rotdao.getzdata()
            }


//            Try alternative method to get datapoints


            val dataPoints = tim_series!!.mapIndexed { index, time ->
                    DataPoint(time.toDouble(), plot_series!![index].toDouble())
            }.toTypedArray()


            val series = LineGraphSeries(dataPoints)


            lineGraphView.addSeries(series)


            lineGraphView.viewport.apply {

                setScalableY(true)
                isScalable=true
                setScrollableY(true)
                isScrollable=true



            }

            lineGraphView.gridLabelRenderer.apply {
                isHorizontalLabelsVisible=false
                horizontalAxisTitle="Time"
                verticalAxisTitle=intent.getStringExtra("button")+"-orientation"
            }

        }










    }
}