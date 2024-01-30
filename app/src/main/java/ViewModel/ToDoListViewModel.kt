import Model.TodoListModel
import Service.ToDoListService.ToDoListService
import android.annotation.SuppressLint
import android.content.ContentValues
import android.provider.BaseColumns
import androidx.lifecycle.ViewModel


class ToDoListViewModel : ViewModel() {
    private var toDoListService = ToDoListService.instant

    var todoList = toDoListService.getTodoList()

    fun getToDoList() {
        toDoListService.fetchToDoList()
    }

    fun saveOfflineValue(todoListModels: ArrayList<TodoListModel>, dbHelper: SQLiteService) {
        val db = dbHelper.writableDatabase

        todoListModels.forEach { value ->
            val values = ContentValues().apply {
                put("USER_ID", value.userId)
                put("ID", value.id)
                put("TITLE", value.title)
                put("COMPLETE", value.completed)
            }

            db?.insert("TODO", null, values)
        }
    }

    @SuppressLint("Recycle")
    fun readOfflineValue(dbHelper: SQLiteService) {
        val db = dbHelper.readableDatabase
        val test : ArrayList<TodoListModel> = ArrayList()

        val cursor = db.query(
            "TODO",
            null,
            null,
            null,
            null,
            null,
            null
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val userId = cursor.getInt(cursor.getColumnIndexOrThrow("USER_ID"))
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"))
                val complete =
                    cursor.getInt(cursor.getColumnIndexOrThrow("COMPLETE")) == 1


                val offlineValue = TodoListModel(userId, id, title, complete)
                test.add(offlineValue)
                todoList.value = test
            }
            cursor.close()
        }

    }
}