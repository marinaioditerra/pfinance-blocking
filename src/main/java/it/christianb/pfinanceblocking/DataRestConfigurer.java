package it.christianb.pfinanceblocking;

import it.christianb.pfinanceblocking.model.Deposit;
import it.christianb.pfinanceblocking.model.Movement;
import it.christianb.pfinanceblocking.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class DataRestConfigurer extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config);
        config.exposeIdsFor(User.class, Deposit.class, Movement.class);
    }

}
