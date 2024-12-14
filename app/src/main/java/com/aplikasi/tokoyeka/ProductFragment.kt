import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplikasi.tokoyeka.DetailFragment
import com.aplikasi.tokoyeka.databinding.FragmentProductBinding
import com.aplikasi.tokoyeka.model.Product
import com.aplikasi.tokoyeka.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var productApiService: ProductApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)

        // Ambil nama kategori dari argument yang dikirimkan
        val categoryName = arguments?.getString("category") ?: ""

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Panggil API dan tampilkan produk
        productApiService = ApiClient.apiService
        fetchProducts(categoryName)

        return binding.root
    }

    private fun fetchProducts(category: String) {
        productApiService.getProductsByCategory(category).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val products = response.body()
                    products?.let {
                        // Kirim data produk dan lambda untuk klik
                        binding.recyclerView.adapter = ProductAdapter(it) { product ->
                            navigateToProductDetail(product)
                        }
                    }
                } else {
                    Log.e("ProductFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("ProductFragment", "Failed to fetch products", t)
            }
        })
    }

    private fun navigateToProductDetail(product: Product) {
        // Membuat Intent untuk berpindah ke ProductDetailActivity
        val intent = Intent(requireContext(), DetailFragment::class.java)

        // Menyertakan data produk (misalnya ID produk) ke dalam Intent
        intent.putExtra("productId", product.id)

        // Memulai activity untuk melihat detail produk
        startActivity(intent)
    }
}