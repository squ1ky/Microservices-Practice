package com.katharsys.customer;

import com.katharsys.clients.fraud.FraudCheckResponse;
import com.katharsys.clients.fraud.FraudClient;
import com.katharsys.clients.notification.NotificationClient;
import com.katharsys.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo : check if email valid
        //todo : check if email not taken
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }

        // todo: make it async, i.e add to Queue
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        "Katharsys",
                        String.format("Welcome, %s", customer.getFirstName()),
                        LocalDateTime.now()
                )
        );
    }
}
