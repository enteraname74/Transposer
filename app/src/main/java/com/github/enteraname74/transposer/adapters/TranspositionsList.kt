package com.github.enteraname74.transposer.adapters

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Transposition
import java.io.Serializable

// Classe permettant de représenter une liste de musiques :
data class TranspositionsList(
    private val context : Context,
    var list : ArrayList<Transposition>,
    private val transpositionListener : OnTranspositionListener
) : RecyclerView.Adapter<TranspositionsList.TranspositionViewHolder>(), Serializable {

    class TranspositionViewHolder(itemView: View, private var onScaleListener: OnTranspositionListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {

        val scaleName: TextView = itemView.findViewById(R.id.scale_name)
        init {
            super.itemView
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onClick(v: View?) {
            this.onScaleListener.onTranspositionClick(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            this.onScaleListener.onTranspositionLongClick(adapterPosition)
            return true
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(adapterPosition, 10, 0, itemView.resources.getString(R.string.delete_transposition))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranspositionViewHolder {
        return TranspositionViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.scale_layout,
                parent,
                false
            ), transpositionListener
        )
    }

    override fun onBindViewHolder(holder: TranspositionViewHolder, position: Int) {
        holder.scaleName.text = list[position].transpositionName
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnTranspositionListener {
        fun onTranspositionClick(position: Int)

        fun onTranspositionLongClick(position : Int)
    }
}