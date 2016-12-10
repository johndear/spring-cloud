package spring.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

//@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("spring.cloud.dao")
public class LocalApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(LocalApplication.class).web(true).run(args);
	}
}