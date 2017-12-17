package it.christianb.pfinanceblocking.model;

import it.christianb.pfinanceblocking.repos.DepositRepo;
import it.christianb.pfinanceblocking.repos.UserRepo;
import org.hamcrest.Matcher;
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
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
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

    private static Long KRAKEN_DEPOSIT_ID = 12345L;
    private static Long COINBASE_DEPOSIT_ID = 99999L;
    private static Long MYBANK_DEPOSIT_ID = 55555L;
    private static Long NICK_USER_ID = 2L;

    @Rule public ExpectedException exception = ExpectedException.none();

    @Test public void testDepositCreationOk() throws Exception {
        assertThat(depositRepo.findAll().size(), equalTo(3)); // the ones from the data.sql script
        User user = userRepo.findOne(NICK_USER_ID);
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
        Deposit krakenDeposit = depositRepo.findOne(KRAKEN_DEPOSIT_ID);
        Deposit coinBaseDeposit = depositRepo.findOne(COINBASE_DEPOSIT_ID);

        assertThat(krakenDeposit.getMovements().size(), equalTo(3)); // from the data.sql
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

    @Test public void MovementTagsMapping() throws Exception {
        Deposit myBankDeposit = depositRepo.findOne(MYBANK_DEPOSIT_ID);

        assertThat(myBankDeposit.getMovements().size(), equalTo(1));
        Movement movement = myBankDeposit.getMovements().get(0);

        assertThat(movement.getTags().size(), equalTo(1));
        assertThat(movement.getTags().stream().map(Tag::getName).collect(Collectors.toList()), contains("Salary"));
    }

}
