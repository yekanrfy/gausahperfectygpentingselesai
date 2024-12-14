package com.aplikasi.tokoyeka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.tokoyeka.databinding.FragmentCategoryBinding
import com.aplikasi.tokoyeka.model.Category
import com.aplikasi.tokoyeka.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView // Mendeklarasikan RecyclerView untuk menampilkan daftar kategori
    private lateinit var categoryAdapter: CategoryAdapter // Adapter untuk RecyclerView
    private lateinit var categories: List<Category> // Menyimpan data kategori

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menginflate layout fragment_category.xml
        val binding = FragmentCategoryBinding.inflate(inflater, container, false)

        // Inisialisasi RecyclerView dan mengatur layout manager
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context) // Menampilkan daftar secara vertikal

        // Mengambil data kategori dari API
        fetchCategories()

        return binding.root // Mengembalikan root view untuk fragment ini
    }

    private fun fetchCategories() {
        // Panggilan API untuk mengambil daftar kategori
        ApiClient.apiService.getCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    // Jika respon berhasil, ambil data kategori dan update adapter
                    categories = response.body() ?: emptyList() // Menyimpan kategori ke dalam list
                    categoryAdapter = CategoryAdapter(categories) { category ->
                        onCategorySelected(category) // Menangani saat kategori dipilih
                    }
                    recyclerView.adapter = categoryAdapter // Mengatur adapter pada RecyclerView
                } else {
                    // Jika terjadi error pada response, tampilkan pesan kesalahan
                    Toast.makeText(context, "Error fetching categories", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                // Menangani jika panggilan API gagal (misalnya jaringan error)
                Toast.makeText(context, "Failed to load categories", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onCategorySelected(category: Category) {
        // Fungsi untuk menangani kategori yang dipilih
        // Navigasi ke ProductFragment dan mengirimkan ID kategori
        val bundle = Bundle()
        bundle.putInt("categoryId", category.id)
        findNavController().navigate(R.id.productFragment, bundle)
    }
}
