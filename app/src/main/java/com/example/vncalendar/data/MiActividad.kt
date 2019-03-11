package com.example.vncalendar.data

import androidx.room.Entity


//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//
@Entity(tableName="miactividad_table")
data class MiActividad(
    var titulo:String,
    var descipcion: String,
    var priority:Int
) {

}