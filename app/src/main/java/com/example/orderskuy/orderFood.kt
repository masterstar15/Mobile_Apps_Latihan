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

class orderFood : AppCompatActivity() {
    var reg_btn: Button? = null
    var nama: EditText? = null
    var makanan: EditText? = null
    var jumlah: EditText? = null
    var Nama: String? = null
    var Makanan: String? = null
    var Jumlah: String? = null
    var builder: AlertDialog.Builder? = null
    var ord_url = "http://192.168.1.4/food.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_food)
        reg_btn = findViewById<View>(R.id.daftar_tbl_daftar) as Button
        nama = findViewById<View>(R.id.namapemesan) as EditText
        makanan = findViewById<View>(R.id.namamakanan) as EditText
        jumlah = findViewById<View>(R.id.jumlahmakanan) as EditText
        builder = AlertDialog.Builder(this@orderFood)
        reg_btn!!.setOnClickListener {
            Nama = nama!!.text.toString()
            Makanan = makanan!!.text.toString()
            Jumlah = jumlah!!.text.toString()
            if (Nama == "" || Makanan == "" || Jumlah == "") {
                builder!!.setTitle("Sepertinya terjadi kesalahan")
                builder!!.setMessage("Dimohon untuk mengisi seluruh form")
                displayAlert("input_error")
            } else {
                if (Makanan == "Nasi Goreng" || Makanan == "Mie Goreng" || Makanan == "Milk Shake" || Makanan == "Air")  {
                    val stringRequest: StringRequest =
                            object : StringRequest(
                                    Method.POST, ord_url,
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
                                    params["Makanan"] = Makanan!!
                                    params["Jumlah"] = Jumlah!!
                                    params["Nama"] = Nama!!
                                    return params
                                }
                            }
                    MySingleton.getInstance(this@orderFood)?.addToRequestque(stringRequest)

                } else {
                    builder!!.setTitle("Sepertinya terjadi kesalahan")
                    builder!!.setMessage("Pesanan tidak ada di menu")
                    displayAlert("input_error")
                }
            }
        }
    }


    fun displayAlert(code: String) {
        builder!!.setPositiveButton(
            "OK"
        ) { dialog, which ->
            if (code == "input_error") {
                makanan!!.setText("")
                nama!!.setText("")
                jumlah!!.setText("")
            } else if (code == "reg_success") {
                finish()
            }
        }
        val alertDialog = builder!!.create()
        alertDialog.show()
    }
}