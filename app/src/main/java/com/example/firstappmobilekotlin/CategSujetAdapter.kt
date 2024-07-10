package com.example.firstappmobilekotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.firstappmobilekotlin.data.Sujet

class CategSujetAdapter(
    var mContext: Context,
    var resource: Int,
    var values: ArrayList<Sujet>
): ArrayAdapter<Sujet>(mContext, resource, values) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val categ = values[position]
        val itemView = LayoutInflater.from(mContext).inflate(resource, parent, false)
        val titreSujet = itemView.findViewById<TextView>(R.id.titreSujet)
        val descriptionSujet = itemView.findViewById<TextView>(R.id.descriptionSujet)
        val imageSujet = itemView.findViewById<ImageView>(R.id.imageSujet)
        val imageShowPopup = itemView.findViewById<ImageView>(R.id.imageShowPopup)

        titreSujet.text = categ.titre
        descriptionSujet.text = categ.description
        imageSujet.setImageResource(categ.image)

        // Ajouter un evenement de click sur le menu popup
        imageShowPopup.setOnClickListener{
            val popupMenu = PopupMenu(mContext, it)
            // Convertir le popup en xml
            popupMenu.menuInflater.inflate(R.menu.list_popup_menu, popupMenu.menu)

            // Methode lorsqu'on clique sur les menus
            popupMenu.setOnMenuItemClickListener {item ->
                when(item.itemId){
                    R.id.itemShow -> {
                        Intent(mContext, PostDetailsActivity::class.java).also{
                            it.putExtra("titre", categ.titre)
                            mContext.startActivity(it)
                        }
                    }
                    R.id.itemDelete -> {
                        values.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
                true
            }

            // Afficher le menu
            popupMenu.show()
        }

        return itemView
    }
}