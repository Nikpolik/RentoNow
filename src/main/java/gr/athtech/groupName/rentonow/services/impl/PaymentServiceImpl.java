package gr.athtech.groupName.rentonow.services.impl;

import gr.athtech.groupName.rentonow.aspect.Logged;
import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.PaymentDto;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.models.Payment;
import gr.athtech.groupName.rentonow.models.PaymentStatus;
import gr.athtech.groupName.rentonow.models.PaymentType;
import gr.athtech.groupName.rentonow.repositories.PaymentRepository;
import gr.athtech.groupName.rentonow.services.BookingService;
import gr.athtech.groupName.rentonow.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Logged
    @Override
    public PaymentDto createPayment(Long bookingId) throws NotFoundException {
        Booking booking = bookingService.getBookingById(bookingId);
        Payment payment = new Payment();
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(booking.getPrice());
        payment.setBookingId(bookingId);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        return PaymentDto.fromPayment(paymentRepository.save(payment));
    }

    @Logged
    @Override
    public PaymentDto finalizePayment(Long paymentId, String paymentType) throws NotFoundException {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()) {
            throw new NotFoundException("There is no Payment with the provided id");
        }
        Payment payment = paymentOptional.get();
        payment.setPaymentType(PaymentType.valueOf(paymentType));
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        return PaymentDto.fromPayment(paymentRepository.save(payment));
    }
}
