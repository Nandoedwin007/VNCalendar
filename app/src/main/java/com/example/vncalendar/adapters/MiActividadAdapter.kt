package com.example.vncalendar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vncalendar.R
import com.example.vncalendar.data.MiActividad
import kotlinx.android.synthetic.main.actividad_item.view.*
import org.w3c.dom.Text
import java.util.*

class MiActividadAdapter: androidx.recyclerview.widget.ListAdapter<MiActividad, MiActividadAdapter.MiActividadHolder>(DIFF_CALLBACK){

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MiActividad>(){
            override fun areItemsTheSame(oldItem: MiActividad, newItem: MiActividad): Boolean {
                return oldItem.id == newItem.id
            }

            //Nótese que si se cambia algo en la clase MiActividad aquí también se debe considerar el cambio
            override fun areContentsTheSame(oldItem: MiActividad, newItem: MiActividad): Boolean {
                return oldItem.titulo == newItem.titulo && oldItem.descipcion == newItem.descipcion &&
                        oldItem.tipoActividad == newItem.tipoActividad && oldItem.tipoVibracion == newItem.tipoVibracion &&
                        oldItem.diaActividad == newItem.diaActividad && oldItem.mesActividad == newItem.mesActividad &&
                        oldItem.fecha == oldItem.fecha && oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiActividadAdapter.MiActividadHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.actividad_item,parent
        ,false)
        return MiActividadHolder(itemView)
    }

    override fun onBindViewHolder(holder: MiActividadHolder, position: Int) {
        val currentMiActividad : MiActividad = getItem(position)

        //Aquí van los holders
        holder.textViewPriority.text = currentMiActividad.priority.toString()
        holder.textViewTituloActividad.text = currentMiActividad.titulo
        holder.textViewDescripcionActividad.text = currentMiActividad.descipcion
    }

    fun getMiActividadAt(position: Int):MiActividad{
        return getItem(position)
    }

    inner class MiActividadHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewPriority:TextView = itemView.text_view_priority
        var textViewTituloActividad:TextView = itemView.text_view_titulo_actividad
        var textViewDescripcionActividad:TextView = itemView.text_view_descripcion_actividad

    }

    interface OnItemClickListener{
        fun onItemClick(miActividad: MiActividad)
    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener = listener
    }
}