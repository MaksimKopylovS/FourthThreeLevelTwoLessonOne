package max_sk.HomeWork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/secured.properties")
public class HomeWorkApplication {
	public static void main(String[] args) {
		SpringApplication.run(HomeWorkApplication.class, args);
	}

}
