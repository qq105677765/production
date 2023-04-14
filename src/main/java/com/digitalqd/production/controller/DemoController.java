package com.digitalqd.production.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalqd.production.service.EnergyMetricalService;

import lombok.Data;

@RestController
@RequestMapping(path = "/demo")
@CrossOrigin
public class DemoController {

    @Autowired
    private EnergyMetricalService energyMetricalService;

    @Data
    static class Electric {
        private Timestamp timestamp;
        private Double value;
    }

    @GetMapping(path = "/find")
    public String findItem() {
        // energyMetricalService.saveEnergyMetricalDataToDb("自来水", "冲压车间", "1677600000", "1677686400");
        return "seccess";
    }

}
