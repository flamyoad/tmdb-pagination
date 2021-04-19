package com.flamyoad.tmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.flamyoad.tmdb.databinding.ActivityMainBinding
import com.flamyoad.tmdb.ui.MovieListFragment
import kotlinx.android.synthetic.main.activity_main.*

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Prevent the old fragment from being recreated & replaced on screen rotation
        if (savedInstanceState == null) {
            val movieListFragment = MovieListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, movieListFragment, MovieListFragment.TAG)
                .commit()
        }
    }
}
