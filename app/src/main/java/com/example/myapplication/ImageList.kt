package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.io.IOException
import java.io.InputStream


class ImageList : ComponentActivity() {

	private lateinit var imagePaths: List<String>
	private lateinit var context: Context

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		context = applicationContext


		setContent {
			MyApplicationTheme {
				Surface(
					modifier = Modifier.fillMaxWidth(),
					color = MaterialTheme.colorScheme.background
				) {
					val scrollState = rememberScrollState()
					Column(modifier = Modifier
						.fillMaxSize()
						.verticalScroll(scrollState)
						.align()
						) {
							val gotMaps = getBitmapsFromAssets(getImagePathsFromAssets())
							var i = 0
							for (gotMap in gotMaps) {
								SetImage(i, gotMap)
								i += 1
							}
					}
				}
			}
		}
	}


	/*
	@Composable
	fun getImagePathsFromAssets(): List<String> {
		val manager = LocalContext.current.assets
		return try {
			manager.list("images")?.map { "images/$it" } ?: emptyList()
		} catch (e: IOException) {
			e.printStackTrace()
			emptyList()
		}
	}
	*/
	@Composable
	fun getImagePathsFromAssets(): List<String> {
		val manager = LocalContext.current.assets
		val finIst = ArrayList<String>()
		try {
			val ist = manager.list("images") // .map() finds the path of the file
			if (ist != null) {
				for (name in ist) {
					if (name.contains("purch")) {
						finIst.add(name)
					}
				}
			}
		} catch (e: IOException) {
			e.printStackTrace()
		}
		return finIst.map { "images/$it" }
	}


	@Composable
	fun getBitmapsFromAssets(paths: List<String>): ArrayList<Bitmap?> {
		val bitmaps: ArrayList<Bitmap?> = ArrayList()
		val locality = LocalContext.current.assets
		try {
		for (path in paths) {
				val inStream: InputStream = locality.open(path)
				val bitmap = BitmapFactory.decodeStream(inStream)
				bitmaps.add(bitmap)
			}
		}catch (ie: IOException) {
			ie.printStackTrace()
			bitmaps.add(null)
		}
		return bitmaps
	}


	@Composable
	fun SetImage(count: Int, imageMap: Bitmap?) {
		imageMap?.let { img ->
			Image(
				bitmap = img.asImageBitmap(),
				contentDescription = null,
				modifier = Modifier
					.size(140.dp)
					.padding(4.dp)
					.clickable { this.intent.putExtra("imagePath", imagePaths[count]) }
			)
		}

	}

}



