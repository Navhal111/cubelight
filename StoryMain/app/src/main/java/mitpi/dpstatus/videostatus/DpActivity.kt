package mitpi.dpstatus.videostatus

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mitpi.dpstatus.videostatus.R
import kotlinx.android.synthetic.main.activity_dp.view.*


class DpActivity : Fragment() {
    lateinit var rootView :View
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater!!.inflate(R.layout.activity_dp, container, false)

        rootView.boys.setOnClickListener{
            val intent = Intent(rootView.context, DpListActivity::class.java)
//
            intent.putExtra("keyName", "Whatsappstatusvideoas");
            startActivity(intent)

        }
        rootView.girls.setOnClickListener {
            val intent = Intent(rootView.context, DpListActivity::class.java)
            intent.putExtra("keyName", "aswhatsappstatusvideo");
            startActivity(intent)
        }
        rootView.cute.setOnClickListener{
            val intent = Intent(rootView.context, DpListActivity::class.java)
            intent.putExtra("keyName", "Cutedpas");
            startActivity(intent)
        }
        return rootView
    }

}
