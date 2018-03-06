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
import org.jetbrains.anko.toast


class StatusDisplay: RecyclerView.Adapter<StatusDisplay.ViewHolder>() {
    lateinit var context1: Context


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent!!.context).inflate(R.layout.dpiconmain, parent, false)
        context1=parent.context
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 2

    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        if(position==0){
            val id = context1.getResources().getIdentifier("mitpi.dpstatus.videostatus:drawable/boys", null, null)
            holder!!.image.setImageResource(id)
            holder.imagetext.text = "Boys"
            holder.cardView.setOnClickListener {
                val intent = Intent(context1, DpListActivity::class.java)
                intent.putExtra("keyName", "dpsstatus");
                context1.startActivity(intent)
                context1.toast("Boys")
            }
        }else{

            val id = context1.getResources().getIdentifier("mitpi.dpstatus.videostatus:drawable/girls", null, null)
            holder!!.image.setImageResource(id)
            holder.imagetext.text = "Girls"
            holder.cardView.setOnClickListener {
                context1.toast("Girls")
            }

        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.find(R.id.img)
        var imagetext : TextView = itemView.find(R.id.imgtext)
        var cardView :CardView = itemView.find(R.id.cardView)
        init {

        }
    }
}