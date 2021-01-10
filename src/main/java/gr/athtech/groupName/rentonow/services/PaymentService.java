package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.PaymentDto;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;

public interface PaymentService {
    PaymentDto createPayment(Long bookingId) throws NotFoundException;
    PaymentDto finalizePayment(Long paymentId, String paymentType) throws NotFoundException;
}
