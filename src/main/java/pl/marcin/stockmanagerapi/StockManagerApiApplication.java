package pl.marcin.stockmanagerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
public class StockManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockManagerApiApplication.class, args);
	}

}
