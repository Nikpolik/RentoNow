package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.PaymentDto;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;

public interface PaymentService {

    /**
     * Creates a payment for the booking with the
     * provided bookingId.
     *
     * @param bookingId for the id of the booking
     * @return the PaymentDto of the Payment created
     * @throws NotFoundException in case there is no booking
     * with the provided id
     */
    PaymentDto createPayment(Long bookingId) throws NotFoundException;

    /**
     * Finalizes the payment with the provided paymentId
     * and sets the provided paymentType.
     *
     * @param paymentId for the id of the payment to be finalized
     * @param paymentType for the type of the payment
     * @return the PaymentDto of the Payment finalized
     * @throws NotFoundException in case there is no payment with the
     * provided paymentId
     */
    PaymentDto finalizePayment(Long paymentId, String paymentType) throws NotFoundException;
}
