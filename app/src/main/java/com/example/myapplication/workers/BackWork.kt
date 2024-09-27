package com.example.myapplication.workers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.AddEntry
import com.example.myapplication.R

//example script for background tasks using WorkManager.

class BackWork(context : Context, params : WorkerParameters) : Worker(context, params) {
	private var mContext : Context
	init{
		mContext = context
	}

	override fun doWork() : Result {
		//do the work here for example:
		builder()
		//then once finished, the program returns success.
		return Result.success()
		//Result.failure() for failed work
		//Result.retry() if the failed work should be tried at another time(see 'retry policy')
	}

	@SuppressLint("MissingPermission")
	private fun builder() {
		val notifBuilder = NotificationCompat.Builder(mContext, "reminder_channel")
			.setSmallIcon(R.drawable.notification_icon)
			.setContentTitle("Shopping list")
			.setContentText("What did you buy today?")
			.setStyle(
				NotificationCompat.BigTextStyle() //setStyle is used for different notification sizes
				.bigText("Click to save your shopping list in data."))

		val intent = Intent(mContext, AddEntry::class.java)
		val pendIntent : PendingIntent = getActivity(mContext, 3, intent,
			PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)

		notifBuilder.setContentIntent(pendIntent)
		notifBuilder.setAutoCancel(true)

		val notifManage = NotificationManagerCompat.from(mContext)
		notifManage.notify(1, notifBuilder.build())
	}
}
/*
//now that the worker class is defined, we need to request some work...
//in main class:
val uploadRequest : WorkRequest = OneTimeWorkRequestBuilder<UploadWorker>() //different types of requests exist, simplest cases only need one time requests.
					.build()
//or
val simpleRequest = OneTimeWorkRequest.from(TheWorker::class.java)//for simple work.



//for repeating work requests:
val remindRequest : WorkRequest = PeriodicWorkRequestBuilder<ThePeriodicWorker>(1, TimeUnit.HOURS)//self explanatory, 1 hour, the doWork() of periodic worker class is called.
	.build()

//then we submit the request
WorkManager
	.getInstance(myContext)
	.enqueue(uploadWorkRequest)
	.enqueue(remindRequest)*/
	

