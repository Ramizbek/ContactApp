package ramizbek.aliyev.contactapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ramizbek.aliyev.contactapp.models.User
import ramizbek.aliyev.contactapp.utils.Constant.DB_NAME
import ramizbek.aliyev.contactapp.utils.Constant.DB_VERSION
import ramizbek.aliyev.contactapp.utils.Constant.ID
import ramizbek.aliyev.contactapp.utils.Constant.NAME
import ramizbek.aliyev.contactapp.utils.Constant.NUMBER
import ramizbek.aliyev.contactapp.utils.Constant.TABLE_NAME

class MyDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    MyDBService {
    override fun onCreate(p0: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $TABLE_NAME($ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, $NAME TEXT NOT NULL, $NUMBER INTEGER NOT NULL)"

        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun createUser(user: User) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, user.name)
        contentValues.put(NUMBER, user.number)
        database.insert(TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun readUser(): List<User> {
        val list = ArrayList<User>()
        val query = "select * from $TABLE_NAME"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {

                val user = User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                )
                list.add(user)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun updateUser(user: User): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, user.id)
        contentValues.put(NAME, user.name)
        contentValues.put(NUMBER, user.number)

        return database.update(TABLE_NAME, contentValues, "$ID = ?", arrayOf(user.id.toString()))
    }

    override fun deleteUser(user: User) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$ID = ?", arrayOf(user.id.toString()))
        database.close()

    }
}