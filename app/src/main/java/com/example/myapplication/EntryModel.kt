package com.example.myapplication

//import android.content.Intent
import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

@SuppressLint("NewApi")

data class EntryArch(
	var image : Bitmap? = null,
	var product : String = "Product",
	var year : String = LocalDateTime.now().year.toString(),
	var month : String = LocalDateTime.now().monthValue.toString(),
	var day : String = LocalDateTime.now().dayOfMonth.toString(),
	var price : String = "Price"
)

class EntryModel : ViewModel() {
	//...put any variables you want here
	private val _uiState = MutableStateFlow(EntryArch())// notice we passed the data class to the constructor
	val uiState : StateFlow<EntryArch> = _uiState.asStateFlow()
	
	private var _entryNumber = 0
	val entryNumber : Int 
		get() = _entryNumber
	//the technique of a '_value' and its 'value' counterpart is called 'Backing property', it somehow protects the data from being tampered with but allows safe access to it.
	
	fun updateState(update : EntryArch){
		_uiState.value = update
	}

	
}
