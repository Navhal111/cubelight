package mitpi.dpstatus.videostatus

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.util.ArrayList

/**
 * Created by lime on 12/2/18.
 */
class SingelFunction {

        internal var m_id: Long? = null
        var mainJson : JSONArray? = null
        var set1 =0;
        fun DounloadVideos(): JSONArray {
            var VideoId = JSONArray()
            val externalDirectory = Environment.getExternalStorageDirectory().toString()
            val files = File(externalDirectory+ "/DpStatus/")
            val inFiles = ArrayList<File>()
            val fileslist = files.listFiles()

            if(fileslist != null){
                for (file in fileslist) {
                    if(file.length()>0 && file.extension == "mp4"){
                        inFiles.add(file)
                        var TestId = JSONObject()
                        var Teatarray = file.name.split(" $ ")
                        if(Teatarray.size > 1){
                            TestId.put("Id",Teatarray[1].replace(".mp4",""))
                            VideoId.put(TestId)
                        }
                    }


                }
                return VideoId
            }
            return VideoId
        }

        fun DounloadVideosName(): ArrayList<File> {
            val externalDirectory = Environment.getExternalStorageDirectory().toString()
            val files = File(externalDirectory+ "/DpStatus/")
            val inFiles = ArrayList<File>()
            if(files.exists()){
                val fileslist = files.listFiles()
                if(fileslist != null){
                    for (file in fileslist){
                        if(file.length()>0 && file.extension == "mp4"){
                            inFiles.add(file)
                        }

                    }
                    return inFiles
                }
                return inFiles
            }
            return inFiles
        }

    fun DownloadVideUrl(videoid:String,filename:String,context:Context){
        var mainurl = "https://youtubemydown.000webhostapp.com/getvideo.php?videoid=$videoid&type=Download"
        val Virsionquery = Volley.newRequestQueue(context)

        val jsonobj2 = StringRequest(Request.Method.GET, mainurl,
                Response.Listener<String> {
                    response ->
                    try{
                        var Jsonaaray = JSONArray(response);
                        var mainjson= Jsonaaray.getJSONObject(0)

                        val uri = Uri.parse(mainjson.getString("url"))
                        val request = DownloadManager.Request(uri)
                        request.setTitle("MainStory")

                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        val downloadfolder = File(Environment.getExternalStorageDirectory(), "MainStory")
                        if (!downloadfolder.exists()) {
                            downloadfolder.mkdirs()
                        }
                        request.setDestinationInExternalPublicDir("MainStory", "demo.mp4")

                        val manager = context.applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                        var m_id = manager.enqueue(request)

                    }catch (e: JSONException){

                    }catch (e:NullPointerException){

                    }catch (e:IllegalArgumentException){

                    }catch (e:Exception){
                    }
                }, Response.ErrorListener {

        })
        Virsionquery.add(jsonobj2)

    }
}