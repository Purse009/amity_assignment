package Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.amity.assignment.R
import Model.TodoListModel

internal class GridViewAdapter(
    private val todoList: List<TodoListModel>,
    private val context: Context
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return todoList.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder", "InflateParams", "SetTextI18n")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var convertView = p1
        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        convertView = layoutInflater!!.inflate(R.layout.gridview_item, null)

        val title = convertView!!.findViewById<TextView>(R.id.title)
        val complete = convertView.findViewById<TextView>(R.id.complete)

        title.text = "Title : ${todoList[p0].title}"
        complete.text = "Complete : ${ todoList[p0].completed}"

        return convertView
    }
}