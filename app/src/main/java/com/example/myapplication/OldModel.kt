package com.example.myapplication

//import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar



data class OldArch (
	var image : Bitmap? = null,
	var product : String = "Product",
	var year : String = Calendar.getInstance().get(Calendar.YEAR).toString(),
	var month : String = Calendar.getInstance().get(Calendar.MONTH).toString(),
	var day : String = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString(),
	var price : String = "Price"
)

class OldModel : ViewModel() {
	//...put any variables you want here
	private val _uiState = MutableStateFlow(OldArch())// notice we passed the data class to the constructor
	val uiState : StateFlow<OldArch> = _uiState.asStateFlow()
	
	private var _entryNumber = 0
	val entryNumber : Int 
		get() = _entryNumber
	//the technique of a '_value' and its 'value' counterpart is called 'Backing property', it somehow protects the data from being tampered with but allows safe access to it.
	
	fun updateState(update : OldArch){
		_uiState.value = update
	}

	
}
