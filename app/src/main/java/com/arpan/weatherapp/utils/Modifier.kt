package com.arpan.weatherapp.utils

open class Modifier {

    companion object{
        fun getTemp(temp : Double) : String {
            val t = (temp - 273.15).toLong()
            return "$t"
        }
    }

}