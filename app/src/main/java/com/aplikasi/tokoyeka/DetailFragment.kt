package com.aplikasi.tokoyeka

import ApiService
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aplikasi.tokoyeka.model.Product
import com.aplikasi.tokoyeka.network.ApiClient
import com.bumptech.glide.Glide
import okhttp3.Response

class DetailFragment : Fragment() {

    private var productId: Int? = null
    private lateinit var productImageView: ImageView
    private lateinit var productNameTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var productStockTextView: TextView
    private lateinit var productDescriptionTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_detail, container, false)

        // Menyambungkan variabel dengan view
        productImageView = rootView.findViewById(R.id.gambar)
        productNameTextView = rootView.findViewById(R.id.detailProductName)
        productPriceTextView = rootView.findViewById(R.id.detailProductPrice)
        productStockTextView = rootView.findViewById(R.id.detailProductStock)
        productDescriptionTextView = rootView.findViewById(R.id.detailProductDescription)

        // Mengambil ID produk dari argument (produkId yang dikirimkan)
        productId = arguments?.getInt("productId")

        // Set up API Service
        val productApiService = ApiClient.apiService

        // Ambil detail produk berdasarkan ID
        productId?.let {
            fetchProductDetails(it, productApiService)
        }

        return rootView
    }

    private fun fetchProductDetails(productId: Int, productApiService: ApiService) {
        productApiService.getProductById(productId).enqueue(object :
            retrofit2.Callback<Product> {
            override fun onResponse(call: retrofit2.Call<Product>, response: retrofit2.Response<Product>) {
                if (response.isSuccessful) {
                    val product = response.body()
                    product?.let {
                        // Menampilkan gambar produk
                        Glide.with(requireContext()).load(it.imageUrl).into(productImageView)

                        // Menampilkan data produk di UI
                        productNameTextView.text = it.name
                        productPriceTextView.text = "Harga: ${it.price}"
                        productStockTextView.text = "Stok: ${it.stock}"
                        productDescriptionTextView.text = it.description
                    }
                } else {
                    Log.e("ProductDetailFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<Product>, t: Throwable) {
                Log.e("ProductDetailFragment", "Failed to fetch product details", t)
            }
        })
    }
}
