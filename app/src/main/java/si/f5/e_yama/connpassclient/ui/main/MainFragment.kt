package si.f5.e_yama.connpassclient.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import si.f5.e_yama.connpassclient.R
import si.f5.e_yama.connpassclient.connpass.Search
import si.f5.e_yama.connpassclient.databinding.MainFragmentBinding
import si.f5.e_yama.connpassclient.ui.main.result.ResultListAdapter
import java.util.concurrent.Executors


class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: MainFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.deliveryKeyword.observe(this) { binding.searchBar.setText(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.main_toolbar)
        val editText: EditText = view.findViewById(R.id.search_bar)
        val button: Button = view.findViewById(R.id.search_button)
        val swipe: SwipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        val list: ListView = view.findViewById(R.id.search_result)

        val adapter = ResultListAdapter(requireContext(), R.layout.search_result_item, arrayListOf())

        toolbar.inflateMenu(R.menu.options_menu)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.detailed_search) {
                findNavController().navigate(R.id.action_detailed_search)
                return@setOnMenuItemClickListener true
            }
            false
        }

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searching.value = true
                return@setOnEditorActionListener true
            }
            false
        }

        button.setOnClickListener { viewModel.searching.value = true }

        swipe.setOnRefreshListener { viewModel.searching.value = true }
        swipe.viewTreeObserver.addOnScrollChangedListener { swipe.isEnabled = list.scrollY == 0; }

        list.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}

            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (viewModel.events.value == null) return
                if (totalItemCount != 0 && totalItemCount == firstVisibleItem + visibleItemCount && viewModel.updating.value == false) {
                    more(false)
                }
            }
        })
        list.adapter = adapter
        list.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.selected = adapter.getItem(position) ?: return
                findNavController().navigate(R.id.action_detail_result)
            }
        }

        viewModel.searching.observe(this) {
            swipe.isRefreshing = it
            if (it)
                search()
        }

        viewModel.events.observe(this) {
            adapter.refresh(it)
            viewModel.searching.value = false
        }
    }

    private fun search() {
        val searchAnd = arrayListOf<String>()
        val searchOr = arrayListOf<String>()


        (if (viewModel.searchModeAnd.value == true) searchAnd else searchOr).addAll(viewModel.keyword.value!!.split(" "))
        viewModel.prefecture.forEach { searchOr.add(it.full) }

        val searchYearMonth: Int = if (viewModel.from.value!! == "") 0 else viewModel.from.value!!.toInt()


        viewModel.search = Search(
            null,
            searchAnd,
            searchOr,
            if (searchYearMonth.toString().length == 6) listOf(searchYearMonth) else null,
            null,
            null,
            null,
            null,
            null
        )
        viewModel.deliveryKeyword.value = viewModel.keyword.value

        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view?.windowToken, 0)

        more(true)
    }

    private fun more(reset: Boolean) {
        viewModel.updating.value = true
        Executors.newSingleThreadExecutor().execute {
            if (reset) viewModel.events.value!!.clear()

            viewModel.events.value!!.addAll(viewModel.search.next())
            viewModel.events.postValue(viewModel.events.value)
            viewModel.updating.postValue(false)
        }
    }
}