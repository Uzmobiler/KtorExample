package uz.mobiler.ktorexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import uz.mobiler.ktorexample.databinding.ActivityMainBinding
import uz.mobiler.ktorexample.networking.ApiService
import uz.mobiler.ktorexample.networking.ApiServiceImpl
import uz.mobiler.ktorexample.networking.Response
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    private val myJob = Job()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            launch {
                ApiService.client.use {
                    val service = ApiServiceImpl(it)
                    when (val response = service.getUsers()) {
                        is Response.Error -> {
                            Log.d(TAG, "onCreate: ${response.message}")
                        }
                        is Response.Success -> {
                            Log.d(TAG, "onCreate: ${response.data}")
                        }
                    }
                }
                ApiService.client.close()
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + myJob
}