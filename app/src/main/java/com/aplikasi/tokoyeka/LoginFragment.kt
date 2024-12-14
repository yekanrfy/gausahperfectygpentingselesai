package com.aplikasi.tokoyeka

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class LoginFragment : Fragment() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    private val predefinedUsername = "yeka"
    private val predefinedPassword = "12345678"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        etUsername = view.findViewById(R.id.etUsername)
        etPassword = view.findViewById(R.id.etPassword)
        btnLogin = view.findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username == predefinedUsername && password == predefinedPassword) {
                // Simpan status login ke SharedPreferences
                val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                // Tampilkan pesan sukses
                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                // Navigasi ke CategoryFragment
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, CategoryFragment()) // Ganti fragment yang aktif
                transaction.addToBackStack(null) // Menambahkan transaksi ke back stack
                transaction.commit()

            } else {
                // Pesan gagal
                Toast.makeText(context, "Username atau Password salah", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
