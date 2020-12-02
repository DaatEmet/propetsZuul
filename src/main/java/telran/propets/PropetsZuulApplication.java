package telran.propets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import telran.propets.filter.LogFilter;

@SpringBootApplication
@EnableZuulProxy
public class PropetsZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropetsZuulApplication.class, args);
	}

	@Bean
	public LogFilter simpleFilter() {
		return new LogFilter();
	}

}
