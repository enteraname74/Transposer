package com.github.enteraname74.transposer.adapters

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.classes.Transposition
import java.io.Serializable

// Classe permettant de représenter une liste de transpositions :
class TranspositionsList(
    private val context: Context,
    var list: ArrayList<Transposition>,
    private val transpositionListener: OnTranspositionListener,
    var source: String = "Local"
) : RecyclerView.Adapter<TranspositionsList.TranspositionViewHolder>(), Serializable {

    /*
    Classe permettant de répresenter un élément de notre liste
    Elle implémente plusieurs éléments pour gérer les cliques, les cliques long ou encore les contextMenu
     */
    class TranspositionViewHolder(
        itemView: View,
        private var onScaleListener: OnTranspositionListener,
        private var source: String,
        private var list: ArrayList<Transposition>
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener,
        View.OnCreateContextMenuListener {

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
            itemView.showContextMenu()
            return true
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            if (source != "Cloud") {
                menu?.add(
                    adapterPosition,
                    if (source != "Favourites") 10 else 20,
                    0,
                    itemView.resources.getString(R.string.delete_transposition)
                )
                menu?.add(
                    adapterPosition,
                    if (source != "Favourites") 11 else 21,
                    0,
                    if (source != "Favourites" && list[adapterPosition].isFavourite) itemView.resources.getString(
                        R.string.remove_from_favourites
                    )
                    else if (source != "Favourites" && !(list[adapterPosition].isFavourite)) itemView.resources.getString(
                        R.string.add_to_favourites
                    )
                    else itemView.resources.getString(R.string.remove_from_favourites)
                )
                menu?.add(
                    adapterPosition,
                    if (source != "Favourites") 13 else 23,
                    0,
                    itemView.resources.getString(R.string.share_online)
                )
            } else {
                menu?.add(
                    adapterPosition,
                    14,
                    0,
                    itemView.resources.getString(R.string.save_locally)
                )
            }
            menu?.add(
                adapterPosition,
                12,
                0,
                itemView.resources.getString(R.string.send_to_a_contact)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranspositionViewHolder {
        return TranspositionViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.scale_layout,
                parent,
                false,
            ), transpositionListener,
            source,
            list
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
    }
}