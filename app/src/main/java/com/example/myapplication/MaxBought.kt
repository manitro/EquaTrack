package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme


class MaxBought : ComponentActivity() {

    private lateinit var yearBase: ExpenseBase
    private lateinit var entries: ArrayList<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        yearBase = com.example.myapplication.ExpenseBase(this)
        //val theYear = this.intent.getExtraValue("theYear")
        val theMonth = this.intent.getStringExtra("theMonth")!!
        entries = yearBase.read(theMonth)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(), //try to fill as much space as possible
                    color = MaterialTheme.colorScheme.background
                ) {
                    Rower(entries)
                }
            }
        }
    }
    @Composable
    fun Rower(entry : ArrayList<Array<String>>) {
    	val scroller = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scroller)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Product",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 3.dp)
                )
                Text(
                    text = "Year",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 3.dp)
                )
                Text(
                    text = "Month",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 3.dp)
                )
                Text(
                    text = "Day",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 3.dp)
                )
                Text(
                    text = "Price",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 3.dp)
                )
            }
            entry.forEach{ row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                        .border(1.dp, Color.Cyan, shape = RectangleShape)
                        /*.clickable { maybe use selectable instead
                            yearBase.deleteData(row)
                        }*/
                ) {
                    row.forEach { item ->
                        Text(
                            text = item,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 3.dp)
                        )
                    }
                }
            }
        }
    }
}
