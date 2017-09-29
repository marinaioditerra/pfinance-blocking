package it.christianb.pfinanceblocking.model;

import it.christianb.pfinanceblocking.repos.DepositRepo;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class OrmTest {

    @Autowired
    private DepositRepo depositRepo;

    @Rule public ExpectedException exception = ExpectedException.none();

    @Test public void testDepositInsertionOk() throws Exception {
        assertThat(depositRepo.findAll().size(), Matchers.equalTo(1)); // the one from the data.sql

        Deposit aDeposit = new Deposit("Unicredit", Currency.EURO);
        Deposit savedDeposit = depositRepo.save(aDeposit);

        assertNotNull(savedDeposit.getId());
        assertEquals("Unicredit", savedDeposit.getName());
        assertEquals(Currency.EURO, savedDeposit.getCurrency());
        assertThat(depositRepo.findAll().size(), Matchers.equalTo(2));
    }

    @Test public void testDepositUniqueName() throws Exception {
        exception.expectMessage(Matchers.containsString("depositNameConstraint".toUpperCase()));
        Deposit aDeposit = new Deposit("Kraken", Currency.EURO);
        depositRepo.save(aDeposit);
    }

}
