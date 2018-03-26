package `in`.pistalix.notificationapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException



class MainActivity : AppCompatActivity() {
    var ANDROID_NOTIFICATION_URL = "https://gcm-http.googleapis.com/gcm/send"
    var ANDROID_NOTIFICATION_KEY = "AIzaSyB4CDLosSPJPYIZ_xGRqE8tumkWnC9V5JY"
    var monthlist = listOf<String>("Most", "Love", "Sad", "Roma")
    private var mLastBackPress: Long = 0
    private val mBackPressThreshold: Long = 3500
    private var pressBackToast: Toast? = null
    lateinit var simpleadapteryear: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        simpleadapteryear = ArrayAdapter(this,R.layout.list_row,R.id.txt,monthlist)
        listcategory.adapter = simpleadapteryear
        pressBackToast = Toast.makeText(applicationContext, R.string.press_back_again_to_exit,
                Toast.LENGTH_SHORT)
        notification.setOnClickListener{
            var msg =msg.text.toString()
            var title = title_main.text.toString()
            var image = listcategory.selectedItemId.toString()
            var packege_name = packege_name.text.toString()
            if( msg!="" && msg!=" " && title!="" && title!=" "){
                sendAndroidNotification("dsa",msg,title,packege_name,image)
            }

        }
    }

    @Throws(IOException::class)
    fun sendAndroidNotification(deviceToken: String, message: String, title: String,packege:String,image:String) {
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val obj = JSONObject()
        val msgObject = JSONObject()
        try {
            msgObject.put("message", message)
            msgObject.put("title", title)
            msgObject.put("Title", title)
            msgObject.put("image", image)
            msgObject.put("video", "")
            msgObject.put("video_id", "")
            msgObject.put("packege",packege)

            obj.put("to", "/topics/global")
            obj.put("data", msgObject)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        Thread(Runnable {
            val body = RequestBody.create(mediaType, obj.toString())
            val request = Request.Builder().url(ANDROID_NOTIFICATION_URL).post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("Authorization", "key=" + ANDROID_NOTIFICATION_KEY).build()
            val call = client.newCall(request).execute()
            toast("Notification Send")
        }).start()
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (Math.abs(currentTime - mLastBackPress) > mBackPressThreshold) {
            pressBackToast!!.show()
            mLastBackPress = currentTime
        } else {
            pressBackToast!!.cancel()
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }
}
