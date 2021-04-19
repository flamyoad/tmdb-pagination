package com.flamyoad.tmdb.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.flamyoad.tmdb.MainActivity
import com.flamyoad.tmdb.R
import com.flamyoad.tmdb.adapter.MovieProdCompaniesAdapter
import com.flamyoad.tmdb.databinding.ActivityMovieDetailsBinding
import com.flamyoad.tmdb.model.MovieDetails

@ExperimentalPagingApi
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding

    private val viewModel: MovieDetailsViewModel by viewModels()

    private val listAdapter = MovieProdCompaniesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val movieId = intent.getIntExtra(MovieListFragment.MOVIE_ID, -1)
        viewModel.fetchMovieDetails(movieId)
        initCompanyList()

        viewModel.movieDetails().observe(this) {
            processMovieDetails(it)
            listAdapter.setList(it.productionCompanies)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movie_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }


    private fun processMovieDetails(details: MovieDetails) {
        with(binding) {
            Glide.with(this@MovieDetailsActivity)
                .load(details.getBackdropUrl())
                .into(imgBackdrop)

            Glide.with(this@MovieDetailsActivity)
                .load(details.getPosterUrl())
                .into(imgPoster)

            txtTitle.text = details.title
            txtTagline.text = details.tagline
            txtUserScore.text = details.getUserScore()
            txtGenres.text = details.getGenres()
            txtTime.text = details.getPlaytime()
            txtLanguage.text = details.getLanguages()
        }

        with(binding.summaryLayout) {
            txtMainTitle.text = details.title
            txtSummary.text = details.summary
            txtCountries.text = details.getProdCountries()
        }
    }

    private fun initCompanyList() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        with(binding.summaryLayout) {
            listCompanies.layoutManager = linearLayoutManager
            listCompanies.adapter = listAdapter
        }
    }
}
