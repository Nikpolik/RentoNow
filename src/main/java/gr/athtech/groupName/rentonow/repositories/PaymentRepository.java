package gr.athtech.groupName.rentonow.repositories;

import gr.athtech.groupName.rentonow.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
