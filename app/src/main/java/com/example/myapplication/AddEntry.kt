package com.example.myapplication

//import android.content.Intent
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.io.IOException
import java.io.InputStream
import java.time.LocalDateTime
import java.util.Calendar


class AddEntry : ComponentActivity(){

	//private lateinit var lister : ArrayList<Array<Any>>  for use in the advanced version.
	private lateinit var context : Context
	private var bitPath : String? = null


    private var aproduct = "none"
    private lateinit var ayear : String
    private lateinit var amonth : String
    private lateinit var aday : String
    private var aprice = "none"
	
     @SuppressLint("SuspiciousIndentation")
     override fun onCreate(savedInstanceState: Bundle?){
       super.onCreate(savedInstanceState)


       context = this.applicationContext //for out of scope access
         ayear = LocalDateTime.now().year.toString()
         amonth = LocalDateTime.now().monthValue.toString()
         aday = LocalDateTime.now().dayOfMonth.toString()
       /*lister = ArrayList()
       lister = getResources().getStringArray(R.languages.English)*/
       val resIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
                		//turn the image path from the result into a bitmap.
                        bitPath = result.data?.getStringExtra("imagePath").toString()
                	}//opening ImageList for getting an image's path
         resIntent.launch(Intent(this, ImageList::class.java))
       setContent {
       		MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxSize(), //try to fill as much space as possible
                    color = MaterialTheme.colorScheme.background
                ) {
					Column (modifier = Modifier.fillMaxSize()){
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//in the viewModels we use dates...
							if(bitPath!=null){
								SetImage() //for API => 26 we can use java.time.LocalDateTime(EntryModel)
							}
							EntryBlock()  //Note: Build.VERSION_CODES.0 : Version code O is API:26
						} else {
							if(bitPath!=null){
								ChooseImage() //but for API < 26 we need to use java.util.Calendar(OldModel)
							}
							OldBlock()
						}
						}
					}
				}
			   }
     }


	
	/* we will need to use a popup window or contract activity for selecting a standard purchase and then add the name and price to the text fields
	val registerIntent = registerForActivityResult(ActivityResultContract.StartActivityForResult()){ res ->
		name = res.getResultString(name) or somthing
	}
	val intento = Intent(this, ProductSelect::class.java)
	registerIntent.launch(intento)
	
	textPrice = getString(R.value.Purchases.)
	*/
	
	@SuppressLint("SuspiciousIndentation", "NewApi")
	@Composable
	fun EntryBlock(entryModel : EntryModel = viewModel()) {
        val entryArch by entryModel.uiState.collectAsState()
		Column(modifier = Modifier.fillMaxWidth()) {
			
			var produ by remember{ mutableStateOf("") }
			var years by remember{ mutableStateOf(LocalDateTime.now().year.toString()) }
			var moons by remember{ mutableStateOf(LocalDateTime.now().monthValue.toString()) }
			var days by remember{ mutableStateOf(LocalDateTime.now().dayOfMonth.toString()) }
			var cash by remember{ mutableStateOf("") }

			var applyEnb by remember { mutableStateOf(false) }
		Row(modifier = Modifier.fillMaxWidth()){
			Text("Product")
			BasicTextField(
				produ,
				onValueChange = {
					produ = it
                    aproduct = it
					//entryModel.updateState(entryArch.copy(product = it))
				},
				modifier = Modifier
					.background(Color.White)
					.fillMaxWidth()
					.padding(4.dp)
			)}
		Row(modifier = Modifier.fillMaxWidth()){
			Text("Year")
			BasicTextField(
				years,
				onValueChange = {
					years = it
                    ayear = it
					//entryModel.updateState(entryArch.copy(year = it))
				},
				modifier = Modifier
					.background(Color.White)
					.fillMaxWidth()
					.padding(4.dp)
			)
			}
		Row(modifier = Modifier.fillMaxWidth()){
			Text("Month")
			BasicTextField(
				moons,
				onValueChange = {
					moons = it
                    amonth = it
					//entryModel.updateState(entryArch.copy(month = it))
				},
				modifier = Modifier
					.background(Color.White)
					.fillMaxWidth()
					.padding(4.dp)
			)
			}
			
		Row(modifier = Modifier.fillMaxWidth()){
			Text("Day")
			BasicTextField(
				days,
				onValueChange = {
					days = it
                    aday = it
					//entryModel.updateState(entryArch.copy(day = it))
				},
				modifier = Modifier
					.background(Color.White)
					.fillMaxWidth()
					.padding(4.dp)
			)
			}
		Row(modifier = Modifier.fillMaxWidth()){
			Text("Price")
			BasicTextField(
				cash,
				onValueChange = {
					cash = it
                    aprice = it
					//entryModel.updateState(entryArch.copy(price = it))
				},
				modifier = Modifier
					.background(Color.White)
					.fillMaxWidth()
					.padding(4.dp)
			)
			}

			Button(
				onClick = {
					entryModel.updateState(entryArch.copy(product = aproduct, year = ayear, month = amonth, day = aday, price = aprice))
					/*entryModel.updateState(entryArch.copy(,year = ayear))
					entryModel.updateState(entryArch.copy(,month = amonth))
					entryModel.updateState(entryArch.copy(,day = aday)) note: updating multiple data values must be at the same time (same line)
					entryModel.updateState(entryArch.copy(,price = aprice))*/
					applyEnb = true
				},
				modifier = Modifier
					.fillMaxWidth()
			) { Text("Apply Values") }

			Button(
				onClick = {
					saveTheData(entryArch)
				},
				modifier = Modifier
					.fillMaxWidth(),
				enabled = applyEnb
			) { Text("Add current values") }
			Row(){
				Text(text = entryArch.product, modifier = Modifier.padding(2.dp))
				Text(text = entryArch.year, modifier = Modifier.padding(2.dp))
				Text(text = entryArch.month, modifier = Modifier.padding(2.dp))
				Text(text = entryArch.day, modifier = Modifier.padding(2.dp))
				Text(text = entryArch.price, modifier = Modifier.padding(2.dp))
			}

		}
	}

	@SuppressLint("SuspiciousIndentation")
	@Composable
	fun OldBlock(oldModel : OldModel = viewModel()) {
		val oldArch by oldModel.uiState.collectAsState()
		Column(modifier = Modifier.fillMaxSize()) {
		
			var produ by remember{ mutableStateOf(oldArch.product) }
			var years by remember{ mutableStateOf(Calendar.getInstance().get(Calendar.YEAR).toString()) }
			var moons by remember{ mutableStateOf(Calendar.getInstance().get(Calendar.MONTH).toString()) }
			var days by remember{ mutableStateOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString(),) }
			var cash by remember{ mutableStateOf(oldArch.price) }


			BasicTextField(
				value = produ,
				//value = oldArch.product,
				onValueChange = {
					produ = it
					//oldModel.updateState(oldArch.copy(product = it))
				},
				modifier = Modifier
					.background(Color.White)
					.fillMaxWidth()
					.padding(4.dp)
			)

			BasicTextField(
				value = years,
				//value = oldArch.year,
				onValueChange = {
					years = it
					//oldModel.updateState(oldArch.copy(year = it))
				},
				modifier = Modifier
					.background(Color.White)
					.fillMaxWidth()
					.padding(4.dp)
			)

			BasicTextField(
				value = moons,
				//value = oldArch.month,
				onValueChange = {
					moons = it
					//oldModel.updateState(oldArch.copy(month = it))
				},
				modifier = Modifier
					.background(Color.White)
					.fillMaxWidth()
					.padding(4.dp)
			)

			BasicTextField(
				value = days,
				//value = oldArch.day,
				onValueChange = {
					days = it
					//oldModel.updateState(oldArch.copy(day = it))
				},
				modifier = Modifier
					.background(Color.White)
					.fillMaxWidth()
					.padding(4.dp)
			)

			BasicTextField(
				value = cash,
				//value = oldArch.price,
				onValueChange = {
					cash = it
					//oldModel.updateState(oldArch.copy(price = it))
				},
				modifier = Modifier
					.background(Color.White)
					.fillMaxWidth()
					.padding(4.dp)
			)

			Button(
				onClick = {
					oldModel.updateState(oldArch.copy(product = produ))
					oldModel.updateState(oldArch.copy(year = years))
					oldModel.updateState(oldArch.copy(month = moons))
					oldModel.updateState(oldArch.copy(day = days))
					oldModel.updateState(oldArch.copy(price = cash))
					writeTheData(oldArch)
				},
				modifier = Modifier.fillMaxWidth()
			) { Text("Add current values") }

		}
	}

	private fun saveTheData(entry : EntryArch) {
		val yearBase = ExpenseBase(this)
		val product : String = entry.product
		val year : String = entry.year
		val month : String = entry.month
		val day : String = entry.day
		val price : String = entry.price
		yearBase.save(product, year, month, day, price)
	}

	private fun writeTheData(entry : OldArch) {
		val yearBase = ExpenseBase(this)
		val product : String = entry.product
		val year : String = entry.year
		val month : String = entry.month
		val day : String = entry.day
		val price : String = entry.price
		yearBase.save(product, year, month, day, price)
	}

    @Composable
    fun SetImage(entryModel : EntryModel = viewModel()) {
        val entryArch by entryModel.uiState.collectAsState()
        val bitMap : Bitmap?
        val locality = LocalContext.current.assets
        bitMap = try {
            val inStream: InputStream? = bitPath?.let { locality.open(it) }
            BitmapFactory.decodeStream(inStream)
        }catch (ie: IOException) {
            ie.printStackTrace()
            null
        }
        bitMap?.let {
            entryModel.updateState(entryArch.copy(image = it))
        }
        entryArch.image?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.padding(4.dp)
            )
        }
    }

    @Composable
    fun ChooseImage(oldModel: OldModel = viewModel()) {
        val oldArch by oldModel.uiState.collectAsState()
        val bitMap : Bitmap?
        val locality = LocalContext.current.assets
        bitMap = try {
            val inStream: InputStream? = bitPath?.let { locality.open(it) }
            BitmapFactory.decodeStream(inStream)
        }catch (ie: IOException) {
            ie.printStackTrace()
            null
        }
        bitMap?.let {
            oldModel.updateState(oldArch.copy(image = it))
        }
        oldArch.image?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.padding(4.dp)
            )
        }
    }



}

