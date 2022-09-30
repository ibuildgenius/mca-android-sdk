package com.covergenius.mca_sdk_android.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ProductDetails(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("certificateable")
    val certificateable: Boolean,
    @SerializedName("claimable")
    val claimable: Boolean,
    @SerializedName("cover_period")
    val coverPeriod: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deleted_at")
    val deletedAt: Any,
    @SerializedName("description")
    val description: String,
    @SerializedName("distributor_commission_percentage")
    val distributorCommissionPercentage: String,
    @SerializedName("document")
    val document: Any,
    @SerializedName("form_fields")
    val formFields: List<FormField>,
    @SerializedName("full_benefits")
    val fullBenefits: List<FullBenefit>,
    @SerializedName("how_it_works")
    val howItWorks: Any,
    @SerializedName("how_to_claim")
    val howToClaim: Any,
    @SerializedName("id")
    val id: String,
    @SerializedName("inspectable")
    val inspectable: Boolean,
    @SerializedName("is_dynamic_pricing")
    val isDynamicPricing: Boolean,
    @SerializedName("is_live")
    val isLive: Boolean,
    @SerializedName("key_benefits")
    val keyBenefits: String,
    @SerializedName("mca_commission_percentage")
    val mcaCommissionPercentage: String,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("name")
    val name: String,
    @SerializedName("prefix")
    val prefix: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_category_id")
    val productCategoryId: String,
    @SerializedName("provider_id")
    val providerId: String,
    @SerializedName("renewable")
    val renewable: Boolean,
    @SerializedName("route_name")
    val routeName: String,
    @SerializedName("updated_at")
    val updatedAt: String
)