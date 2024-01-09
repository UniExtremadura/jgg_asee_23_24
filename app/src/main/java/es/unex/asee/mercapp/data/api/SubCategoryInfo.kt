package es.unex.asee.mercapp.data.api

import com.google.gson.annotations.SerializedName


data class SubCategoryInfo (

  @SerializedName("id"          ) var id         : Int?                = null,
  @SerializedName("name"        ) var name       : String?             = null,
  @SerializedName("order"       ) var order      : Int?                = null,
  @SerializedName("layout"      ) var layout     : Int?                = null,
  @SerializedName("products"    ) var products   : ArrayList<ProductInfo> = arrayListOf(),
  @SerializedName("published"   ) var published  : Boolean?            = null,
  @SerializedName("is_extended" ) var isExtended : Boolean?            = null

)