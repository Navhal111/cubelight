package mitpi.dpstatus.videostatus


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mitpi.dpstatus.videostatus.R
import kotlinx.android.synthetic.main.video_state.view.*


class Videofeagment: Fragment()  {
    lateinit var rootView : View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater!!.inflate(R.layout.video_state, container, false)

        rootView.most.setOnClickListener {

            val intent = Intent(rootView.context, VideosList::class.java)
            intent.putExtra("playlistid","PLJg6xDIpYxVqUjGAvUaY2DWxj6srTbigq")
            startActivity(intent)
        }

        rootView.love.setOnClickListener {

            val intent = Intent(rootView.context, VideosList::class.java)
            intent.putExtra("playlistid","PLJg6xDIpYxVo6EmUOGahdY9QIR8KUHP8Z")
            startActivity(intent)
        }
        rootView.roma.setOnClickListener {

            val intent = Intent(rootView.context, VideosList::class.java)
            intent.putExtra("playlistid","PLJg6xDIpYxVptuNqSCWFc1Y3q-4-yhKbH")
            startActivity(intent)
        }
        rootView.sad.setOnClickListener {

            val intent = Intent(rootView.context, VideosList::class.java)
            intent.putExtra("playlistid","PLJg6xDIpYxVoVqPRB9MyW3pWTe8J13JR_")
            startActivity(intent)
        }
        rootView.cute.setOnClickListener {

            val intent = Intent(rootView.context, VideosList::class.java)
            intent.putExtra("playlistid","PLJg6xDIpYxVrOP870F6hZC5BMXv7P_8-l")
            startActivity(intent)
        }
        rootView.birth.setOnClickListener {

            val intent = Intent(rootView.context, VideosList::class.java)
            intent.putExtra("playlistid","PLJg6xDIpYxVpQyAFKq53cZPTpz4hkp6fC")
            startActivity(intent)
        }
        rootView.other.setOnClickListener {

            val intent = Intent(rootView.context, VideosList::class.java)
            intent.putExtra("playlistid","PLJg6xDIpYxVpWOGS_i_ALsMn_Rhcv4aEj")
            startActivity(intent)
        }
        return rootView
    }
}