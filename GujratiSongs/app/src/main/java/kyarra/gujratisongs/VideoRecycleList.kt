package kyarra.gujratisongs


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.json.JSONException
import android.view.animation.AnimationUtils


class VideoRecycleList(var name :Array<String>): RecyclerView.Adapter<VideoRecycleList.ViewHolder>()
{
    lateinit var context1: Context
    var lastPosition:Int = 0
    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        try{
            val animation = AnimationUtils.loadAnimation(context1,
                    if (position > lastPosition)
                        R.xml.up_from_bottom
                    else
                        R.anim.down_from_top)
            holder.itemView.startAnimation(animation);
            lastPosition = position;
            var videoid   = name.get(position)
            Picasso.with(context1).load("https://i.ytimg.com/vi/${videoid.trim()}/0.jpg").fit().into(holder.thummail)
            holder.card_view2.setOnClickListener{
                val intent = Intent(context1, MainVideoView::class.java)
                intent.putExtra("videoid", videoid)
                intent.putExtra("Title", " ")
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoRecycleList.ViewHolder {

        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.videolist, parent, false)
        context1=parent.context
        return ViewHolder(itemView)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thummail : ImageView = itemView.find(R.id.imagethum)
        var  card_view2 : CardView = itemView.find(R.id.card_view2)
        init {

        }
    }
}
