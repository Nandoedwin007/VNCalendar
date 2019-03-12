package com.example.vncalendar

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vncalendar.adapters.MiActividadAdapter
import com.example.vncalendar.data.MiActividad
import com.example.vncalendar.viewmodels.MiActividadViewModelMain
import kotlinx.android.synthetic.main.activity_mis_actividades.*


//Basado en el código del repositorio https://github.com/berkeatac/Notes-App
class MisActividades : AppCompatActivity() {

    companion object {
        const val ADD_MIACTIVIDAD_REQUEST = 1
        const val EDIT_MIACTIVIDAD_REQUEST = 2
        const val VER_CONTACTO_REQUEST = 3

       lateinit var miActividadViewModelMain:MiActividadViewModelMain
    }


//      CÓDIGO DEL LABORATORIO 7 PARA REFERENCIA
//    private lateinit var contactoViewModelEdit: ContactoViewModelEdit
//    private lateinit var contactoViewModelCreate: ContactoViewModelCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_actividades)

        button_add_actividad.setOnClickListener{
            startActivityForResult(
                Intent(this,AgregarActividad::class.java),
                ADD_MIACTIVIDAD_REQUEST
            )
        }

        recycler_view_misactividades.layoutManager = LinearLayoutManager(this)
        recycler_view_misactividades.setHasFixedSize(true)

        var adapter:MiActividadAdapter()

        recycler_view_misactividades.adapter = adapter

        miActividadViewModelMain = ViewModelProviders.of(this).get(MiActividadViewModelMain::class.java)

        miActividadViewModelMain.getAllMisActividades().observe(this,Observer<List<MiActividad>>{
            adapter.submitList(it)
        })

        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                miActividadViewModelMain.delete(adapter.getMiActividadAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext,"Actividad Eliminada!", Toast.LENGTH_LONG).show()
            }
        }).attachToRecyclerView(recycler_view_misactividades)

        adapter.setOnItemClickListener(object :MiActividadAdapter.OnItemClickListener{
            override fun onItemClick(miActividad: MiActividad) {
                var intent = Intent(baseContext,AgregarActividad::class.java)

                //Nótese que si se cambia algo en la clase MiActividad aquí también se debe considerar el cambio
                intent.putExtra(AgregarActividad.EXTRA_ID,miActividad.id)
                intent.putExtra(AgregarActividad.EXTRA_TITULO_ACTIVIDAD,miActividad.titulo)
                intent.putExtra(AgregarActividad.EXTRA_DESCRIPCION_ACTIVIDAD,miActividad.descipcion)
                intent.putExtra(AgregarActividad.EXTRA_TIPO_ACTIVIDAD,miActividad.tipoActividad)
                intent.putExtra(AgregarActividad.EXTRA_TIPO_VIBRACION,miActividad.tipoVibracion)
                intent.putExtra(AgregarActividad.EXTRA_DIA_ACTIVIDAD,miActividad.diaActividad)
                intent.putExtra(AgregarActividad.EXTRA_MES_ACTIVIDAD,miActividad.mesActividad)
                intent.putExtra(AgregarActividad.EXTRA_FECHA_ACTIVIDAD,miActividad.fecha)
                intent.putExtra(AgregarActividad.EXTRA_PRIORITY,miActividad.priority)

                startActivityForResult(intent, EDIT_MIACTIVIDAD_REQUEST)
            }
        })

    }
//Código de referencia del laboratorio 7
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return when (item?.itemId){
//            R.id.delete_all_contactos -> {
//                contactoViewModelMain.deleteAllContactos()
//                Toast.makeText(this, "Todos los contactos eliminados", Toast.LENGTH_SHORT).show()
//                true
//            }
//            else -> {
//                super.onOptionsItemSelected(item)
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Nótese que si se cambia algo en la clase MiActividad aquí también se debe considerar el cambio
        if (requestCode== ADD_MIACTIVIDAD_REQUEST && resultCode == Activity.RESULT_OK){
            val newMiActividad = MiActividad (
                data!!.getStringExtra(AgregarActividad.EXTRA_TITULO_ACTIVIDAD),
                data.getStringExtra(AgregarActividad.EXTRA_DESCRIPCION_ACTIVIDAD),
                data.getIntExtra(AgregarActividad.EXTRA_TIPO_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_TIPO_VIBRACION,1),
                data.getIntExtra(AgregarActividad.EXTRA_DIA_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_MES_ACTIVIDAD,1),
                data.getStringExtra(AgregarActividad.EXTRA_FECHA_ACTIVIDAD),
                data.getIntExtra(AgregarActividad.EXTRA_PRIORITY,1),
            )

            miActividadViewModelMain.insert(newMiActividad)

            Toast.makeText(this, "Se ha creado una nueva actividad!", Toast.LENGTH_SHORT).show()
        }else if (requestCode == EDIT_MIACTIVIDAD_REQUEST && resultCode == Activity.RESULT_OK){
            val id = data?.getIntExtra(AgregarActividad.EXTRA_ID,-1)

            if (id == -1){
                Toast.makeText(this, "Lo sentimos, no se ha podido actualizar! Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
