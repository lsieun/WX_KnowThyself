package cn.lsieun.knowthyself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
// Cannot determine embedded database driver class for database type NONE
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class KnowthyselfApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowthyselfApplication.class, args);
	}
}
