package mitpi.dpstatus.videostatus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent

class AboutActivity : AppCompatActivity(),SimpleGestureFilter.SimpleGestureListener {

    override fun onSwipe(direction: Int) {
        when (direction) {
            SimpleGestureFilter.SWIPE_LEFT ->
                onBackPressed()
        }
    }

    override fun onDoubleTap() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun dispatchTouchEvent(me: MotionEvent): Boolean {
        this.detector!!.onTouchEvent(me)
        return super.dispatchTouchEvent(me)
    }

    private var detector: SimpleGestureFilter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        overridePendingTransition(R.xml.enter, R.xml.exit)
        detector = SimpleGestureFilter(this, this)

    }
    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
    }
}
