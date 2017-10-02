package it.christianb.pfinanceblocking.model;

import it.christianb.pfinanceblocking.repos.DepositRepo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@RunWith(SpringRunner.class)
public class OrmTest {

    @Autowired
    private DepositRepo depositRepo;

    @Rule public ExpectedException exception = ExpectedException.none();

    @Test public void testDepositInsertionOk() throws Exception {
        assertThat(depositRepo.findAll().size(), equalTo(2)); // the ones from the data.sql

        Deposit aDeposit = new Deposit("Unicredit", Currency.EURO);
        Deposit savedDeposit = depositRepo.save(aDeposit);

        assertNotNull(savedDeposit.getId());
        assertEquals("Unicredit", savedDeposit.getName());
        assertEquals(Currency.EURO, savedDeposit.getCurrency());
        assertThat(savedDeposit.getMovements().size(), equalTo(0));
        assertThat(depositRepo.findAll().size(), equalTo(3));
    }

    @Test public void testDepositUniqueName() throws Exception {
        exception.expectMessage(containsString("depositNameConstraint".toUpperCase()));
        Deposit aDeposit = new Deposit("Kraken", Currency.EURO);
        depositRepo.save(aDeposit);
    }

    @Test public void testDepositMovementsMapping() throws Exception {
        Deposit krakenDeposit = depositRepo.findOne(12345L); // from the data.sql
        Deposit coinBaseDeposit = depositRepo.findOne(99999L); // from the data.sql

        assertThat(krakenDeposit.getMovements().size(), equalTo(2));
        AbstractMovement movement = krakenDeposit.getMovements().get(0);
        assertThat(movement.getType(), equalTo(MovementType.IN));
        assertTrue(movement instanceof InMovement);
        assertThat(movement.getAmount(), equalTo(new BigDecimal("10.00")));
        assertNotNull(movement.getDate());
        movement = krakenDeposit.getMovements().get(1);
        assertThat(movement.getType(), equalTo(MovementType.OUT));
        assertTrue(movement instanceof OutMovement);
        assertThat(movement.getAmount(), equalTo(new BigDecimal("1.00")));

        assertThat(coinBaseDeposit.getMovements().size(), equalTo(1));
        movement = coinBaseDeposit.getMovements().get(0);
        assertThat(movement.getType(), equalTo(MovementType.TRANSFER));
        assertTrue(movement instanceof TransferMovement);
        assertThat(movement.getAmount(), equalTo(new BigDecimal("2.00")));
    }

}
