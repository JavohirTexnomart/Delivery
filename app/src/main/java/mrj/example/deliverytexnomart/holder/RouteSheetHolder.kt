package mrj.example.deliverytexnomart.holder

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.databinding.RoutesheetItemBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.RouteSheet
import mrj.example.deliverytexnomart.view.OrdersActivity

class RouteSheetHolder(var itemViewbinding: RoutesheetItemBinding) :
    RecyclerView.ViewHolder(itemViewbinding.root) {

    fun bind(routeSheet: RouteSheet) {
        val resources = itemViewbinding.root.resources
        itemViewbinding.apply {
            txtNumber.text =
                resources.getString(R.string.text_number, routeSheet.number)
            txtDate.text =
                resources.getString(R.string.text_date, routeSheet.date)
            constraintRoutesheet.apply {
                setOnClickListener {
                    val context = this.context
                    val intent = Intent(context, OrdersActivity::class.java)
                    intent.putExtra(C.KEY_ROUTE_SHEET_NUMBER, routeSheet.number)
                    intent.putExtra(C.KEY_ROUTE_SHEET_DATE, routeSheet.date)
                    context.startActivity(intent)
                }
            }
        }
    }

}