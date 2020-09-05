package com.example.tram4.mainactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tram4.R
import com.example.tram4.api.models.DepartureInfo

class TimeTableAdapter(private var departures: List<DepartureInfo>) : RecyclerView.Adapter<TimeTableAdapter.DepartureViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tram_list_item, parent, false) as ConstraintLayout
        return DepartureViewHolder(view)
    }

    override fun getItemCount(): Int = departures.size

    override fun onBindViewHolder(holder: DepartureViewHolder, position: Int) {
        holder.bind(departures[position])
    }

    fun update(departures: List<DepartureInfo>){
        this.departures = departures
        notifyDataSetChanged()
    }

    class DepartureViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val route = view.findViewById<TextView>(R.id.tram_route)
        private val sign = view.findViewById<TextView>(R.id.tram_sign)
        private val departureTime = view.findViewById<TextView>(R.id.departure_time)

        fun bind(departureInfo: DepartureInfo){
            route.text = departureInfo.route
            sign.text = departureInfo.sign
            departureTime.text = "${departureInfo.departureInMinutes} min"
        }
    }
}