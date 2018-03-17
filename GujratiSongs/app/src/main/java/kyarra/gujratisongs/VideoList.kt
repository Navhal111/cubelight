package kyarra.gujratisongs

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.leo.simplearcloader.ArcConfiguration
import com.leo.simplearcloader.SimpleArcDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import org.json.JSONArray



class VideoList : AppCompatActivity() {
    lateinit var CrateList : SharedPreferences
    lateinit var mDialog: SimpleArcDialog
    lateinit var configuration : ArcConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)
        CrateList = getSharedPreferences("List", Context.MODE_PRIVATE)
        var MainJson = JSONObject(CrateList.getString("VideoList","{'no':'no'}"))

        if (MainJson.has("no")){
            toast("No Video Found")
        }else{
            var index = intent.getStringExtra("index")
            val arrJson = MainJson.getJSONArray(index)
            val arr = arrayOfNulls<String>(arrJson.length())
            for (i in 0 until arrJson.length())
                arr[i] = arrJson.getString(i)
            var newList = arr as Array<String>
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = VideoRecycleList(newList)
        }

    }
}
