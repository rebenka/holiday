package com.rebenka.holiday.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HumanResourceServiceStub implements HumanResourceService {

    public void bookHoliday(Date startDate, Date endDate, String name) {
        System.out.println("############# Booking holiday for [" + startDate + "-" + endDate + "] for [" + name + "]  ############# ");
    }
}
