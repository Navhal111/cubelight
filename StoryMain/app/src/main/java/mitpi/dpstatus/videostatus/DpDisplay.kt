package mitpi.dpstatus.videostatus

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import mitpi.dpstatus.videostatus.R
import org.jetbrains.anko.find
import org.json.JSONArray


class DpDisplay(var mainjson:JSONArray): RecyclerView.Adapter<DpDisplay.ViewHolder>() {
    lateinit var context1: Context


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent!!.context).inflate(R.layout.dpicon, parent, false)
        context1=parent.context
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mainjson.length()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            var mainObject  = mainjson.getJSONObject(position)
            var imagename = mainObject.getString("image")
            val id = context1.getResources().getIdentifier("mitpi.dpstatus.videostatus:drawable/$imagename", null, null)
            holder!!.image.setImageResource(id)
            holder.imagetext.text =mainObject.getString("title")

            holder!!.cardView.setOnClickListener {
                val intent = Intent(context1, VideosList::class.java)
                intent.putExtra("playlistid", mainObject.getString("plailistid"));
                context1.startActivity(intent)
            }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.find(R.id.img)
        var imagetext : TextView = itemView.find(R.id.imgtext)
        var cardView : CardView = itemView.find(R.id.cardView)
        init {

        }
    }
}