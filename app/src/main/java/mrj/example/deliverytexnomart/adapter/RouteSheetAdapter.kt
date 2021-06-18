package mrj.example.deliverytexnomart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mrj.example.deliverytexnomart.databinding.RoutesheetItemBinding
import mrj.example.deliverytexnomart.holder.RouteSheetHolder
import mrj.example.deliverytexnomart.model.RouteSheet

class RouteSheetAdapter(var routesheet_list: List<RouteSheet>) :
    RecyclerView.Adapter<RouteSheetHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteSheetHolder {
        val itemViewBinding =
            RoutesheetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RouteSheetHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: RouteSheetHolder, position: Int) {
        val routeSheet = routesheet_list.get(position)
        holder.bind(routeSheet)
    }

    override fun getItemCount(): Int = routesheet_list.size
}