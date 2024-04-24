package com.example.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.database.EventEntity

 class AdapterView (var events: List<EventEntity>) :
        RecyclerView.Adapter<AdapterView.EventViewHolder>() {
        private lateinit var adapterClickListener: AdapterClickListener
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view_design, parent, false)
            return EventViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
            val event = events[position]
            holder.head.text = event.title
            holder.desc.text = event.description
            holder.time.text = event.startTime + " \n" + event.endTime
            holder.punkt.setOnClickListener{
                val selectedEvent = events[position]
                val selectedId = selectedEvent.id
                if (adapterClickListener != null) adapterClickListener.onClick(selectedId)

            }

        }

        fun addOnClickListener(clickListener: AdapterClickListener) {
            adapterClickListener = clickListener

        }

    override fun getItemCount(): Int = events.size

        class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val head: TextView = itemView.findViewById(R.id.head)
            val desc: TextView = itemView.findViewById(R.id.desc)
            val time: TextView = itemView.findViewById(R.id.time)
            val punkt: ConstraintLayout = itemView.findViewById(R.id.punkt)
        }


    interface AdapterClickListener {

        fun onClick(id: Long)



    }

}

