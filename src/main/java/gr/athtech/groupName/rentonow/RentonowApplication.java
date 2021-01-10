package gr.athtech.groupName.rentonow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RentonowApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentonowApplication.class, args);
	}

}
