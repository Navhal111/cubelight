package kyarra.gujratisongs

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.json.JSONException

class RecycleList(var name :Array<String>): RecyclerView.Adapter<RecycleList.ViewHolder>()
{
    lateinit var context1: Context

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

            try{
                holder.textmain.text = name.get(position)
                if((position+1)%2 == 0){
                    holder.appliniar.setBackgroundResource(R.drawable.bg2)
                }else{
                    holder.appliniar.setBackgroundResource(R.drawable.bg1)
                }
                holder.appliniar.setOnClickListener{
                    val intent = Intent(context1, VideoList::class.java)
                    intent.putExtra("index",position.toString())
                    context1.startActivity(intent)
                }

        }catch (e: JSONException){
            context1.toast("Error while Requesting")
        }catch (e:NullPointerException){
            context1.toast("Error while Requesting")
        }catch (e:IllegalArgumentException){
            context1.toast("Error while Requesting")
        }catch (e:Exception){
            context1.toast("Error while Requesting")
        }

    }

    override fun getItemCount(): Int {
        return  name.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleList.ViewHolder {

        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.listactivity, parent, false)
        context1=parent.context
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var appliniar : LinearLayout = itemView.find(R.id.set_liniar)
        var textmain : TextView = itemView.find(R.id.maintext)
        init {

        }
    }
}
