package gr.athtech.groupName.rentonow.controllers;

import gr.athtech.groupName.rentonow.dtos.PaymentDto;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PutMapping(value = "/{id}/finalize")
    public PaymentDto finalizePayment(@PathVariable Long id, @RequestParam String paymentType) throws NotFoundException {
        return paymentService.finalizePayment(id, paymentType);
    }
}
