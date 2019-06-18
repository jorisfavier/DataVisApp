package fr.jorisfavier.a2048datavis.model.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class ProductDTO(
    @SerializedName("product_id")
    val productId: Long,
    @SerializedName("last_sales_date")
    val lastSalesDate: Date,
    @SerializedName("first_sales_date")
    val firstSalesDate: Date)
