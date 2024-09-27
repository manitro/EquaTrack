package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.workers.BackWork
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity(), ViewModelStoreOwner {
	
	private lateinit var activ: Intent


	@SuppressLint("MissingPermission")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val context = this.applicationContext

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
			val checker = registerForActivityResult(ActivityResultContracts.RequestPermission()){
				if(it){
					//call doWork
					val remindRequest : WorkRequest = PeriodicWorkRequestBuilder<BackWork>(2, TimeUnit.HOURS)//self explanatory, 1 hour, the doWork() of periodic worker class is called.
						.build()

//then we submit the request
					WorkManager
						.getInstance(context)
						.enqueueUniquePeriodicWork("reminder", ExistingPeriodicWorkPolicy.UPDATE, remindRequest as PeriodicWorkRequest)
				}
			}
		checker.launch(Manifest.permission.POST_NOTIFICATIONS)
	}
	else{
		//call doWork
			val remindRequest : WorkRequest = PeriodicWorkRequestBuilder<BackWork>(2, TimeUnit.HOURS)//self explanatory, 1 hour, the doWork() of periodic worker class is called.
				.build()

//then we submit the request
			WorkManager
				.getInstance(context)
				.enqueueUniquePeriodicWork("reminder", ExistingPeriodicWorkPolicy.UPDATE, remindRequest as PeriodicWorkRequest)
	}

		setContent {
			MyApplicationTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier
						.padding(16.dp)
						.fillMaxSize(), //try to fill as much space as possible
					color = MaterialTheme.colorScheme.background
				) {
					Column() {
						/* other statesvar
                    listState = rememberLazyListState() 
                    var isTopButton by remember{derivedStateOf{
                    		listState.firstVisibleItemIndex < 3
                    	}
                    }
                    */
						//var lang by remember { mutableStateOf(lang.language) }
						/* we need to put a circle in between every 4 boxes, like so:
		___ ___
		| | | |
		--- ---
		___0___<- the 0 is the circle
		| | | |
		--- ---
		to achieve that: I imagine we can draw a half a circle in the top row, another half in the bottow row, and then we insert the on click listener in the entire screen to match the circle. or we can put Clickable on both half circles. (note: then there cannot be any padding between the rows
		
		a vector drawable is useful here. since the boxes will have one corner cut into an arc each.
		*/

						//get the device dimensions, then set the dimensions of the boxes to be percentages of the device dimension.
						val rowMod = Modifier
							.padding(16.dp)
							.wrapContentSize()

						Box(modifier = Modifier.fillMaxWidth()) {
							Text("Please select an Operation:")
						}

						Button(
							onClick = {
								activ = Intent(context, Purchases::class.java)
								startActivity(activ)
							},
							modifier = Modifier.fillMaxWidth()
						) {
							//Icon()
							Text("Purchases")
						}

						Button(
							onClick = {
								activ = Intent(context, AddEntry::class.java)
								startActivity(activ)
							},
							modifier = Modifier.fillMaxWidth()
						) {
							//Icon()
							Text("Add Purchases")
						}

						Button(
							onClick = {
								activ = Intent(context, MaxBought::class.java)
								startActivity(activ)
							},
							modifier = Modifier.fillMaxWidth()
						) {
							//Icon()
							Text("Maximum spent")
						}
						
						Button(
							onClick = {
								activ = Intent(context, MaxBought::class.java)
								startActivity(activ)
							},
							modifier = Modifier.fillMaxWidth()
						) {
							//Icon()
							Text("Maximum spent")
						}
						/*
			Button(onClick {
				activ = Intent(Purchases::class.java)
			},
			modifier = Modifier.fillMaxWidth()){
				Icon()
				Text("Statistics")
			}*/

						/*Button(
							onClick = {
								activ = Intent(context, Settings::class.java)
								startActivity(activ)
							},
							modifier = Modifier.fillMaxWidth()
						) {
							//Icon()
							Text("Settings")
						}*/
						Text("Equatrack: alpha 1.0")

					}
				}//Surface end...
			}//MyAppTheme end...

		}//setContent end...
	}

	private fun noteCheck(notif : Notification){

	}
}
