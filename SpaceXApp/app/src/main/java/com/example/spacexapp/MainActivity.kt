package com.example.spacexapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val BASE_URL = "https://api.spacexdata.com/v3/launches"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchForLaunches(BASE_URL)
        setListeners()
    }

    fun setListeners() {
        all_launches.setOnClickListener { searchForLaunches(BASE_URL) }
        past_launches.setOnClickListener { searchForLaunches(BASE_URL.plus("/past")) }
        upcoming_launches.setOnClickListener { searchForLaunches(BASE_URL.plus("/upcoming")) }
    }

    fun searchForLaunches(url: String) {

        url.httpGet().responseObject(Launch.Deserializer()) { request, response, result ->
            val (launches, err) = result

            setDataToView(launches)

            err?.let { Toast.makeText(this, err.exception.message.toString(), Toast.LENGTH_LONG).show() }
        }
    }

    fun setDataToView(launches: ArrayList<Launch>?) {

        launches?.let {
            listView.adapter = LaunchAdapter(launches!!) {}
            listView.adapter.notifyDataSetChanged()
        }

        listView.isViewVisible(!launches.isNullOrEmpty())
        emptyView.isViewVisible(launches.isNullOrEmpty())

        //Set items separator
        if (listView.itemDecorationCount == 0) {
            listView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        }
    }

    fun View.isViewVisible(visible: Boolean) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }

}
