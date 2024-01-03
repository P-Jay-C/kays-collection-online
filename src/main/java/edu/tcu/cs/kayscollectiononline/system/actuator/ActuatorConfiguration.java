package edu.tcu.cs.kayscollectiononline.system.actuator;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfiguration {

    public HttpExchangeRepository httpExchangeRepository(){
//        repository.setCapacity(1000);
        return new InMemoryHttpExchangeRepository();
    }
}
