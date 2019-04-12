package com.example.vncalendar

import android.app.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_actividad.*
import java.util.*
import android.content.Context
import android.widget.TextView


class AgregarActividad : AppCompatActivity() {


    //Nótese que si se cambia algo en la clase MiActividad aquí también se debe considerar el cambio
    companion object {
        const val EXTRA_ID = "com.example.vncalendar.EXTRA_ID"
        const val EXTRA_TITULO_ACTIVIDAD = "com.example.vncalendar.EXTRA_TITULO_ACTIVIDAD"
        const val EXTRA_DESCRIPCION_ACTIVIDAD = "com.example.vncalendar.EXTRA_DESCRIPCION_ACTIVIDAD"
        const val EXTRA_TIPO_ACTIVIDAD = "com.example.vncalendar.EXTRA_TIPO_ACTIVIDAD"
        //const val EXTRA_TIPO_VIBRACION = "com.example.vncalendar.EXTRA_TIPO_VIBRACION"
        const val EXTRA_DIA_ACTIVIDAD = "com.example.vncalendar.EXTRA_DIA_ACTIVIDAD"
        const val EXTRA_MES_ACTIVIDAD = "com.example.vncalendar.EXTRA_MES_ACTIVIDAD"
        const val EXTRA_ANO_ACTIVIDAD = "com.example.vncalendar.EXTRA_ANO_ACTIVIDAD"
        const val EXTRA_HORA_ACTIVIDAD= "com.example.vncalendar.EXTRA_HORA_ACTIVIDAD"
        const val EXTRA_MINUTO_ACTIVIDAD= "com.example.vncalendar.EXTRA_MINUTO_ACTIVIDAD"
        const val EXTRA_PRIORITY = "com.example.vncalendar.EXTRA_PRIORITY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_actividad)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 3


        if (intent.hasExtra(EXTRA_ID)){

            title = "Editar Mi Actividad"

            edit_text_titulo_actividad.setText(intent.getStringExtra(EXTRA_TITULO_ACTIVIDAD))
            edit_text_descripción_actividad.setText(intent.getStringExtra(EXTRA_DESCRIPCION_ACTIVIDAD))

            if (intent.getIntExtra(EXTRA_TIPO_ACTIVIDAD,1) == 1){
                deporte.isChecked = true
            }else if (intent.getIntExtra(EXTRA_TIPO_ACTIVIDAD,1) == 2){
                estudio.isChecked = true
            }//else if (intent.getIntExtra(EXTRA_TIPO_ACTIVIDAD,1)==3){
                //mandado.isChecked = true
            //}

//            if (intent.getIntExtra(EXTRA_TIPO_VIBRACION,1) == 1){
//                vibracion_larga.isChecked = true
//            }else if (intent.getIntExtra(EXTRA_TIPO_VIBRACION,1) == 2){
//                vibracion_corta.isChecked = true
//            }

            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY,1)
        } else  {
            title = "Agregar Contacto"
        }

        val botonGuardarActividad = findViewById<Button>(R.id.button_guardar_actividad)
        val botonRegresaActividades = findViewById<Button>(R.id.button_regresar_a_actividades)
        val botonSeleccionarFecha= findViewById<ImageButton>(R.id.button_pickdate)
        val botonGrabarAudio=findViewById<ImageButton>(R.id.imageButton_grabar_audio)

        val myDateYear =findViewById<TextView>(R.id.myDateYear)
        val myDateMonth = findViewById<TextView>(R.id.myDateMonth)
        val myDateDay = findViewById<TextView>(R.id.myDateDay)
        val myTimeHour = findViewById<TextView>(R.id.myTimeHour)
        val myTimeMinute = findViewById<TextView>(R.id.myTimeMinute)


        botonGrabarAudio.setOnClickListener{
            Toast.makeText(this,"Lanzar grabacion de audio",Toast.LENGTH_SHORT).show()
            // se grabara el audio o importara
        }


        fun setAlarm(myCalendar: Calendar){
            //metodo que programa la alarma en el fondo
            Toast.makeText(this,"Configurando notificacion",Toast.LENGTH_SHORT).show()
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,myCalendar.timeInMillis,pendingIntent)


        }


        botonGuardarActividad.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                //Funcion que intenta guardar la informacion en el formulario
                saveMiActividad()
            }
        })

        //Funcion que abre la activity de mis actividades
        botonRegresaActividades.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {

                finish()
            }
        })

        botonSeleccionarFecha.setOnClickListener{
            //Toast.makeText(this,"Lanzar el calendario",Toast.LENGTH_SHORT).show()
            //pickTime()

            //selecciona una hora para la alarma
            val cal = Calendar.getInstance()

            val timeSetListener= TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                Toast.makeText(this,"Hora : "+hour+" Minuto : "+minute,Toast.LENGTH_SHORT).show()
                myTimeHour.setText(hour.toString())
                myTimeMinute.setText(minute.toString())

                //llama al metodo que configura la alarma


            }
            TimePickerDialog(this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false).show()
            setAlarm(cal)

            val cal2 = Calendar.getInstance()

            val timeSetListener2= DatePickerDialog.OnDateSetListener { DatePicker, year, month, day ->
                cal2.set(Calendar.YEAR, year)
                cal2.set(Calendar.MONTH, month)
                cal2.set(Calendar.DAY_OF_MONTH,day)
                Toast.makeText(this,"YEAR : "+year+" MONTH : "+month+ " DAY :"+ day,Toast.LENGTH_SHORT).show()
                //llama al metodo que configura la alarma
                myDateYear.setText(year.toString())
                myDateMonth.setText(month.toString())
                myDateDay.setText(day.toString())


            }
            DatePickerDialog(this,timeSetListener2,cal2.get(Calendar.YEAR),cal2.get(Calendar.WEEK_OF_YEAR),cal2.get(Calendar.DAY_OF_WEEK)).show()
            setAlarm(cal2)


        }

        fun pickTime(){

            val cal = Calendar.getInstance()
            val timeSetListener= TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                Toast.makeText(this,"Hora : "+hour+" Minuto : "+minute,Toast.LENGTH_SHORT).show()
                setAlarm(cal)

        }

        }

        fun launchPickDate(){


        }
    }

    //Debo colocar el boton que guarda el contacto

    //Funcion que abre la activity de MostrarMenu


    private fun saveMiActividad(){
        //no agregar actividades si los campos estan vacios
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
//            if (vibracion_larga.isChecked){
//                putExtra(EXTRA_TIPO_VIBRACION,1)
//            }else if (vibracion_corta.isChecked){
//                putExtra(EXTRA_TIPO_VIBRACION,2)
//            }



            //Fecha solo para pruebas

            putExtra(EXTRA_DIA_ACTIVIDAD,myDateDay.getText())
            putExtra(EXTRA_MES_ACTIVIDAD,myDateMonth.getText())
            putExtra(EXTRA_ANO_ACTIVIDAD,myDateYear.getText())


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
