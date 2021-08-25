package ar.com.astroapp.ui.apod

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ar.com.astroapp.R
import ar.com.astroapp.core.Result
import ar.com.astroapp.data.remote.RemoteApodDataSource
import ar.com.astroapp.databinding.FragmentApodBinding
import ar.com.astroapp.presentation.ApodViewModel
import ar.com.astroapp.presentation.ApodViewModelFactory
import ar.com.astroapp.repository.ApodRepositoryImpl
import ar.com.astroapp.repository.RetrofitClient
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ApodFragment : Fragment(R.layout.fragment_apod) {

    private lateinit var binding: FragmentApodBinding

    private val viewModel by viewModels<ApodViewModel> {
        ApodViewModelFactory(
            ApodRepositoryImpl(
                RemoteApodDataSource(RetrofitClient.webservice)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentApodBinding.bind(view)

        viewModel.fetchApod()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getApod().collect {
                    when(it){
                        is Result.Loading -> {
                            Log.d("apod", "Loading: Loading...")
                            binding.progressBar.visibility = View.VISIBLE
                            binding.ivReload.visibility = View.GONE
                            binding.linearLayoutApod.visibility = View.INVISIBLE
                        }
                        is Result.Success -> {
                            Log.d("apod", "Success: ${it.data}")
                            binding.progressBar.visibility = View.GONE
                            binding.ivReload.visibility = View.GONE
                            binding.linearLayoutApod.visibility = View.VISIBLE
                            binding.tvTitleApod.text = it.data.title
                            binding.tvDateApod.text = it.data.date
                            binding.tvDescriptionApod.text = it.data.explanation
                            Glide.with(requireActivity()).load(it.data.url).into(binding.ivApod)

                        }
                        is Result.Failure -> {
                            Log.d("apod", "Failure: ${it.exception}")
                            binding.progressBar.visibility = View.GONE
                            binding.ivReload.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), "${it.exception}", Toast.LENGTH_SHORT).show()
                            binding.ivReload.setOnClickListener {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.ivReload.visibility = View.GONE
                                viewModel.fetchApod()
                            }

                        }
                    }
                }
            }
        }
    }
}