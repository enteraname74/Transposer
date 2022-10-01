package com.github.enteraname74.transposer.adapters

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Scale
import java.io.Serializable

// Classe permettant de représenter une liste de musiques :
data class ScalesList(
    private val context : Context,
    private val scaleListener : OnScaleListener
    ) : RecyclerView.Adapter<ScalesList.ScaleViewHolder>(), Serializable {

    class ScaleViewHolder(itemView: View, private var onScaleListener: OnScaleListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener,View.OnCreateContextMenuListener {

        val scaleName: TextView = itemView.findViewById(R.id.scale_name)
        init {
            super.itemView
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onClick(v: View?) {
            this.onScaleListener.onScaleClick(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            itemView.showContextMenu()
            return true
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(adapterPosition, 0, 0, itemView.resources.getString(R.string.send_to_a_contact))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScaleViewHolder {
        return ScaleViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.scale_layout,
                parent,
                false
            ), scaleListener
        )
    }

    override fun onBindViewHolder(holder: ScaleViewHolder, position: Int) {
        holder.scaleName.text = AppData.scalesList[position].scaleName
    }

    override fun getItemCount(): Int {
        return AppData.scalesList.size
    }

    interface OnScaleListener {
        fun onScaleClick(position: Int)
    }
}
