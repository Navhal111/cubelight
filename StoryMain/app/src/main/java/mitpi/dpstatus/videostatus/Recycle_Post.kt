package mitpi.dpstatus.videostatus

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.ads.InterstitialAd
import org.jetbrains.anko.find
import org.json.JSONArray
import org.json.JSONObject
import com.bumptech.glide.Glide
import mitpi.dpstatus.videostatus.R
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest


class Recycle_Post(var name: JSONArray,var ads :InterstitialAd): RecyclerView.Adapter<Recycle_Post.ViewHolder>() {
    lateinit var context1: Context
    var click_event = 0;
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent!!.context).inflate(R.layout.picslist, parent, false)
        context1=parent.context
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return name.length()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        try{
        ads.adListener = object : AdListener() {
            override fun onAdClosed() {
                val adRequest = AdRequest.Builder().addTestDevice(context1.getString(R.string.interstial_ads)).build()
                ads.loadAd(adRequest)
            }
        }
        var json_post: JSONObject
        json_post = name.getJSONObject(position).getJSONObject("node")
        var url = json_post.getString("display_url")
        Glide.with(context1).load(url).into(holder!!.image)
        holder.image.setOnClickListener{
           try{
//               var ImageObject:JSONObject = json_post.getJSONArray("thumbnail_resources").getJSONObject(0)
               var intent = Intent(context1, DpImageShare::class.java)
               intent.putExtra("imagethum",json_post.getString("thumbnail_src"))
               intent.putExtra("image",url)
               context1.startActivity(intent)
               click_event+=1
               if(click_event%3==0){
                   ads.show()
                   val adRequest = AdRequest.Builder().addTestDevice(context1.getString(R.string.interstial_ads)).build()
                   ads.loadAd(adRequest)
               }
           }catch (e :Exception){
               ToastInstallSucc("Sorry Problem On Server, Try Again After One Minute")
           }catch (e:NullPointerException){
               ToastInstallSucc("Sorry Problem On Server, Try Again After One Minute")
           }catch (e:IllegalArgumentException){
               ToastInstallSucc("Sorry Problem On Server, Try Again After One Minute")
           }catch (e: ActivityNotFoundException){
               ToastInstallSucc("Sorry Problem On Server, Try Again After One Minute")
           }

        }
        }catch (e :Exception){
            ToastInstallSucc("Sorry Problem On Server, Try Again After One Minute")
        }catch (e:NullPointerException){
            ToastInstallSucc("Sorry Problem On Server, Try Again After One Minute")
        }catch (e:IllegalArgumentException){
            ToastInstallSucc("Sorry Problem On Server, Try Again After One Minute")
        }catch (e: ActivityNotFoundException){
            ToastInstallSucc("Sorry Problem On Server, Try Again After One Minute")
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.find(R.id.mainlini)
        init {

        }
    }

    fun ToastInstallSucc(str :String){

        SuperActivityToast.create(context1).setText(str).setDuration(Style.DURATION_MEDIUM).setFrame(Style.FRAME_KITKAT).setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED)).setAnimations(Style.ANIMATIONS_POP).show();
    }
}