package com.example.checkout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.checkout.ui.PaymentScreen
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import com.paypal.android.sdk.payments.PaymentConfirmation


class MainActivity : ComponentActivity() {
    private lateinit var payPalConfig: PayPalConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        payPalConfig = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AQ8b9b5AtxZkKHA4BLyRb2KKpbdV8o8wMlhnS12WZMClLdVPNxHayzT-af9tqRgiAyG1zfAZpwJGPQ1_") // Replace with your actual client ID
            .merchantName("Name")

        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig)
        startService(intent)

        setContent {
            PaymentScreen(payPalConfig) { amount ->
                startPayPalPayment(amount)
            }
        }
    }

    private fun startPayPalPayment(amount: String) {
        val payment = PayPalPayment(
            amount.toBigDecimal(),
            "USD",
            "Payment for MyPay",
            PayPalPayment.PAYMENT_INTENT_SALE
        )

        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)

        startActivityForResult(intent, 124)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 124) {
            if (resultCode == RESULT_OK) {
                val paymentConfirmation = data?.getParcelableExtra<PaymentConfirmation>("payment_confirmation")
                paymentConfirmation?.let {
                    val paymentDetails = it.toJSONObject().toString(4)
                    println(paymentDetails)
                }
            }
        }
    }
}
