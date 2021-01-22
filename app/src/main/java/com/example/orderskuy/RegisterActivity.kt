package com.example.orderskuy

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class RegisterActivity : AppCompatActivity() {
    var reg_btn: Button? = null
    var Name: EditText? = null
    var Email: EditText? = null
    var UserName: EditText? = null
    var Password: EditText? = null
    var ConPassword: EditText? = null
    var name: String? = null
    var email: String? = null
    var username: String? = null
    var password: String? = null
    var conpassword: String? = null
    var builder: AlertDialog.Builder? = null
    var reg_url = "http://192.168.1.4/register.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        reg_btn = findViewById<View>(R.id.daftar_tbl_daftar) as Button
        Name = findViewById<View>(R.id.regist_name) as EditText
        Email = findViewById<View>(R.id.regist_email) as EditText
        UserName = findViewById<View>(R.id.regist_username) as EditText
        Password = findViewById<View>(R.id.regist_password) as EditText
        ConPassword = findViewById<View>(R.id.regist_conpassword) as EditText
        builder = AlertDialog.Builder(this@RegisterActivity)
        reg_btn!!.setOnClickListener {
            name = Name!!.text.toString()
            email = Email!!.text.toString()
            username = UserName!!.text.toString()
            password = Password!!.text.toString()
            conpassword = ConPassword!!.text.toString()
            if (name == "" || email == "" || username == "" || password == "" || conpassword == "") {
                builder!!.setTitle("Sepertinya terjadi kesalahan")
                builder!!.setMessage("Dimohon untuk mengisi seluruh form")
                displayAlert("input_error")
            } else {
                if (password != conpassword) {
                    builder!!.setTitle("Sepertinya terjadi kesalahan")
                    builder!!.setMessage("Password kamu tidak sesuai!")
                    displayAlert("input_error")
                } else {
                    val stringRequest: StringRequest =
                        object : StringRequest(
                            Method.POST, reg_url,
                            Response.Listener { response ->
                                try {
                                    val jsonArray = JSONArray(response)
                                    val jsonObject = jsonArray.getJSONObject(0)
                                    val code = jsonObject.getString("code")
                                    val message =
                                        jsonObject.getString("message")
                                    builder!!.setTitle("Server Response..")
                                    builder!!.setMessage(message)
                                    displayAlert(code)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }, Response.ErrorListener { }) {
                            @Throws(AuthFailureError::class)
                            override fun getParams(): Map<String, String> {
                                val params: MutableMap<String, String> =
                                    HashMap()
                                params["name"] = name!!
                                params["email"] = email!!
                                params["user_name"] = username!!
                                params["password"] = password!!
                                return params
                            }
                        }
                    MySingleton.getInstance(this@RegisterActivity)?.addToRequestque(stringRequest)
                }
            }
        }
    }

    fun displayAlert(code: String) {
        builder!!.setPositiveButton(
            "OK"
        ) { dialog, which ->
            if (code == "input_error") {
                Password!!.setText("")
                ConPassword!!.setText("")
            } else if (code == "reg_success") {
                finish()
            } else if (code == "reg_failed") {
                Name!!.setText("")
                Email!!.setText("")
                UserName!!.setText("")
                Password!!.setText("")
                ConPassword!!.setText("")
            }
        }
        val alertDialog = builder!!.create()
        alertDialog.show()
    }
}