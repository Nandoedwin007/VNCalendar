package com.example.vncalendar.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MiActividadDao{
    @Insert
    fun insert(miAcitividad:MiActividad)

    @Update
    fun update (miAcitividad: MiActividad)

    @Delete
    fun delete  (miAcitividad: MiActividad)

    @Query("DELETE FROM miactividad_table")
    fun deleteAllActividades()

    @Query ("SELECT * FROM miactividad_table ORDER BY priority DESC")
    fun getAllActividades():LiveData<List<MiActividad>>

}