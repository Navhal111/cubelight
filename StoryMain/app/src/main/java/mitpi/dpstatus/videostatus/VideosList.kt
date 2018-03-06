package mitpi.dpstatus.videostatus

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import mitpi.dpstatus.videostatus.R
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_videos_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class VideosList : AppCompatActivity() {
    var nextpage: String? = null
    var last_int=0
    var hasMore = true
    lateinit var mainJson: JSONArray
    var stringid: String? = null
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos_list)
        val playlistid = intent.getStringExtra("playlistid")
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nwInfo = connectivityManager.activeNetworkInfo
        if (nwInfo != null && nwInfo.isConnectedOrConnecting) {

        }else{
            ToastInstallApp("Check your Network Connection")
        }
        var adRequest: AdRequest
        mInterstitialAd = InterstitialAd(this)
        adRequest = AdRequest.Builder().build()
        val unitId = getString(R.string.interstial_ads)
        mInterstitialAd.setAdUnitId(unitId)
        mInterstitialAd.loadAd(adRequest)
        var imageurl: String
        val queyj2 = Volley.newRequestQueue(this)
        //        https@ //www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=12&playlistId="+"+playlistid+"+"&key=AIzaSyDFaZ9yHK_TqYvAmNG9VGUZUinAwNlCyKs
        val jsonobj2 = JsonObjectRequest(Request.Method.GET, "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=12&playlistId="+playlistid+"&key=AIzaSyDFaZ9yHK_TqYvAmNG9VGUZUinAwNlCyKs", null,

                Response.Listener<JSONObject>
                {
                    response ->
                    try {
                        val setert: JSONArray = response.get("items") as JSONArray
                        if (setert.length() > 0) {
                            val jsona = JSONArray()
                            var j1 : JSONObject
                            var j3 : JSONObject
                            if (response.has("nextPageToken")) {

                                nextpage = response.get("nextPageToken").toString()
                            } else {
                                nextpage = null
                            }

                            var i = 0
                            var j = 0
                            while (i < setert.length()) {
                                j1 = setert.get(i) as JSONObject
                                j3 = j1.get("snippet") as JSONObject
                                var j4 : JSONObject
                                if (j3.has("thumbnails")) {
                                    j4 = j3.get("thumbnails") as JSONObject
                                    imageurl = j4.getJSONObject("high").getString("url")
                                    j4 = j3.get("resourceId") as JSONObject
                                    val j5 = JSONObject()
                                    j5.put("id", j4.get("videoId"))
                                    j5.put("title", j3.get("title"))
                                    j5.put("imageurl", imageurl)
                                    stringid = j4.get("videoId").toString()

                                    if (j3.getString("title") != "Private video") {
                                        jsona.put(j, j5)
                                        j++;
                                    }
                                }

                                i++
                            }
                            last_int = setert.length()
                            mainJson = jsona
                            if (setert.length() > 0) {

//                                            json1.text=mainJson.toString()
                                recyclerView.layoutManager = LinearLayoutManager(this)
//
                                recyclerView.adapter = RecyleJson(mainJson, playlistid, mInterstitialAd)
                            }
                        } else {
                            ToastInstallApp("No video Found")
                        }

//                    json1.text=jsona.toString()
                    }catch (e:NullPointerException){
                        ToastInstallApp("Something Went wrong")
                    }catch (e: JSONException){
                        ToastInstallApp("Something Went wrong")
                    }catch (e:IllegalArgumentException){
                        ToastInstallApp("Something Went wrong")
                    }catch (e:Exception){
                        ToastInstallApp("Something Went wrong")
                    }
                }, Response.ErrorListener {
            //                    ToastInstallApp("Check your Network Connection")
//                toast("somthing went wrong")
//
        })



        queyj2.add(jsonobj2)


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (hasMore) {
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    //position starts at 0
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {
                        SuperActivityToast.create(this@VideosList, Style(), Style.TYPE_PROGRESS_CIRCLE).setText("Loading..").setDuration(Style.DURATION_SHORT).setFrame(Style.FRAME_KITKAT).setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN)).setAnimations(Style.ANIMATIONS_POP).show()
                        if (last_int >= 12 && nextpage != null) {
                            try {

                                var imageurl: String
                                val queyj2 = Volley.newRequestQueue(this@VideosList)

                                val jsonobj2 = JsonObjectRequest(Request.Method.GET, "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=12&pageToken=$nextpage&playlistId=" + playlistid + "&key=AIzaSyDFaZ9yHK_TqYvAmNG9VGUZUinAwNlCyKs", null,

                                        Response.Listener<JSONObject>
                                        { response ->
                                            try {
                                                val setert: JSONArray = response.get("items") as JSONArray

                                                if (setert.length() > 0) {
                                                    val jsona = JSONArray()
                                                    var j1 :JSONObject
                                                    var j3 :JSONObject
                                                    if (response.has("nextPageToken")) {

                                                        nextpage = response.get("nextPageToken").toString()
                                                    } else {
                                                        nextpage = null
                                                    }

                                                    var i = 0
                                                    var m = 0
                                                    while (i < setert.length()) {
                                                        j1 = setert.get(i) as JSONObject
                                                        j3 = j1.get("snippet") as JSONObject
                                                        var j4 :JSONObject
                                                        if (j3.has("thumbnails")) {
                                                            j4 = j3.get("thumbnails") as JSONObject
                                                            imageurl = j4.getJSONObject("high").getString("url")
                                                            j4 = j3.get("resourceId") as JSONObject
                                                            val j5 = JSONObject()
                                                            j5.put("id", j4.get("videoId"))
                                                            j5.put("title", j3.get("title"))
                                                            j5.put("imageurl", imageurl)
                                                            stringid = j4.get("videoId").toString()

                                                            if (j3.getString("title") != "Private video") {
                                                                jsona.put(m, j5)
                                                                m++;
                                                            }
                                                        }
                                                        i++
                                                    }
                                                    last_int = setert.length()
                                                    var j = 0
                                                    while (j < jsona.length()) {

                                                        mainJson.put(jsona.get(j))


                                                        j += 1

                                                    }
                                                    recyclerView.adapter.notifyDataSetChanged()
                                                }

                                            }
                                            catch (e:NullPointerException){
                                                ToastInstallApp("Something Went wrong")
                                            }catch (e:JSONException){
                                                ToastInstallApp("Somet7hing Went wrong")
                                            }catch (e:IllegalArgumentException){
                                                ToastInstallApp("Something Went wrong")
                                            }catch (e:Exception){
                                                ToastInstallApp("Something Went wrong")
                                            }
                                        }, Response.ErrorListener {
                                    ToastInstallApp("Check your Network Connection")
//
                                })



                                queyj2.add(jsonobj2)
//                        toast(layoutManager.findLastCompletelyVisibleItemPosition().toString())
                            }
                            catch (e:NullPointerException){
                                ToastInstallApp("Something Went wrong")
                            }catch (e:JSONException){
                                ToastInstallApp("Something Went wrong")
                            }catch (e:IllegalArgumentException){
                                ToastInstallApp("Something Went wrong")
                            }catch (e:Exception){
                                ToastInstallApp("Something Went wrong")
                            }

                        }

                    }
                }
            }
        })


    }

    fun ToastInstallApp(str :String){

        SuperActivityToast.create(this).setText(str).setDuration(Style.DURATION_MEDIUM).setFrame(Style.FRAME_KITKAT).setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED)).setAnimations(Style.ANIMATIONS_POP).show();
    }
}
