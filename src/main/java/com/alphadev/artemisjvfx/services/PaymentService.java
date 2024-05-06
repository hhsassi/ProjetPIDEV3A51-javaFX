package com.alphadev.artemisjvfx.services;

import com.alphadev.artemisjvfx.models.Actions;

public interface PaymentService {
    void processTransaction(Actions selectedAction, int quantity) throws PaymentException;
}
