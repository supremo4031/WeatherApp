package com.arpan.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.arpan.weatherapp.databinding.ActivityMainBinding
import com.arpan.weatherapp.utils.Modifier
import com.arpan.weatherapp.viewmodel.WeatherViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private val weatherViewModel : WeatherViewModel by viewModels()

    @Inject
    lateinit var glide : RequestManager

    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()

        weatherViewModel.getCityData()
        weatherViewModel.getData.observe(this, { response ->
            Log.d(TAG, "onCreate: " + response.weather[0].description)
            if (response.weather[0].description == "clear sky" || response.weather[0].description == "mist") {
                glide.load(R.drawable.clouds)
                    .into(binding.image)
            } else
                if (response.weather[0].description == "haze" || response.weather[0].description == "overcast clouds" || response.weather[0].description == "fog") {
                    glide.load(R.drawable.haze)
                        .into(binding.image)
                } else
                    if (response.weather[0].description == "rain") {
                        glide.load(R.drawable.rain)
                            .into(binding.image)
                    }
            binding.description.text = response.weather[0].description
            binding.name.text = response.name
            binding.degree.text = response.wind.deg.toString()
            binding.speed.text = response.wind.speed.toString()
            binding.temp.text = Modifier.getTemp(response.main.temp)
            binding.humidity.text = response.main.humidity.toString()
        })

        if(savedInstanceState == null) {
            weatherViewModel.getSearchData("Kolkata")
        }
    }

    private fun init() {
        binding.apply {
            searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        weatherViewModel.getSearchData(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }
}