package si.f5.e_yama.connpassclient.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import si.f5.e_yama.connpassclient.connpass.Search
import si.f5.e_yama.connpassclient.json.ConnpassEvent
import si.f5.e_yama.connpassclient.util.Prefecture

class MainViewModel : ViewModel() {
    val searching = MutableLiveData(false)
    val updating = MutableLiveData(false)

    val searchModeAnd = MutableLiveData(true) // true: And, false: Or
    val keyword = MutableLiveData("")
    val deliveryKeyword = MutableLiveData("")
    val from = MutableLiveData("")
    val prefecture = arrayListOf<Prefecture>()

    lateinit var search: Search
    lateinit var selected: ConnpassEvent

    val events = MutableLiveData<ArrayList<ConnpassEvent>>()

    init {
        events.value = arrayListOf()
    }
}