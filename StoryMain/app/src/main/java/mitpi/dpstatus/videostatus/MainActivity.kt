package mitpi.dpstatus.videostatus

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import mitpi.dpstatus.videostatus.R
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonroids.gif.GifDrawable


class MainActivity : AppCompatActivity() {
    private val REQUEST_WRITE_EXTERNAL_STORAGE = 1
    private var pressBackToast: Toast? = null
    private val SPLASH_TIME_OUT = 13000
    private var mLastBackPress: Long = 0
    private val mBackPressThreshold: Long = 3500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pressBackToast = Toast.makeText(applicationContext, R.string.press_back_again_to_exit,
                Toast.LENGTH_SHORT)
        val adRequest = AdRequest.Builder()
                .build()
        adView.loadAd(adRequest)
        val check = (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        if (!check) {

            ActivityCompat.requestPermissions(this@MainActivity as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL_STORAGE)
        }
        val gifFromResource = GifDrawable(resources, R.drawable.logo2)
        animation.setImageDrawable(gifFromResource)
        gifFromResource.start()
//            toast(gifFromResource.duration.toString())
        Handler().postDelayed(
        {
            gifFromResource.stop()
        },SPLASH_TIME_OUT.toLong())


        dp.setOnClickListener{
            var intent = Intent(this, MainTab::class.java)
            intent.putExtra("set","image")
            startActivity(intent)
        }

        status.setOnClickListener{
            var intent = Intent(this, MainTab::class.java)
            intent.putExtra("set","video")
            startActivity(intent)
        }
        rateus.setOnClickListener {

            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=mitpi.dpstatus.videostatus")))
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=mitpi.dpstatus.videostatus")))
            }
        }
        share.setOnClickListener{

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain*"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Beautiful DP And Video Status For Whatsapp");
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=mitpi.dpstatus.videostatus");
            startActivity(Intent.createChooser(intent, "via"))
        }


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
