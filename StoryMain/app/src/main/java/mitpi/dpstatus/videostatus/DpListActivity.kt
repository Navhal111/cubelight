package mitpi.dpstatus.videostatus

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import mitpi.dpstatus.videostatus.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_dp_list.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DpListActivity : AppCompatActivity() {
    val REQUEST_WRITE_EXTERNAL_STORAGE = 1
    lateinit var jasonArray_post: JSONArray
    var last_int=0
    var max_id: String? = null
    var hasMore = true
    internal lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dp_list)
        val text = intent.getStringExtra("keyName")
        var check = (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)== PackageManager.PERMISSION_GRANTED)
        if (!check) {

            ActivityCompat.requestPermissions(this as Activity,
                    arrayOf(Manifest.permission.ACCESS_NETWORK_STATE),
                    REQUEST_WRITE_EXTERNAL_STORAGE)
        }
        mInterstitialAd = InterstitialAd(this)
        var adRequest = AdRequest.Builder()
                .build()
        val unitId = getString(R.string.interstial_ads)
        mInterstitialAd.setAdUnitId(unitId)
        mInterstitialAd.loadAd(adRequest)
        recyclerView.layoutManager = GridLayoutManager(this,3)
        val Get_Post_Queue = Volley.newRequestQueue(this@DpListActivity)

        val Json_object_post = JsonObjectRequest(Request.Method.GET, "https://www.instagram.com/$text/?__a=1",null,

                Response.Listener<JSONObject>
                {
                    response ->
                    //                    toast(response.toString())

                    val user_main: JSONObject = response.get("user") as JSONObject
                    val node_all: JSONObject = user_main.get("media") as JSONObject
                    val array_Json: JSONArray = node_all.get("nodes") as JSONArray
                    val main_json_array= JSONArray()
//                    json_post.text=array_Json.toString()
                    var i=0;
                    while(i<array_Json.length()){
                        var main_check=array_Json.get(i) as JSONObject
                        if(main_check.get("__typename")=="GraphImage"){

                            main_json_array.put(main_check)

                        }
                        i+=1
                    }
                    jasonArray_post=main_json_array
                    recyclerView.adapter = Recycle_Post(jasonArray_post, mInterstitialAd)
                    if(array_Json.length()!=0){
                        last_int=array_Json.length()

                        var demo = array_Json.get(array_Json.length()-1) as JSONObject
                        max_id= demo["id"].toString()
                    }else{
                        toast("Somthing Went wrong")
                    }


                }, Response.ErrorListener {
            toast("Network issue")
        })

        Get_Post_Queue.add(Json_object_post)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (hasMore) {
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    //position starts at 0
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {

                        if(last_int>=12){
                            val Get_Post_Queue1 = Volley.newRequestQueue(this@DpListActivity)
//
                            val Json_object_post1 = JsonObjectRequest(Request.Method.GET, "https://www.instagram.com/$text/?__a=1&max_id=$max_id",null,

                                    Response.Listener<JSONObject>
                                    {
                                        response ->
                                        //                    toast(response.toString())

                                        val user_main: JSONObject= response.get("user") as JSONObject
                                        val node_all: JSONObject= user_main.get("media") as JSONObject
                                        val array_Json:JSONArray = node_all.get("nodes") as JSONArray
                                        var main_json_array= JSONObject()
                                        last_int=array_Json.length()
                                        var i=0
                                        while(i<array_Json.length()){
                                            main_json_array=array_Json.getJSONObject(i)
                                            if(main_json_array.get("__typename")=="GraphImage"){

                                                jasonArray_post.put(array_Json.get(i))
                                            }

                                            i+=1

                                        }
                                        try{
                                            recyclerView.adapter.notifyDataSetChanged()
                                            var demo = array_Json.get(array_Json.length()-1) as JSONObject
                                            max_id= demo["id"].toString()
                                        }catch (e: JSONException){

                                        }catch (e:Exception){

                                        }


                                    }, Response.ErrorListener {
                                toast("Network issue")
                            })

                            Get_Post_Queue1.add(Json_object_post1)
//                        toast(layoutManager.findLastCompletelyVisibleItemPosition().toString())
                        }


                    }
                }
            }
        })


    }
}

