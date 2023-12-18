package edu.tcu.cs.kayscollectiononline;

import edu.tcu.cs.kayscollectiononline.artifact.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KaysCollectionOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaysCollectionOnlineApplication.class, args);
	}

	@Bean
	public IdWorker idWorker(){
		return new IdWorker(1,1);
	}


}

