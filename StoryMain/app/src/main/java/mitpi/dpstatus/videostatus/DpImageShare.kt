package mitpi.dpstatus.videostatus

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.bumptech.glide.Glide
import mitpi.dpstatus.videostatus.R
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils
import com.google.android.gms.ads.AdRequest
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_share_set.*
import java.io.File
import java.io.FileOutputStream

class DpImageShare : AppCompatActivity() {
    var REQUEST_WRITE_EXTERNAL_STORAGE =1
    lateinit var url:String
    lateinit var thumimage:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_share_set)

        try {
            val adRequest = AdRequest.Builder()
                    .build()
            adView.loadAd(adRequest)
        url = intent.getStringExtra("image")
        thumimage = intent.getStringExtra("imagethum")
//            Glide.with(this).load(url).into(post_image_view)
        Picasso.with(this).load(url).into(post_image_view);
        }catch (e:NullPointerException){
            ToastInstallApp("No Image Get From Server")
        }
        catch (e: Exception) {
            ToastInstallApp("Somthing Went Wrong")
        }
        ImageDownload.setOnClickListener{

            var bitmap: Bitmap? = null
            try {
                // Download Image from URL
                val input = java.net.URL(url).openStream()
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input)
                var check = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                if (!check) {

                    ActivityCompat.requestPermissions(this as Activity,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            REQUEST_WRITE_EXTERNAL_STORAGE)
                }

                val externalDirectory = Environment.getExternalStorageDirectory().toString()
                val folder = File(externalDirectory + "/DpStatus")
                if (!folder.exists()) {
                    folder.mkdir()
                }
                val file = File(folder, "share_image_" + System.currentTimeMillis() + ".jpg")
                val out = FileOutputStream(file)
                SingleMediaScanner(this, file)
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.close()
                ToastInstallSucc("Image Has Downloaded")
            }catch (e:NullPointerException){
                ToastInstallApp("No Image Get From Server")
            }
            catch (e: Exception) {
                ToastInstallApp("Somthing Went Wrong")
            }


        }

        ImageShare.setOnClickListener {

            var bitmap: Bitmap
            try {

                val input = java.net.URL(url).openStream()
                bitmap = BitmapFactory.decodeStream(input)
                var check = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                if (!check) {

                    ActivityCompat.requestPermissions(this as Activity,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            REQUEST_WRITE_EXTERNAL_STORAGE)
                }

                val externalDirectory = Environment.getExternalStorageDirectory().toString()
                val folder = File(externalDirectory + "/DpStatus")
                if (!folder.exists()) {
                    folder.mkdir()
                }
                val file = File(folder, "share_image_" + System.currentTimeMillis() + ".jpg")
                val out = FileOutputStream(file)
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.close()
                var bmpUri = Uri.fromFile(file)
                // Download Image from URL
                val intent = Intent(android.content.Intent.ACTION_SEND)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_TEXT, "Share from  https://play.google.com/store/apps/details?id=mitpi.dpstatus.videostatus");
                intent.putExtra(Intent.EXTRA_STREAM, bmpUri)
                startActivity(Intent.createChooser(intent, "Share via"))
            }catch (E:NullPointerException){
                ToastInstallApp("Image Not Found")
            }catch (E:NullPointerException){
                ToastInstallApp("Image Not Found")
            }catch (E:Exception){

            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    fun ToastInstallSucc(str :String){

        SuperActivityToast.create(this).setText(str).setDuration(Style.DURATION_MEDIUM).setFrame(Style.FRAME_KITKAT).setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN)).setAnimations(Style.ANIMATIONS_POP).show();
    }

    fun ToastInstallApp(str :String){

        SuperActivityToast.create(this).setText(str).setDuration(Style.DURATION_MEDIUM).setFrame(Style.FRAME_KITKAT).setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED)).setAnimations(Style.ANIMATIONS_POP).show();
    }
}
