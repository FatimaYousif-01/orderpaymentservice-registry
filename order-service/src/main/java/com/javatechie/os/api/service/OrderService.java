package com.javatechie.os.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.javatechie.os.api.common.Payment;
//import com.javatechie.os.api.common.TransactionRequest;
//import com.javatechie.os.api.common.TransactionResponse;
import com.javatechie.os.api.common.Payment;
import com.javatechie.os.api.common.TransactionRequest;
import com.javatechie.os.api.common.TransactionResponse;
import com.javatechie.os.api.entity.Order;
import com.javatechie.os.api.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
//@RefreshScope


//URL: http://localhost:9191/h2-console/login.do?jsessionid=ed619d44df19a4c52847c9935213504d

public class OrderService {

//    Logger logger= LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository repository;

        @Autowired
//    @Lazy
    private RestTemplate template;

    public TransactionResponse saveOrder(TransactionRequest request)
    {
        String response="";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        Payment paymentResponse = template.postForObject("http://localhost:8086/payment/doPayment", payment, Payment.class);



        response = paymentResponse.getPaymentStatus().equals("success") ? "payment processing successful and order placed" : "there is a failure in payment api , order added to cart";
//
        repository.save(order);
         return new TransactionResponse(order, paymentResponse.getAmount(),paymentResponse.getTransactionId(), response);
    }



//
//    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
//    private String ENDPOINT_URL;

//    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
//        String response = "";
//        Order order = request.getOrder();
//        Payment payment = request.getPayment();
//        payment.setOrderId(order.getId());
//        payment.setAmount(order.getPrice());
//        //rest call
//        logger.info("Order-Service Request : "+new ObjectMapper().writeValueAsString(request));
//        Payment paymentResponse = template.postForObject(ENDPOINT_URL, payment, Payment.class);
//
//        response = paymentResponse.getPaymentStatus().equals("success") ? "payment processing successful and order placed" : "there is a failure in payment api , order added to cart";
//        logger.info("Order Service getting Response from Payment-Service : "+new ObjectMapper().writeValueAsString(response));
//        repository.save(order);
//        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
//    }
}