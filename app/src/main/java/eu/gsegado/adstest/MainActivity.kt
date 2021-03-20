package eu.gsegado.adstest

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

    }

    /*private fun addMotherFragment(id: Int) {
        val newFragment = MotherFragment()
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()

        transaction.replace(id, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }*/

}