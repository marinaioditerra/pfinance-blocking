package it.christianb.pfinanceblocking.model;

import it.christianb.pfinanceblocking.repos.DepositRepo;
import it.christianb.pfinanceblocking.repos.UserRepo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
@Transactional
@RunWith(SpringRunner.class)
public class OrmTest {

    @Autowired
    private DepositRepo depositRepo;

    @Autowired
    private UserRepo userRepo;

    @Rule public ExpectedException exception = ExpectedException.none();

    @Test public void testDepositInsertionOk() throws Exception {
        assertThat(depositRepo.findAll().size(), equalTo(3)); // the ones from the data.sql script
        User user = userRepo.findOne(2L); // the one from the data.sql script
        assertThat(user, notNullValue());

        Deposit aDeposit = new Deposit("Unicredit", Currency.EURO, user);
        Deposit savedDeposit = depositRepo.save(aDeposit);

        assertThat(savedDeposit.getId(), notNullValue());
        assertThat(savedDeposit.getOwner().getUsername(), equalTo("nick"));
        assertThat(savedDeposit.getName(), equalTo("Unicredit"));
        assertThat(savedDeposit.getCurrency(), equalTo(Currency.EURO));
        assertThat(savedDeposit.getMovements().size(), equalTo(0));
        assertThat(depositRepo.findAll().size(), equalTo(4));
    }

    @Test public void testDepositUniqueName() throws Exception {
        User user = userRepo.findOne(2L); // the one from the data.sql script
        assertThat(user, notNullValue());
        exception.expectMessage(containsString("depositNameConstraint".toUpperCase()));
        Deposit aDeposit = new Deposit("Kraken", Currency.EURO, user);
        depositRepo.save(aDeposit);
    }

    @Test public void testDepositMovementsMapping() throws Exception {
        Deposit krakenDeposit = depositRepo.findOne(12345L); // from the data.sql
        Deposit coinBaseDeposit = depositRepo.findOne(99999L); // from the data.sql

        assertThat(krakenDeposit.getMovements().size(), equalTo(3));
        Movement movement = krakenDeposit.getMovements().get(0);
        assertThat(movement.getType(), equalTo(MovementType.IN));
        assertThat(movement.getAmount(), equalTo(new BigDecimal("10.00")));
        assertNotNull(movement.getDate());
        movement = krakenDeposit.getMovements().get(1);
        assertThat(movement.getType(), equalTo(MovementType.OUT));
        assertThat(movement.getAmount(), equalTo(new BigDecimal("1.00")));

        assertThat(coinBaseDeposit.getMovements().size(), equalTo(1));
        movement = coinBaseDeposit.getMovements().get(0);
        assertThat(movement.getType(), equalTo(MovementType.TRANSFER));
        assertThat(movement.getAmount(), equalTo(new BigDecimal("2.00")));
    }

}
