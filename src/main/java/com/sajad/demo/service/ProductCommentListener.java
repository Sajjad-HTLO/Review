//package com.sajad.demo.service;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
///**
// * A simple kafka listener for new comments to notify the default ADMIN user to verify or reject it.
// */
//@Component
//public class ProductCommentListener {
//
//    @KafkaListener(topics = "-comments", groupId = "myGroup")
//    public void listenOnNewComments(String comment) {
//        System.out.println("Received new comment: " + comment);
//    }
//}
