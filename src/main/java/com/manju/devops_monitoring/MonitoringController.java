package com.manju.devops_monitoring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitoringController {

    @GetMapping("/")
    public String home() {
        return "DevOps Monitoring & Observability Demo";
    }
}