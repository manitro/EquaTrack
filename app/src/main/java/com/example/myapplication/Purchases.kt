package com.example.myapplication

//import android.content.Intent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme


class Purchases : ComponentActivity() {

    /*
    private lateinit var yearBase : ExpenseBase
	private lateinit var years : ArrayList<Int>
    */
    private lateinit var month: String
    private lateinit var active: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*yearBase = ExpenseBase(this)
       years = yearBase.readYears()*/
        val context = applicationContext
        active = Intent(this, MaxBought::class.java)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(), //try to fill as much space as possible
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {

                        Row(modifier = Modifier.wrapContentSize())
                        {
                            Box(modifier = Modifier
                                .clickable { month = "1" }
                                .padding(4.dp)
                            ) { Text("1") }
                            Box(modifier = Modifier
                                .clickable { month = "2" }
                                .padding(4.dp)
                            ) { Text("2") }
                            Box(modifier = Modifier
                                .clickable { month = "3" }
                                .padding(4.dp)
                            ) { Text("3") }
                            Box(modifier = Modifier
                                .clickable { month = "4" }
                                .padding(4.dp)
                            ) { Text("4") }
                        }

                        Row(modifier = Modifier.wrapContentWidth())
                        {
                            Box(modifier = Modifier
                                .clickable { month = "5" }
                                .padding(4.dp)
                            ) { Text("5") }
                            Box(modifier = Modifier
                                .clickable { month = "6" }
                                .padding(4.dp)
                            ) { Text("6") }
                            Box(modifier = Modifier
                                .clickable { month = "7" }
                                .padding(4.dp)
                            ) { Text("7") }
                            Box(modifier = Modifier
                                .clickable { month = "8" }
                                .padding(4.dp)
                            ) { Text("8") }
                        }

                        Row(modifier = Modifier.wrapContentWidth())
                        {
                            Box(modifier = Modifier
                                .clickable { month = "9" }
                                .padding(4.dp)
                            ) { Text("9") }
                            Box(modifier = Modifier
                                .clickable { month = "10" }
                                .padding(4.dp)
                            ) { Text("10") }
                            Box(modifier = Modifier
                                .clickable { month = "11" }
                                .padding(4.dp)
                            ) { Text("11") }
                            Box(modifier = Modifier
                                .clickable { month = "12" }
                                .padding(4.dp)
                            ) { Text("12") }
                        }
                        Button(
                            onClick = {
                                active.putExtra("theMonth", month)
                                startActivity(active)
                            },
                            modifier = Modifier.padding(8.dp)
                        ) { Text("Open purchases") }
                    }
                }
            }
        }
    }
}