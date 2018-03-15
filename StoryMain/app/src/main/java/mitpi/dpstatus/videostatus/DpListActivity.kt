package mitpi.dpstatus.videostatus

import android.Manifest
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_dp_list.*
import org.jetbrains.anko.toast
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import org.json.JSONArray
import org.json.JSONObject
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd


class DpListActivity : AppCompatActivity() {
    var linearLayoutManager : LinearLayoutManager? = null
    lateinit var jasonArray_post: JSONArray
    val REQUEST_WRITE_EXTERNAL_STORAGE = 1
    var next_port:Boolean = false
    var next_cursor=" "
    var last_int=0
    var user_id = " "
    var hasMore = true
    private var mAdView: AdView? = null
    internal lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dp_list)
        val text = intent.getStringExtra("keyName")
        var adRequest1: AdRequest
        mInterstitialAd = InterstitialAd(this)
        adRequest1 = AdRequest.Builder().build()
        val unitId = getString(R.string.interstial_ads)
        mInterstitialAd.setAdUnitId(unitId)
        mInterstitialAd.loadAd(adRequest1)
        var check = (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)== PackageManager.PERMISSION_GRANTED)
        if (!check) {

            ActivityCompat.requestPermissions(this as Activity,
                    arrayOf(Manifest.permission.ACCESS_NETWORK_STATE),
                    REQUEST_WRITE_EXTERNAL_STORAGE)
        }
//        actionBar.title="Quotes"
        recyclerView.layoutManager = GridLayoutManager(this,3)
        val Get_Post_Queue = Volley.newRequestQueue(this@DpListActivity)

        val Json_object_post = JsonObjectRequest(Request.Method.GET, "https://www.instagram.com/$text/?__a=1",null,

                Response.Listener<JSONObject>
                {
                    response ->
                    //                    toast(response.toString())
                    var user_sate :JSONArray = response.getJSONObject("graphql").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONArray("edges")
                    val main_json_array= JSONArray()
                    var i=0;
                    while(i<user_sate.length()){
                        var main_check=user_sate.get(i) as JSONObject
                        var node = main_check.getJSONObject("node")
                        if(node.get("__typename")=="GraphImage"){
                            main_json_array.put(main_check)

                        }
                        i+=1
                    }
                    jasonArray_post=main_json_array
                    recyclerView.adapter = Recycle_Post(jasonArray_post,mInterstitialAd)
                    var ckeck_jsin_cursor = response.getJSONObject("graphql").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONObject("page_info")
                    if(ckeck_jsin_cursor.getBoolean("has_next_page")){
                        next_cursor = ckeck_jsin_cursor.getString("end_cursor")
                        user_id = response.getJSONObject("graphql").getJSONObject("user").getString("id")
                        next_port=true
                    }
                    if(user_sate.length()!=0){
                        last_int=user_sate.length()
                    }



                }, Response.ErrorListener {
            toast("Network issue")
        })

        Get_Post_Queue.add(Json_object_post)


//
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (hasMore) {
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    //position starts at 0
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {

                        if(next_port && next_cursor != " " && last_int >=12 ){
                            val Get_Post_Queue1 = Volley.newRequestQueue(this@DpListActivity)
//
                            val Json_object_post1 = JsonObjectRequest(Request.Method.GET, "https://instagram.com/graphql/query/?query_id=17888483320059182&id=$user_id&first=12&after=$next_cursor",null,

                                    Response.Listener<JSONObject>
                                    {
                                        response ->
                                        var user_sate :JSONArray = response.getJSONObject("data").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONArray("edges")
                                        var i=0;
                                        while(i<user_sate.length()){
                                            var main_check=user_sate.get(i) as JSONObject
                                            var node = main_check.getJSONObject("node")
                                            if(node.get("__typename")=="GraphImage"){

                                                jasonArray_post.put(main_check)

                                            }
                                            i+=1
                                        }
                                        recyclerView.adapter.notifyDataSetChanged()
                                        var ckeck_jsin_cursor = response.getJSONObject("data").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONObject("page_info")
                                        if(ckeck_jsin_cursor.getBoolean("has_next_page")){
                                            next_cursor = ckeck_jsin_cursor.getString("end_cursor")
                                            next_port=true
                                        }else{
                                            next_cursor = " "
                                            next_port=false
                                        }
                                        if(user_sate.length()!=0){
                                            last_int=user_sate.length()
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


    public override fun onPause() {
        if (mAdView != null) {
            mAdView!!.pause()
        }
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        if (mAdView != null) {
            mAdView!!.resume()
        }
    }

    public override fun onDestroy() {
        if (mAdView != null) {
            mAdView!!.destroy()
        }
        super.onDestroy()
    }


    override fun onBackPressed(){
        finish()
    }
}


