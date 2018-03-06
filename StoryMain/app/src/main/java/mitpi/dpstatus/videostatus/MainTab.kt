package mitpi.dpstatus.videostatus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main_tab.*
import android.support.v4.view.ViewPager
import android.support.v4.app.FragmentStatePagerAdapter
import mitpi.dpstatus.videostatus.R
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils
import it.neokree.materialtabs.MaterialTab
import it.neokree.materialtabs.MaterialTabListener


class MainTab : AppCompatActivity(), MaterialTabListener {

    lateinit var adapter: ViewPagerAdapter
    var state :String =" "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tab)
        overridePendingTransition(R.xml.enter, R.xml.exit);

        adapter = ViewPagerAdapter(supportFragmentManager);
        pager.adapter = adapter;
        try{
            state = intent.getStringExtra("set")

        }catch (e:Exception){
            state = "image"
            ToastInstallSucc("Something Went Wrong")
        }

        pager.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position)

            }
        })
        for (i in 0 until adapter.getCount()) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            )

        }


    }

    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(num: Int): Fragment {
            if(num == 0){
                if(state=="image"){
                    return DpActivity()
                }else{
                    return Videofeagment()
                }
            }else{
                if(state=="image"){
                    return Videofeagment()
                }else{
                    return DpActivity()
                }

            }

        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence {
            if(position == 0){
                if(state=="image"){
                    return "Images"
                }else{
                    return "Videos"
                }
            }else{
                if(state=="image"){
                    return "Videos"
                }else{
                    return "Images"
                }
            }

        }

    }
    override fun onTabReselected(tab: MaterialTab?) {

    }

    override fun onTabUnselected(tab: MaterialTab?) {

    }

    override fun onTabSelected(tab: MaterialTab?) {
        if (tab != null) {
            pager.currentItem = tab.position
        }
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.xml.nathing, R.xml.exit)

    }

    fun ToastInstallSucc(str :String){

        SuperActivityToast.create(this).setText(str).setDuration(Style.DURATION_MEDIUM).setFrame(Style.FRAME_KITKAT).setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED)).setAnimations(Style.ANIMATIONS_POP).show();
    }
}
