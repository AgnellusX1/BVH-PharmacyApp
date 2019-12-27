package com.agnellusx1.pharmacy.Adapters;

public class OrderSample {
    public String NAME;
    public String LOCATION;
    public String MIN;

    public String getLocation() {
        return LOCATION;
    }

    public String getName() {
        return NAME;
    }

    public String getBillNumber() {
        return MIN;
    }

    public OrderSample(String LOCATION, String NAME, String MIN) {
        this.LOCATION = LOCATION;
        this.NAME = NAME;
        this.MIN = MIN;
    }
}
