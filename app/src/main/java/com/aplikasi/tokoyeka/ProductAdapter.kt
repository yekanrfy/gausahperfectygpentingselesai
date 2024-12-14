import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.tokoyeka.R
import com.aplikasi.tokoyeka.databinding.ItemProductBinding
import com.aplikasi.tokoyeka.model.Product

class ProductAdapter(
    private val productList: List<Product>,
    private val onItemClick: (Product) -> Unit // Lambda untuk menangani klik
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // ViewHolder untuk setiap item produk
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val productStock: TextView = itemView.findViewById(R.id.productStock)
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)

        // Mengikat data produk ke UI
        fun bind(product: Product) {
            productName.text = product.name
            productPrice.text = product.price.toString()
            productStock.text = product.stock.toString()
            // Misalnya, kita set gambar produk (dengan asumsi ada URL gambar)
            // Glide.with(itemView.context).load(product.imageUrl).into(productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)

        // Set listener untuk item klik
        holder.itemView.setOnClickListener {
            onItemClick(product) // Memanggil lambda yang diberikan dari fragment
        }
    }

    override fun getItemCount(): Int = productList.size
}
