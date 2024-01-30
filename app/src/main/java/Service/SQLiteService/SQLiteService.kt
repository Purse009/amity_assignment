import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class SQLiteService(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE ${"TODO"} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${"USER_ID"} INEGER," +
                    "${"ID"} INEGER," +
                    "${"TITLE"} TEXT," +
                    "${"COMPLETE"} INEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${"TODO"}")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // เลขเวอร์ชั่น Database ต้องเปลี่ยนทุกครั้งที่มีการแก้ไข Database
        const val DATABASE_VERSION = 1

        // ชื่อ Database
        const val DATABASE_NAME = "todoList.db"
    }
}

