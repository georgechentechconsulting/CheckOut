package com.example.checkout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.paypal.android.sdk.payments.PayPalConfiguration

@Composable
fun PaymentScreen(payPalConfig: PayPalConfiguration, onPay: (String) -> Unit) {
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface), // Background for the screen
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // Center align content
    ) {
        Text(
            text = "Payment",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary, // Primary color for the title
            modifier = Modifier.padding(bottom = 24.dp)
        )

        AmountInput(amount) { newValue ->
            amount = newValue
            errorMessage = null // Reset error when user modifies input
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        PayButton(amount.text) {
            if (validateAmount(amount.text)) {
                onPay(amount.text)
            } else {
                errorMessage = "Please enter a valid amount."
            }
        }
    }
}

@Composable
fun AmountInput(amount: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    TextField(
        value = amount,
        onValueChange = onValueChange,
        label = { Text("Enter Amount") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
    )
}

@Composable
fun PayButton(amountText: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text("Pay Now", style = MaterialTheme.typography.bodyMedium)
    }
}

private fun validateAmount(amount: String): Boolean {
    return amount.toDoubleOrNull()?.let { it > 0 } ?: false
}