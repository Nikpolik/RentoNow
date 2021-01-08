package gr.athtech.groupName.rentonow.dtos;

import gr.athtech.groupName.rentonow.models.Payment;
import gr.athtech.groupName.rentonow.models.PaymentStatus;
import gr.athtech.groupName.rentonow.models.PaymentType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private LocalDateTime paymentDate;
    private PaymentType paymentType;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;

    public static Payment toPayment(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setPaymentType(paymentDto.getPaymentType());
        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentStatus(paymentDto.getPaymentStatus());
        return payment;
    }

    public static PaymentDto fromPayment(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentDate(payment.getPaymentDate());
        paymentDto.setPaymentType(payment.getPaymentType());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setPaymentStatus(payment.getPaymentStatus());
        return paymentDto;
    }

}

