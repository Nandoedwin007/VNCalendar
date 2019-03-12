package com.example.vncalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AgregarActividad : AppCompatActivity() {

//    intent.putExtra(AgregarActividad.EXTRA_ID,miActividad.id)
//    intent.putExtra(AgregarActividad.EXTRA_TITULO_ACTIVIDAD,miActividad.titulo)
//    intent.putExtra(AgregarActividad.EXTRA_DESCRIPCION_ACTIVIDAD,miActividad.descipcion)
//    intent.putExtra(AgregarActividad.EXTRA_TIPO_ACTIVIDAD,miActividad.tipoActividad)
//    intent.putExtra(AgregarActividad.EXTRA_TIPO_VIBRACION,miActividad.tipoVibracion)
//    intent.putExtra(AgregarActividad.EXTRA_DIA_ACTIVIDAD,miActividad.diaActividad)
//    intent.putExtra(AgregarActividad.EXTRA_MES_ACTIVIDAD,miActividad.mesActividad)
//    intent.putExtra(AgregarActividad.EXTRA_FECHA_ACTIVIDAD,miActividad.fecha)
//    intent.putExtra(AgregarActividad.EXTRA_PRIORITY,miActividad.priority)


    //Nótese que si se cambia algo en la clase MiActividad aquí también se debe considerar el cambio
    companion object {
        const val EXTRA_ID = "com.example.vncalendar.EXTRA_ID"
        const val EXTRA_TITULO_ACTIVIDAD = "com.example.vncalendar.EXTRA_TITULO_ACTIVIDAD"
        const val EXTRA_DESCRIPCION_ACTIVIDAD = "com.example.vncalendar.EXTRA_DESCRIPCION_ACTIVIDAD"
        const val EXTRA_TIPO_ACTIVIDAD = "com.example.vncalendar.EXTRA_TIPO_ACTIVIDAD"
        const val EXTRA_TIPO_VIBRACION = "com.example.vncalendar.EXTRA_TIPO_VIBRACION"
        const val EXTRA_DIA_ACTIVIDAD = "com.example.vncalendar.EXTRA_DIA_ACTIVIDAD"
        const val EXTRA_MES_ACTIVIDAD = "com.example.vncalendar.EXTRA_MES_ACTIVIDAD"
        const val EXTRA_FECHA_ACTIVIDAD = "com.example.vncalendar.EXTRA_FECHA_ACTIVIDAD"
        const val EXTRA_PRIORITY = "com.example.vncalendar.EXTRA_PRIORITY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_actividad)
    }
}
