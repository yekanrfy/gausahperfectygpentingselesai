import com.aplikasi.tokoyeka.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Mengambil daftar produk berdasarkan kategori
    @GET("hL3IP/products")
    fun getProductsByCategory(
        @Query("category") category: String
    ): Call<List<Product>>

    // Mengambil produk berdasarkan ID
    @GET("hL3IP/products/{id}")
    fun getProductById(
        @Path("id") productId: Int
    ): Call<Product> // Mengambil produk berdasarkan ID
}
