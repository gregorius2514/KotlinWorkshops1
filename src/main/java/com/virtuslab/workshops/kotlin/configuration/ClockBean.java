package com.virtuslab.workshops.kotlin.configuration;

import java.time.Clock;
import org.springframework.context.annotation.Bean;

public class ClockBean {

    @Bean
    public Clock getClock() {
        return Clock.systemUTC();
    }
}
