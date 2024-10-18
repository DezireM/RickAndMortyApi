package com.example.rickandmortyapi.ui.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.ui.fragment.CharacterFragment



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, CharacterFragment())
                .commit()
        }
    }
}