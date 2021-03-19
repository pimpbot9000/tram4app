package pyramidihuijaus.app.tram4.main_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pyramidihuijaus.app.tram4.R
import pyramidihuijaus.app.tram4.api.models.DepartureInfo

class TimeTableAdapter(private var departures: List<DepartureInfo>) : RecyclerView.Adapter<TimeTableAdapter.DepartureViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tram_list_item, parent, false) as ConstraintLayout
        return DepartureViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = departures.size

    override fun onBindViewHolder(holder: DepartureViewHolder, position: Int) {
        holder.bind(departures[position])
    }

    fun update(departures: List<DepartureInfo>){

        val result = DiffUtil.calculateDiff(DeparturesDiffUtilCallBack(this.departures, departures))
        result.dispatchUpdatesTo(this)
        this.departures = departures

    }



    override fun onBindViewHolder(
        holder: DepartureViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bind(departures[position])
        }

    }

    class DepartureViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val route = view.findViewById<TextView>(R.id.tram_route)
        private val sign = view.findViewById<TextView>(R.id.tram_sign)
        private val departureTime = view.findViewById<TextView>(R.id.departure_time)
        private val redLight = view.findViewById<ImageView>(R.id.red_light)


        fun bind(departureInfo: DepartureInfo){
            if(route.text != departureInfo.route) {
                route.text = departureInfo.route
            }
            if(sign.text !=  departureInfo.sign) {
                sign.text = departureInfo.sign
            }
            departureTime.text = "${departureInfo.departureInMinutes} min"

            if(departureInfo.departureInMinutes == 0){
                redLight.visibility = View.VISIBLE
                redLight.startAnimation(AnimationUtils.getAnimation())
            } else {
                redLight.visibility = View.GONE
            }
        }


    }
}

class DeparturesDiffUtilCallBack(private var oldList: List<DepartureInfo>, private var newList: List<DepartureInfo>) : DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getId().equals(newList[newItemPosition].getId(), false)
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].departureInMinutes == newList[newItemPosition].departureInMinutes
    }

}