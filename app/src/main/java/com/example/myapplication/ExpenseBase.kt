package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class ExpenseBase(context: Context) : SQLiteOpenHelper(context, "loader", null, 1){
    companion object{
        const val TABLE1 = "Purchases"
        const val TABLE2 = "years"
        const val COL1 = "Product"
        const val COL2 = "Year"
        const val COL3 = "Month"
        const val COL4 = "Day"
        const val COL5 = "Price"
         // COL6 = "Currency" remember to add a popup for currency
    }
    private var contexT : Context

    init {contexT = context}
    
    override fun onCreate(db : SQLiteDatabase?) { //if TABLE1 doesn't exist already, build it
       db?.execSQL("CREATE TABLE $TABLE1 ( $COL1 TEXT, $COL2 TEXT, $COL3 TEXT, $COL4 TEXT, $COL5 TEXT ) ")
       db?.execSQL("CREATE TABLE $TABLE2 ( $COL1 INTEGER PRIMARY KEY ) ")
    }// Note: execSQL normally isn't used for selection
    
    override fun onUpgrade(db : SQLiteDatabase?, oldVersion: Int, newVersion: Int) { //any change to TABLE1 will be applied here
       db!!.execSQL("DROP TABLE IF EXISTS $TABLE1 ")
       db!!.execSQL("DROP TABLE IF EXISTS $TABLE2 ")
       onCreate(db)
    }
    
    
    fun saveYear(year : Int){
    	val db = this.writableDatabase
        val quer = "INSERT INTO TABLE $TABLE2 ($COL1 $year)"
        db.execSQL(quer)
        db.close()
    }
    
    //in addition to the database onMethods
    fun save(product : String, year : String, month : String, day : String, price : String){
        val db = this.writableDatabase
        val values = ContentValues()
        
        values.put(COL1, product)
        values.put(COL2, year)
        values.put(COL3, month)
        values.put(COL4, day)
        values.put(COL5, price)
        db.insert(TABLE1, null, values)
        db.close()
        Toast.makeText(contexT, "saved values: $product, $year, $month, $day, $price", Toast.LENGTH_LONG).show()
    }
    
    fun saveAll(entries : Array<String>){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL1, entries[0])
        values.put(COL2, entries[1])
        values.put(COL3, entries[2])
        values.put(COL4, entries[3])
        values.put(COL5, entries[4])
        Toast.makeText(contexT, "saved values: $entries[0], $entries[1], $entries[2], $entries[3], $entries[4]", Toast.LENGTH_LONG).show()

        db.insert(TABLE1, null, values)
        db.close()
        }
//don't forget that the most popular tool for inserting values into a database is the 'Values' class(?)
    
    
    
    
    //we need to see what years we have in the database first, after which we can select the year
    fun readYears() : ArrayList<Int> {
    	var i = 0
    	var current = 0
    	val years = ArrayList<Int>()
        val db = this.readableDatabase
        val cursor : Cursor?
        val query = "SELECT * FROM $TABLE2"
        //val cursor = db.execute("SELECT * FROM $TABLE1 WHERE $Col1 EQUAL $year")
        try{
        cursor = db.rawQuery(query, null)
       }catch (e: SQLiteException) { //this exception makes tops non nullable
        db.execSQL(query)
        return years
       }
        if(cursor.moveToFirst()){
        years.add(cursor.getInt(0))
        while(cursor.moveToNext()){// each move goes to a new row, maybe an array.
            current = cursor?.getInt(0)!!
            if(years[i]!=current){
            years.add(current)
            i+=1
            }
            //handle each element and write...
        	}
        }
        cursor.close()
        db.close()
        return years
    }
    
    @SuppressLint("SuspiciousIndentation")
    fun read(month : String) : ArrayList<Array<String>>{
    	val db = this.readableDatabase
    	val entries = ArrayList<Array<String>>()
        Toast.makeText(contexT, "saved values: $month", Toast.LENGTH_LONG).show()
    	
    	val cursor : Cursor?
       val selQuer = "SELECT * FROM $TABLE1 WHERE $COL3 = ?"
       try{
        cursor = db.rawQuery(selQuer, arrayOf(month))
       }catch (e: SQLiteException) { //this exception makes tops non nullable
        db.execSQL(selQuer)
        return entries
       }
    	if(cursor.moveToFirst()){
    		//it is extremely important that you add the initial value first before implementing the moveToNext() method.
    		entries.add(arrayOf(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)))
    		while(cursor.moveToNext()){
                val entry : Array<String> = arrayOf(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4))
    			entries.add(entry)
    		}
    	}
    	cursor.close()
    	db.close()
    	return entries
    }

    //to delete the entry
    fun deleteData(entries : Array<String>){
        val db = this.writableDatabase
        val selection = "$COL1 LIKE ? AND $COL2 LIKE ? AND $COL3 LIKE ? AND $COL4 LIKE ? AND $COL5 LIKE ?"
        db.delete(TABLE1, selection, entries)
    }
    
}
