package gr.athtech.groupName.rentonow.services.impl;

import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.PaymentDto;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.models.Payment;
import gr.athtech.groupName.rentonow.models.PaymentStatus;
import gr.athtech.groupName.rentonow.repositories.PaymentRepository;
import gr.athtech.groupName.rentonow.services.BookingService;
import gr.athtech.groupName.rentonow.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto, Long bookingId) throws NotFoundException {
        Payment payment = PaymentDto.toPayment(paymentDto);
        //To make sure there is a Booking with this id
        bookingService.getBookingById(bookingId);
        payment.setBookingId(bookingId);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        return PaymentDto.fromPayment(paymentRepository.save(payment));
    }

    @Override
    public PaymentDto finalizePayment(Long paymentId) throws NotFoundException {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()) {
            throw new NotFoundException("There is no Payment with the provided id");
        }
        Payment payment = paymentOptional.get();
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        return PaymentDto.fromPayment(paymentRepository.save(payment));
    }
}
