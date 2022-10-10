package com.covergenius.mca_sdk_android.data.repo

import androidx.annotation.WorkerThread
import com.covergenius.mca_sdk_android.data.cache.ProductDao
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail

class CacheRepo(private val productDao: ProductDao) {


    @WorkerThread
    suspend fun saveProductList(products: List<ProductDetail>) =
        productDao.saveProductList(products)

    suspend fun retrieveProducts(): List<ProductDetail> = productDao.retrieveProducts()

    suspend fun saveSelectedProduct(product: ProductDetail) = productDao.saveSelectedProduct(product)

    suspend fun deleteSelectedProduct(product: ProductDetail) = productDao.deleteSelectedDetails(product)
}