package it.christianb.pfinanceblocking;

import it.christianb.pfinanceblocking.model.Deposit;
import it.christianb.pfinanceblocking.model.InMovement;
import it.christianb.pfinanceblocking.model.OutMovement;
import it.christianb.pfinanceblocking.model.TransferMovement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@SpringBootApplication
public class PfinanceBlockingApplication {

	@Configuration
	public static class DataRestConfigurer extends RepositoryRestConfigurerAdapter {
		@Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
			super.configureRepositoryRestConfiguration(config);
			config.exposeIdsFor(Deposit.class, InMovement.class, OutMovement.class, TransferMovement.class);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(PfinanceBlockingApplication.class, args);
	}
}
