package gr.athtech.groupName.rentonow.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime paymentDate;
    private String paymentType;
    private BigDecimal amount;
    @OneToOne
    private Booking booking;

}
