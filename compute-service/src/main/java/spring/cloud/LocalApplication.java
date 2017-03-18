package spring.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class LocalApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(LocalApplication.class).web(true).run(args);
	}
}