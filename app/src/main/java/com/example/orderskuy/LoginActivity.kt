package com.example.orderskuy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class LoginActivity : AppCompatActivity() {
    var btnLogin: Button? = null
    var btnRegist: Button? = null
    var UserName: EditText? = null
    var Password: EditText? = null
    var username: String? = null
    var password: String? = null
    var login_url = "http://192.168.1.4/login.php"
    var builder: AlertDialog.Builder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnRegist = findViewById<View>(R.id.daftar_tbl_daftar) as Button
        btnRegist!!.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }
        builder = AlertDialog.Builder(this@LoginActivity)
        btnLogin = findViewById<View>(R.id.login_tbl_login) as Button
        UserName = findViewById<View>(R.id.login_username) as EditText
        Password = findViewById<View>(R.id.login_password) as EditText
        btnLogin!!.setOnClickListener {
            username = UserName!!.text.toString()
            password = Password!!.text.toString()
            if (username == "" || password == "") {
                builder!!.setTitle("Sepertinya terjadi kesalahan")
                displayAlert("Masukkan username dan password!")
            } else {
                val stringRequest: StringRequest =
                    object : StringRequest(
                        Method.POST, login_url,
                        Response.Listener { response ->
                            try {
                                val jsonArray = JSONArray(response)
                                val jsonObject = jsonArray.getJSONObject(0)
                                val code = jsonObject.getString("code")
                                if (code == "login_failed") {
                                    builder!!.setTitle("Login Error")
                                    displayAlert(jsonObject.getString("message"))
                                } else {
                                    val intent =
                                        Intent(this@LoginActivity, LoginSuccess::class.java)
                                    val bundle = Bundle()
                                    bundle.putString("name", jsonObject.getString("name"))
                                    bundle.putString("email", jsonObject.getString("email"))
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }, Response.ErrorListener { error ->
                            Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_LONG)
                                .show()
                            error.printStackTrace()
                        }) {
                        @Throws(AuthFailureError::class)
                        override fun getParams(): Map<String, String> {
                            val params: MutableMap<String, String> =
                                HashMap()
                            params["user_name"] = username!!
                            params["password"] = password!!
                            return params
                        }
                    }
                MySingleton.getInstance(this@LoginActivity)!!.addToRequestque(stringRequest)
            }
        }
    }

    fun displayAlert(message: String?) {
        builder!!.setMessage(message)
        builder!!.setPositiveButton(
            "OK"
        ) { dialog, which ->
            UserName!!.setText("")
            Password!!.setText("")
        }
        val alertDialog = builder!!.create()
        alertDialog.show()
    }
}