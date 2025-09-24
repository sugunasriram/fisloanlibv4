package com.github.sugunasriram.fisloanlibv4.fiscode.network.sse

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.FileLogger
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.BaseViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class SSEViewModel : BaseViewModel() {

    // StateFlow to emit events received from SSE
    private val _events = MutableStateFlow<String>("")
    val events: StateFlow<String> = _events

    private lateinit var sseClient: SSEClient

// OkHttpClient SSE
    @OptIn(FlowPreview::class)
    fun startListening(url: String) {
        viewModelScope.launch {
            try {
                // Get the singleton instance of SSEClient from the Companion object
                sseClient = SSEClient(url, viewModelScope)

                // Start listening using the singleton instance
                sseClient.start()

                // Collect and process events from the SSEClient
                sseClient.events
                    .filter { it.isNotEmpty() } // Filter out empty events
                    .debounce(500) // Debounce to prevent rapid successive updates
                    .collect { event ->
                        _events.value = event // Update the StateFlow with new event
                        Log.wtf("Checking  SSE", event)
                        FileLogger.writeToFile(event, false)
                    }
            } catch (e: CancellationException) {
                // Handle coroutine cancellation (likely due to ViewModel being cleared)
                Log.d(
                    "SSEViewModel",
                    "Coroutine was cancelled, likely due to ViewModel being cleared: ${e.message}"
                )
                throw e
            } catch (e: Exception) {
                // Log any other exceptions that occur during SSE listening
                e.printStackTrace()
            } finally {
                // Log when the coroutine completes or is cancelled
                Log.d("SSEViewModel", "Coroutine has completed or was cancelled.")
            }
        }
    }

    override fun onCleared() {
        stopListening()
        super.onCleared()
    }

    fun stopListening() {
        if (::sseClient.isInitialized) {
            sseClient.stop()
        }
    }

// java HttpsURLConnection SSE

//    @OptIn(FlowPreview::class)
//    fun startListening(url: String) {
//        viewModelScope.launch {
//            try {
//                // Get the singleton instance of SSEClient from the Companion object
//                val sseClient = SSEClient.getInstance(url, viewModelScope)
//
//                // Start listening using the singleton instance
//                sseClient.startListening()
//
//                // Collect and process events from the SSEClient
//                sseClient.events
//                    .filter { it.isNotEmpty() } // Filter out empty events
//                    .debounce(500) // Debounce to prevent rapid successive updates
//                    .collect { event ->
//                        _events.value = event // Update the StateFlow with new event
//                        Log.wtf("Checking  SSE",event)
//                        FileLogger.writeToFile(event,false)
//                    }
//            } catch (e: CancellationException) {
//                // Handle coroutine cancellation (likely due to ViewModel being cleared)
//                Log.d("SSEViewModel", "Coroutine was cancelled, likely due to ViewModel being cleared: ${e.message}")
//                throw e
//            } catch (e: Exception) {
//                // Log any other exceptions that occur during SSE listening
//                e.printStackTrace()
//            } finally {
//                // Log when the coroutine completes or is cancelled
//                Log.d("SSEViewModel", "Coroutine has completed or was cancelled.")
//            }
//        }
//    }
//
//    fun emptyEvents() {
//        _events.value = ""
//    }
//
//    // Method to stop listening to SSE events
//    fun stopListening() {
//        // Get the instance of SSEClient and stop listening
//        emptyEvents()
//        val sseClient = SSEClient.getInstance("", viewModelScope) // Using empty strings as it's a singleton, no new instance will be created
//        SSEClient.instance = null
//        sseClient.stopListening()
//    }
//
//    // Override onCleared to ensure SSEClient stops listening when ViewModel is cleared
//    override fun onCleared() {
//        super.onCleared()
//        //Sugu - to check
////        stopListening() // Stop listening to avoid memory leaks or unfinished coroutines
//        emptyEvents()
//    }
//
//    fun restartListening(url: String) {
//        stopListening()
//        startListening(url)
//    }
//
}
