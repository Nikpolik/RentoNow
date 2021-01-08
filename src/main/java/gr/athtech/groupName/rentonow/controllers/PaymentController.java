package gr.athtech.groupName.rentonow.controllers;

import gr.athtech.groupName.rentonow.dtos.PaymentDto;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/{id}/finalize")
    public PaymentDto finalizePayment(@PathVariable Long id) throws NotFoundException {
        return paymentService.finalizePayment(id);
    }
}
