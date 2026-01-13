package si.f5.e_yama.connpassclient.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import si.f5.e_yama.connpassclient.MainActivity
import si.f5.e_yama.connpassclient.R
import si.f5.e_yama.connpassclient.databinding.DetailedSearchFragmentBinding
import si.f5.e_yama.connpassclient.ui.main.MainViewModel
import si.f5.e_yama.connpassclient.util.Prefecture

class DetailedSearchFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: DetailedSearchFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DetailedSearchFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.deliveryKeyword.observe(this) { binding.editTextKeywords.setText(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar: Toolbar = view.findViewById(R.id.detailed_search_toolbar)

        toolbar.inflateMenu(R.menu.detailed_search_menu)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.option_search) {
                findNavController().popBackStack()
                viewModel.searching.value = true
                return@setOnMenuItemClickListener true
            }
            false
        }

        val linearLayout: LinearLayout = requireActivity().findViewById(R.id.checkBoxesPrefectures)
        viewModel.deliveryKeyword.value = viewModel.keyword.value

        Prefecture.Area.values().toList().forEach { area ->
            val prefectures: Array<Prefecture> = Prefecture.getByArea(area)
            val areaName = TextView(requireContext())
            val prefs = arrayListOf<Int>()
            val recyclerView = RecyclerView(requireContext())
            val flexboxLayoutManager = FlexboxLayoutManager(requireContext())
            val adapter = ViewAdapter()

            areaName.text = area.text

            prefectures.toList().forEach { prefs.add(it.code) }

            adapter.setItems(prefs)

            recyclerView.layoutManager = flexboxLayoutManager
            recyclerView.adapter = adapter

            linearLayout.addView(areaName)
            linearLayout.addView(recyclerView)
        }
    }

    class ViewAdapter : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val checkBox: CheckBox = view.findViewById(R.id.pref_item)
        }

        private val items = ArrayList<Int>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.prefecture_item, parent, false))
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val prefecture = Prefecture.getByCode(items[position])

            holder.checkBox.text = prefecture!!.full
            if (holder.checkBox.context is MainActivity) {
                val activity: MainActivity = holder.checkBox.context as MainActivity
                holder.checkBox.isChecked = activity.viewModel.prefecture.contains(prefecture)
            }
            holder.checkBox.setOnClickListener {
                if (it.context is MainActivity) {
                    val activity: MainActivity = it.context as MainActivity
                    if (holder.checkBox.isChecked) {
                        activity.viewModel.prefecture.add(prefecture)
                    } else {
                        activity.viewModel.prefecture.remove(prefecture)
                    }
                }
            }
        }

        fun setItems(items: List<Int>) {
            this.items.clear()
            this.items.addAll(items)
        }
    }
}