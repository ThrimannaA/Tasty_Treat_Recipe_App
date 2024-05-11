package com.example.tasty_treat_recipie_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CookeryDatabaseHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME= "cookeryapp.db"
        private const val DATABASE_VERSION= 1
        private const val TABLE_NAME= "allcookeryitems"
        private const val COLOUMN_ID= "id"
        private const val COLOUMN_TITLE= "title"
        private const val COLOUMN_CONTENT= "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery="CREATE TABLE $TABLE_NAME($COLOUMN_ID INTEGER PRIMARY KEY,$COLOUMN_TITLE TEXT,$COLOUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertCookery(cookery: Cookery){
        val db=writableDatabase
        val values= ContentValues().apply {
            put(COLOUMN_TITLE,cookery.title)
            put(COLOUMN_CONTENT,cookery.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllCookery():List<Cookery>{
        val cookeryList= mutableListOf<Cookery>()
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME"
        val cursor=db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLOUMN_ID))
            val title=cursor.getString(cursor.getColumnIndexOrThrow(COLOUMN_TITLE))
            val content=cursor.getString(cursor.getColumnIndexOrThrow(COLOUMN_CONTENT))

            val cookery=Cookery(id,title,content)
            cookeryList.add(cookery)
        }
        cursor.close()
        db.close()
        return cookeryList
    }

    fun updateCookery(cookery: Cookery){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLOUMN_TITLE,cookery.title)
            put(COLOUMN_CONTENT,cookery.content)
        }
        val whereClause="$COLOUMN_ID=?"
        val whereArgs= arrayOf(cookery.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    fun getCookeryByID(cookeryId:Int):Cookery{
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $COLOUMN_ID=$cookeryId"
        val cursor=db.rawQuery(query,null)
        cursor.moveToFirst()

        val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLOUMN_ID))
        val title=cursor.getString(cursor.getColumnIndexOrThrow(COLOUMN_TITLE))
        val content=cursor.getString(cursor.getColumnIndexOrThrow(COLOUMN_CONTENT))

        cursor.close()
        db.close()
        return Cookery(id,title,content)
    }

    fun deleteCookery(cookeryId: Int){
        val db=writableDatabase
        val whereClause="$COLOUMN_ID=?"
        val whereArgs= arrayOf(cookeryId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}