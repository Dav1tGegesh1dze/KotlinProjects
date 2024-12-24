package com.example.task10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var categories = mutableListOf<CategoryClass>() // categories list
    private var shippingList = mutableListOf<ProductDataClass>() // products list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add datas
        addToRecycleView()
        addToRecycleView2()

        // RecyclerViews
        setUpHorizontalRecycleView() // horizontal RecyclerView
        setUpVerticalRecycleView() // vertical RecyclerView
    }

    private fun addToRecycleView() {
        // add categories to list
        categories = mutableListOf(
            CategoryClass("All"),
            CategoryClass("Dress"),
            CategoryClass("Shopping"),
            CategoryClass("Travel"),
            CategoryClass("Food"),
            CategoryClass("Sports"),
            CategoryClass("Technology")
        )
    }

    private fun addToRecycleView2() {
        // add products
        shippingList = mutableListOf(
            ProductDataClass(
                productId = 1,
                shoppingCategory = "Dress",
                mainImage = R.drawable.product1,
                heartImage = R.drawable.heart_image,
                productDescription = "Product 1 Description",
                productPrice = "$10.00"
            ),
            ProductDataClass(
                productId = 2,
                shoppingCategory = "Shopping",
                mainImage = R.drawable.product2,
                heartImage = R.drawable.heart_image,
                productDescription = "Product 2 Description",
                productPrice = "$20.00"
            ),
            ProductDataClass(
                productId = 3,
                shoppingCategory = "Dress",
                mainImage = R.drawable.product3,
                heartImage = R.drawable.heart_image,
                productDescription = "Product 3 Description",
                productPrice = "$10.00"
            ),
            ProductDataClass(
                productId = 4,
                shoppingCategory = "Shopping",
                mainImage = R.drawable.product4,
                heartImage = R.drawable.heart_image,
                productDescription = "Product 4 Description",
                productPrice = "$20.00"
            ),
            ProductDataClass(
                productId = 5,
                shoppingCategory = "Shopping",
                mainImage = R.drawable.product5,
                heartImage = R.drawable.heart_image,
                productDescription = "Product 5 Description",
                productPrice = "$20.00"
            ),
            ProductDataClass(
                productId = 6,
                shoppingCategory = "Shopping",
                mainImage = R.drawable.product6,
                heartImage = R.drawable.heart_image,
                productDescription = "Product 6 Description",
                productPrice = "$20.00"
            ),
            ProductDataClass(
                productId = 7,
                shoppingCategory = "Shopping",
                mainImage = R.drawable.product1,
                heartImage = R.drawable.heart_image,
                productDescription = "Product 7 Description",
                productPrice = "$20.00"
            ),
            ProductDataClass(
                productId = 8,
                shoppingCategory = "Shopping",
                mainImage = R.drawable.product2,
                heartImage = R.drawable.heart_image,
                productDescription = "Product 8 Description",
                productPrice = "$20.00"
            )
        )
    }

    private fun setUpHorizontalRecycleView() {
        val horizontalRecycleView = binding.horizontalRecycleView
        horizontalRecycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecycleView.setHasFixedSize(true)
        horizontalRecycleView.adapter = CategoryClassAdapter(categories) { selectedCategory ->
            filterProductsByCategory(selectedCategory)
        }
    }

    private fun filterProductsByCategory(category: String) {
        if (category == "All") {
            updateVerticalRecycleView(shippingList) // Show complete original list
        } else {
            val filteredList = shippingList.filter { it.shoppingCategory == category }
            updateVerticalRecycleView(filteredList)
        }
    }

    private fun updateVerticalRecycleView(filteredProducts: List<ProductDataClass>) {
        val verticalRecycleView = binding.verticalRecycleView
        (verticalRecycleView.adapter as ProductClassAdapter).apply {
            productList = filteredProducts.toMutableList()  // new list
            notifyDataSetChanged()
        }
    }

    private fun setUpVerticalRecycleView() {
        val verticalRecycleView = binding.verticalRecycleView
        verticalRecycleView.layoutManager = GridLayoutManager(this, 2)
        verticalRecycleView.setHasFixedSize(true)
        verticalRecycleView.adapter = ProductClassAdapter(shippingList)
    }

}
