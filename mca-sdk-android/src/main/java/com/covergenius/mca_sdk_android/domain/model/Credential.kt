package com.covergenius.mca_sdk_android.domain.model

data class Credential(
    val productID: String,
    val reference: String,
    val email: String,
    val form: Form,
    val publicKey: String,
    val transactionType: TransactionType,
    val paymentOption: PaymentOption
)

data class Form(val email: String, val name: String, val phone: String)
enum class TransactionType {Purchase, Inspection}
enum class PaymentOption {Wallet, Gateway}