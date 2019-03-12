package com.example.vncalendar.data

import androidx.room.Entity
import androidx.room.PrimaryKey


//Basado en el código del repositorio https://github.com/berkeatac/Notes-App
@Entity(tableName="miactividad_table")
data class MiActividad(
    var titulo:String,
    var descipcion: String,
    var tipoActividad: Int, //Variable que describe si es actividad de ejercio o estudio, etc.
    var tipoVibracion: Int, //Variable que define el tipo de vibración que sonará en la notifiación
    var diaActividad: Int,
    var mesActividad: Int,
    var fecha:String,
    var priority:Int
) {

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

}