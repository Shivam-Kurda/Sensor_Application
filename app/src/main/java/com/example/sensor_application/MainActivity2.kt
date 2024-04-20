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


        var tim_series:List<String>?=null;


        var plot_series:List<Float>?=null;
        lineGraphView = findViewById(R.id.idGraphView)
        GlobalScope.launch {
            tim_series=rotdao.gettime()

            if(intent.getStringExtra("button")=="x"){
                plot_series=rotdao.getxdata()

            }
            else if(intent.getStringExtra("button")=="y"){
                plot_series=rotdao.getydata()
            }

            else{
                plot_series=rotdao.getzdata()
            }




            var dtpointarr= arrayOf<DataPoint>()

            for (i in 0..<tim_series!!.size){
                dtpointarr+=DataPoint(tim_series!![i].toDouble(),plot_series!![i].toDouble())
            }




            val series = LineGraphSeries(dtpointarr)


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