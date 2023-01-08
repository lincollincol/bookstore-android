package com.linc.payments

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.stripe.android.PaymentConfiguration
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.paymentsheet.PaymentSheetContract
import com.stripe.android.paymentsheet.PaymentSheetResult

@Composable
fun rememberPaymentLauncher(
    callback: PaymentLauncher.PaymentResultCallback
): PaymentLauncher {
    val config = PaymentConfiguration.getInstance(LocalContext.current)
    return PaymentLauncher.rememberLauncher(
        publishableKey = config.publishableKey,
        stripeAccountId = config.stripeAccountId,
        callback = callback
    )
}

fun ManagedActivityResultLauncher<PaymentSheetContract.Args, PaymentSheetResult>.launch(
    clientSecret: String
) = launch(PaymentSheetContract.Args.createPaymentIntentArgs(clientSecret))