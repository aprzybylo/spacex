package com.example.spacexapp

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_img.view.*
import kotlinx.android.synthetic.main.listview_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class LaunchAdapter(val launchesList: ArrayList<Launch>, val listener: (Launch) -> Unit) :
    RecyclerView.Adapter<LaunchAdapter.LaunchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchHolder {
        return LaunchHolder(parent.inflate(R.layout.listview_item))
    }

    fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    override fun getItemCount(): Int {
        return launchesList.size
    }

    override fun onBindViewHolder(holder: LaunchHolder, position: Int) {
        holder.bind(launchesList.get(position), listener)
    }

    class LaunchHolder(view: View?) : RecyclerView.ViewHolder(view) {

        fun bind(item: Launch, listener: (Launch) -> Unit) = with(itemView) {

            mission_name.text = item.mission_name
            rocket_name.text = item.rocket!!.rocket_name
            date.text = getDate(item.launch_date_utc!!)

            item.launch_success?.let{
                status.text = resources.getText(if(item.launch_success!!) R.string.successful else R.string.unsuccessful)
            }

            item.links!!.mission_patch?.let { launch_image.loadUrl(item.links!!.mission_patch)}

            item.links!!.wikipedia?.let{
                link.text = resources.getText(R.string.link)
                link.setOnClickListener {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(item.links!!.wikipedia)
                    startActivity(context, openURL, null)
                }
            }

            launch_image.setOnClickListener {
                item.links!!.mission_patch?.let{
                    val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_img, null)
                    dialogView.full_image.loadUrl(item.links!!.mission_patch)

                    val builder = AlertDialog.Builder(context)
                    builder.setView(dialogView)
                    builder.setPositiveButton(android.R.string.yes) { dialog, which -> dialog.dismiss() }
                    builder.show()
                }
            }

        }

        fun getDate(date: Date): String {
            val format = SimpleDateFormat("dd.MM.yyy hh:mm")
            return format.format(date)
        }

        fun ImageView.loadUrl(url: String?) {
            Picasso.with(context).load(url).into(this)
        }


    }

}

