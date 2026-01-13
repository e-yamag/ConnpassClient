package si.f5.e_yama.connpassclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import si.f5.e_yama.connpassclient.connpass.Search
import si.f5.e_yama.connpassclient.ui.main.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        if (savedInstanceState == null) {
            Search.SERVICE = Retrofit.Builder()
                .baseUrl("https://connpass.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Search.ConnpassService::class.java)
        }
    }
}