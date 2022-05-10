package in.boomcabs.drivermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DrivermanagerApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DrivermanagerApplication.class, args);
		new SpringApplicationBuilder(DrivermanagerApplication.class).web(WebApplicationType.NONE).run(args);
	}

}
