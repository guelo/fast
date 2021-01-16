package com.miguel.fastassignment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguel.fastassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMovies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MoviesAdapter(layoutInflater)
        }

        binding.searchView.setOnQueryTextListener(searchTermListener)

        handleKeyboardFocus()

        vm.viewState.observe(this, viewStateObserver)

        setupConfirmSelected()
    }

    private val viewStateObserver = Observer<ViewState> {
        when (it) {
            is ViewState.Result -> {
                moviesAdapter().replace(it.result)
                binding.rvMovies.scrollToPosition(0)

                toggleView(binding.rvMovies)
            }
            is ViewState.Error -> {
                moviesAdapter().reset()
                binding.error.text = it.message
                toggleView(binding.error)
            }
            ViewState.Empty -> {
                moviesAdapter().reset()
                toggleView(binding.empty)
            }
            ViewState.Loading -> {
                moviesAdapter().reset()
                toggleView(binding.loading)
            }
        }
    }

    private val searchTermListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            vm.searchTermChanged(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            vm.searchTermChanged(newText)
            return true
        }
    }

    private fun setupConfirmSelected() {
        val confirmItem = binding.toolbar.menu.findItem(R.id.confirm_button)
        confirmItem.isEnabled = false
        moviesAdapter().selectedListener = { confirmItem.isEnabled = it.isNotEmpty() }
        confirmItem.setOnMenuItemClickListener {
            AlertDialog.Builder(this)
                .setTitle("Selected Movies")
                .setItems(moviesAdapter().selected.map { it.title }.toTypedArray(), null)
                .show()
            true
        }

    }

    private fun moviesAdapter() = binding.rvMovies.adapter as MoviesAdapter

    private fun toggleView(view: View) {
        binding.empty.gone()
        binding.error.gone()
        binding.loading.gone()
        binding.rvMovies.gone()
        when (view) {
            binding.empty -> view.visible()
            binding.error -> view.visible()
            binding.loading -> view.visible()
            binding.rvMovies -> view.visible()
        }
    }

    private fun View.visible() {
        visibility = View.VISIBLE
    }

    private fun View.gone() {
        visibility = View.GONE
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleKeyboardFocus() {
        binding.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        }

        binding.rvMovies.setOnTouchListener { v, e ->
            binding.contentContainer.requestFocus()
            false
        }
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
