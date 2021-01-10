package gr.athtech.groupName.rentonow.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime paymentDate;
    private PaymentType paymentType;
    private BigDecimal amount;
    private Long bookingId;
    private PaymentStatus paymentStatus;

}
