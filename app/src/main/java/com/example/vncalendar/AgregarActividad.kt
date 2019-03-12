package com.example.vncalendar

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_actividad.*

class AgregarActividad : AppCompatActivity() {


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

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        if (intent.hasExtra(EXTRA_ID)){

            title = "Editar Mi Actividad"

            edit_text_titulo_actividad.setText(intent.getStringExtra(EXTRA_TITULO_ACTIVIDAD))
            edit_text_descripción_actividad.setText(intent.getStringExtra(EXTRA_DESCRIPCION_ACTIVIDAD))

            if (intent.getIntExtra(EXTRA_TIPO_ACTIVIDAD,1) == 1){
                deporte.isChecked = true
            }else if (intent.getIntExtra(EXTRA_TIPO_ACTIVIDAD,1) == 2){
                estudio.isChecked = true
            }

            if (intent.getIntExtra(EXTRA_TIPO_VIBRACION,1) == 1){
                vibracion_larga.isChecked = true
            }else if (intent.getIntExtra(EXTRA_TIPO_VIBRACION,1) == 2){
                vibracion_corta.isChecked = true
            }

            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY,1)
        } else  {
            title = "Agregar Contacto"
        }

        val botonGuardarActividad = findViewById<Button>(R.id.button_guardar_actividad)
        val botonRegresaActividades = findViewById<Button>(R.id.button_regresar_a_actividades)

        //Funcion que abre la activity de MostrarMenu
        botonGuardarActividad.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                // Handler code here.
                saveMiActividad()
            }
        })

        //Funcion que abre la activity de MostrarMenu
        botonRegresaActividades.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                // Handler code here.
                finish()
            }
        })
    }

    //Debo colocar el boton que guarda el contacto

    //Funcion que abre la activity de MostrarMenu


    private fun saveMiActividad(){
        if (edit_text_titulo_actividad.text.toString().trim().isBlank() ||
                edit_text_descripción_actividad.text.toString().trim().isBlank()){
            Toast.makeText(this, "No se puede agregar una nueva actividad", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_TITULO_ACTIVIDAD,edit_text_titulo_actividad.text.toString())
            putExtra(EXTRA_DESCRIPCION_ACTIVIDAD,edit_text_descripción_actividad.text.toString())
            //Extra para tipo de actividad
            if (deporte.isChecked){
                putExtra(EXTRA_TIPO_ACTIVIDAD,1)
            }else if (estudio.isChecked){
                putExtra(EXTRA_TIPO_ACTIVIDAD,2)
            }
            //Extra para tipo de vibraación
            if (vibracion_larga.isChecked){
                putExtra(EXTRA_TIPO_VIBRACION,1)
            }else if (vibracion_corta.isChecked){
                putExtra(EXTRA_TIPO_VIBRACION,2)
            }



            //Fecha solo para pruebas

            putExtra(EXTRA_DIA_ACTIVIDAD,2)
            putExtra(EXTRA_MES_ACTIVIDAD,2)
            putExtra(EXTRA_FECHA_ACTIVIDAD,"Fecha")

            //Extra priority
            putExtra(EXTRA_PRIORITY,number_picker_priority.value)

            if (intent.getIntExtra(EXTRA_ID,-1) != -1) {
                putExtra(EXTRA_ID,intent.getIntExtra(EXTRA_ID,-1))
            }

        }

        setResult(Activity.RESULT_OK,data)
        finish()
    }
}
