package si.f5.e_yama.connpassclient.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import si.f5.e_yama.connpassclient.R
import si.f5.e_yama.connpassclient.databinding.EventDetailFragmentBinding
import si.f5.e_yama.connpassclient.ui.main.MainViewModel

class EventDetailFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return EventDetailFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val event = viewModel.selected

        val title: TextView = view.findViewById(R.id.detail_title)
        val catch: TextView = view.findViewById(R.id.detail_catch)
        val description: WebView = view.findViewById(R.id.detail_description)
        val url: TextView = view.findViewById(R.id.detail_url)
        val hashtag: TextView = view.findViewById(R.id.detail_hashtag)
        val date: TextView = view.findViewById(R.id.detail_date)

        title.text = event.title
        catch.text = event.catch
        description.loadDataWithBaseURL(null, event.description ?: "", "text/html", "UTF8", null)
        description.setBackgroundColor(Color.rgb(250, 250, 250))
        url.text = event.url
        hashtag.text = event.hashtag
        val s = "${event.start} - ${event.end}"
        date.text = s
    }
}