package si.f5.e_yama.connpassclient.ui.main.result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import si.f5.e_yama.connpassclient.R
import si.f5.e_yama.connpassclient.json.ConnpassEvent

class ResultListAdapter(context: Context, resource: Int, items: List<ConnpassEvent>) : ArrayAdapter<ConnpassEvent?>(context, resource, items) {
    private val inflater: LayoutInflater
    private val resourceId: Int

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        resourceId = resource
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: inflater.inflate(resourceId, null)

        val item: ConnpassEvent = getItem(position) ?: return view

        val timeStamp = view.findViewById(R.id.result_time) as TextView
        val title = view.findViewById(R.id.result_title) as TextView
        val description = view.findViewById(R.id.result_description) as TextView

        timeStamp.text = item.start
        title.text = item.title
        description.text = item.catch
        return view
    }

    fun refresh(items: List<ConnpassEvent>) {
        clear()
        addAll(items)
        notifyDataSetChanged()
    }
}