package fit.se.tourbe.features.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.se.tourbe.features.payment.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrderId(String orderId);

    Payment findByTransactionId(String transactionId);
}