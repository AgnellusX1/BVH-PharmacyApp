package com.agnellusx1.pharmacy.Adapters;

public class OrderSample {
    public String location;
    public String name;
    public String billNumber;

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public OrderSample(String location, String name, String billNumber) {
        this.location = location;
        this.name = name;
        this.billNumber = billNumber;


    }
}
