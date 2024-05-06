package com.alphadev.artemisjvfx.services;

import com.alphadev.artemisjvfx.models.Actions;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import com.stripe.param.ChargeCreateParams;
import javafx.scene.control.Alert;
import tray.notification.TrayNotification;

public class StripepaymentService implements PaymentService {

    @Override
    public void processTransaction(Actions selectedAction, int quantity) throws PaymentException {
        try {
            // Convert the price to cents
            long amountInCents = (long) (selectedAction.getValeurAchat() * quantity * 100);

            // Create a charge
            ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency("usd")
                    .setDescription("Payment for " + quantity + " " + selectedAction.getSymbol())
                    .setSource("tok_visa") // Dummy test card token
                    .build();
            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey("sk_test_51Orz9aAUhx2t9ef6CyT9VYPLdripgWppwecP4CVWp2j2bJUDTZR6UyPmoR1jKAqoeXKoN5ojXmiKb89Ofs8sCIme00bwgWezti")
                    .build();
            Charge charge = Charge.create(chargeParams, requestOptions);

            if (charge.getStatus().equals("succeeded")) {
                // Payment successful
                showNotification("Payment Successful", "Your payment was successful!");
            } else {
                // Payment failed
                throw new PaymentException("Payment failed: " + charge.getStatus());
            }
        } catch (StripeException e) {
            throw new PaymentException("Stripe error: " + e.getMessage());
        }
    }

    private void showNotification(String title, String message) {
        TrayNotification notification = new TrayNotification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.showAndWait();
    }
}
