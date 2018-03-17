package kyarra.gujratisongs

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.SharedPreferencesCompat
import android.support.v7.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.leo.simplearcloader.ArcConfiguration
import com.leo.simplearcloader.SimpleArcDialog
import com.leo.simplearcloader.SimpleArcLoader
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var mDialog:SimpleArcDialog
    lateinit var configuration :ArcConfiguration
    lateinit var CrateList : SharedPreferences
    private val REQUEST_WRITE_EXTERNAL_STORAGE = 1
    var titleList = arrayOf("All Gujarati Videos", "Gujarati New Movie Songs", "Gujarati Geeto", "Gujarati Hits", "Gujarati Balgeet", "Navratri Songs", "Gujarati Jokes", "Gujarati Nursery Rhymes", "Gujarati Marriage Songs", "Gujarati Folk Songs", "Gujarati Bewafa Songs", "Gujarati Movie Trailer", "Gujarati Classical Songs", "Gujarati Full Movie", "Gujarati Dayro", "Gujarati Natak", "Gujarati DJ Songs", "Gujarati Halardu Songs", "Old Gujarati Songs", "Gujarati Garba Songs", "Gujarati Evergreen Songs", "Hemant Chauhan Garba", "Top Gujarati Natak", "New Hindi Songs", "Falguni Pathak Garba Songs", "Kinjal Dave Garba Songs", "Naresh Kanodia Songs", "Top Gujarati Bhajans", "Gujarati Krishna Songs", "Gujarati Bhakti Songs", "Gujarati Santvani", "Gujarati Mehndi Songs", "Hiten Kumar Songs", "Gujarati New Recipes", "Gujarati Video Geet", "Gujarati Classical Songs", "Gujarati Aarti Songs", "Dhirubhai Sarvaiya Jokes", "Mayabhai Ahir Jokes", "Gujarati Sad Songs", "Gujarati Pithi Songs", "Gujarati Romantic Songs", "Gujarati Rap Songs", "Gujarati Dandiya Raas Garba", "Gujarati Religious Songs", "Gujarati Timli Songs", "Upendra Trivedi Songs", "Gujarati Urban Songs", "Gujarati Titoda Songs", "Vikram Thakor Gujarati Songs", "Geeta Rabari Songs", "Jignesh Kaviraj Songs", "Gujarati Dakla", "Osman Mir Songs", "Mogal Maa Songs", "Mamta Soni Songs", "Kirtidan Gadhvi Songs", "Alpa Patel Songs")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val check = (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        if (!check) {
            ActivityCompat.requestPermissions(this as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL_STORAGE)
        }

        var collorlist: IntArray = kotlin.IntArray(2)
        collorlist.set(0, ContextCompat.getColor(this, R.color.colorPrimary))
        collorlist.set(1, ContextCompat.getColor(this, R.color.colorAccent))
        mDialog = SimpleArcDialog(this)
        configuration = ArcConfiguration(this)
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.SIMPLE_ARC);
        configuration.setText("Loading...")
        mDialog.setConfiguration(configuration)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
        CrateList = getSharedPreferences("List",Context.MODE_PRIVATE)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecycleList(titleList)
        SetAppList()
    }

    private fun SetAppList() {
        val url = application.getString(R.string.getids)
        val request = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
            try {
                var editor = CrateList.edit()
                editor.putString("VideoList",response.toString())
                editor.commit()
                mDialog.dismiss()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { })
        request.add(jsonObjectRequest)
    }
}
