package com.bdd;

import java.time.LocalDateTime;

public class Order {
    LocalDateTime dateTime;
    boolean takeIn;
    int tableNumber;
    String customerName = "";
    String email = "";
    String comments = "";
    double grandTotal;
    String paymentMethod = "";
    int tipping = 0;
    double tipAmount = 0.0;
    boolean requestInvoice = false;
    double totalWithTip = 0.0;
}
