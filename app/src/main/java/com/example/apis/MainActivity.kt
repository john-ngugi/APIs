package com.example.apis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private val stringLink1 = "https://run.mocky.io/v3/9b7b21d4-192f-413f-a5d9-f5b802b2deab"
//    val stringLink = "https://run.mocky.io/v3/7c8e3284-1d07-482b-a41c-65064c3e8b87"
    val infoLink = "https://jsonplaceholder.typicode.com/comments"
    var volleyRequest: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        volleyRequest = Volley.newRequestQueue(this)
//        getString(stringLink)
//        getJsonArray(infoLink)
        getJsonObject(stringLink1)


    }

    fun getString(url: String){
        var stringReq = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    Log.d("response", response.toString())

                }catch (e: JSONException){e.printStackTrace()}

            }, {
                    error: VolleyError? ->
                try {
                    Log.d("error", error.toString())

                }catch (e: JSONException){e.printStackTrace()}

            })
        volleyRequest!!.add(stringReq)
    }

    fun getJsonArray(url: String){
        val jsonArrayReq = JsonArrayRequest(Request.Method.GET, url, null,Response.Listener {
                        response -> try {
                            Log.d("response",response.toString())

                            for (i in 0.. response.length()){
                                var infoObj = response.getJSONObject(i)
                                var email = infoObj.getString("email")
                                Log.d("email", email.toString())
                                var name = infoObj.getString("name")
                                Log.d("name", "$name has email $email")
                            }
                        } catch (e: JSONException){e.printStackTrace()}
        },Response.ErrorListener {

        })

        volleyRequest !!.add(jsonArrayReq)
    }

    fun getJsonObject(url:String){
        val jsonObjReq = JsonObjectRequest(Request.Method.GET,url,null,Response.Listener { response ->

            try {
//                Log.d("response", response.toString())
                var noun = response.getString("noun")
                Log.d("name", noun.toString())
                var data = response.getString("data")
                Log.d("data", data.toString())

                // get array inside the data key
                var dataArray = response.getJSONArray("data")
                for (i in 0..dataArray.length()){
                    var id = dataArray.getJSONObject(i).getString("id")
                    var magnitude = dataArray.getJSONObject(i).getString("magnitude")
                    Log.d("message","earthquake of id $id has magnitude of $magnitude")
                }

            } catch (e:JSONException){e.printStackTrace()}

        },
            Response.ErrorListener { error->
                Log.d("error",error.toString())
            }
        )

        volleyRequest !!.add(jsonObjReq)
    }
}