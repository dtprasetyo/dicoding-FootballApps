package com.odora.id.footballapps.ui.event

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.odora.id.footballapps.model.Leagues

class SpinnerAdapter(context: Context,
                     textViewResourceId: Int,
                                    private val values: List<Leagues>) : ArrayAdapter<Leagues>(context, textViewResourceId, values) {

    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): Leagues? {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.setText(values[position].strLeague)

        return label
    }

    override fun getDropDownView(position: Int, convertView: View?,
                                 parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.setText(values[position].strLeague)

        return label
    }
}