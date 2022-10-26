package com.github.enteraname74.transposer.adapters

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.classes.Transposition
import java.io.Serializable

// Classe permettant de représenter une liste de musiques :
class FavouriteList(
    private val context : Context,
    var list : ArrayList<Transposition>,
    private val favouriteListener : OnFavouriteListener
) : RecyclerView.Adapter<FavouriteList.FavouriteViewHolder>(), Serializable {

    class FavouriteViewHolder(itemView: View, private var onScaleListener: OnFavouriteListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {

        val scaleName: TextView = itemView.findViewById(R.id.scale_name)
        init {
            super.itemView
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onClick(v: View?) {
            this.onScaleListener.onFavouriteClick(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            itemView.showContextMenu()
            return true
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(adapterPosition, 20, 0, itemView.resources.getString(R.string.delete_transposition))
            menu?.add(adapterPosition, 21, 0, itemView.resources.getString(R.string.change_favourite_statue))
            menu?.add(adapterPosition, 22, 0, itemView.resources.getString(R.string.send_to_a_contact))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.scale_layout,
                parent,
                false
            ), favouriteListener
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.scaleName.text = list[position].transpositionName
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnFavouriteListener {
        fun onFavouriteClick(position: Int)
    }
}