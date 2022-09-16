package com.covergenius.mca_sdk_android.data.model

data class Credential(
    val productID: String,
    val reference: String,
    val email: String,
    val form: Form,
    val publicKey: String,
    val transactionType: TransactionType,
    val paymentOption: PaymentOption
)

class Form
class TransactionType
class PaymentOption

