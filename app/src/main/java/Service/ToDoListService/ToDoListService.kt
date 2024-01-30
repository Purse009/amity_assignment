package Service.ToDoListService

import androidx.lifecycle.MutableLiveData
import Interface.ApiService
import Model.TodoListModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ToDoListService {

    companion object {
        val instant = ToDoListService()
    }

    private var todoListValue = MutableLiveData<ArrayList<TodoListModel>?>()

    fun getTodoList() = todoListValue


    fun fetchToDoList() {
        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.getTasks()


        call.enqueue(object : retrofit2.Callback<List<TodoListModel>> {
            override fun onResponse(
                call: Call<List<TodoListModel>>, response: retrofit2.Response<List<TodoListModel>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        todoListValue.value = response.body()?.let { it1 -> ArrayList(it1) }
                    }
                }
            }

            override fun onFailure(call: Call<List<TodoListModel>>, t: Throwable) {
                println("Fail: ${t.message}")
            }
        })
    }
}

