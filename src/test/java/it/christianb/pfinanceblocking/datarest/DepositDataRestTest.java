package it.christianb.pfinanceblocking.datarest;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.christianb.pfinanceblocking.model.Currency;
import it.christianb.pfinanceblocking.model.Deposit;
import it.christianb.pfinanceblocking.model.MovementType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@RunWith(SpringRunner.class)
public class DepositDataRestTest {

    @Autowired private WebApplicationContext context;

    @Autowired private JdbcTemplate jdbcTemplate;

    private MockMvc mvc;

    @Before
    public void setUpMvc() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON)).build();
    }

    @Test public void testGetFullDepositListOk() throws Exception {
        mvc.perform(get("/api/deposits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length($._embedded.deposits)", greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$._embedded.deposits[0].id", equalTo(12345)))
                .andExpect(jsonPath("$._embedded.deposits[0].movements").doesNotExist()); // movements have to be accessed explicitly
    }

    @Test public void testGetDepositMovementsOk() throws Exception {
        mvc.perform(get("/api/deposits/12345/movements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.movements.length()", equalTo(3)))
                .andExpect(jsonPath("$._embedded.movements[1].type", equalTo(MovementType.OUT.name()))); // order maintained
    }

    @Test public void testCreateNewDeposit() throws Exception {
        Deposit newDeposit = new Deposit("TestDeposit", Currency.USD);
        ObjectMapper mapper = new ObjectMapper();
        mvc.perform(post("/api/deposits").content(mapper.writeValueAsString(newDeposit)))
                .andExpect(status().isCreated());

        assertThat(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM DEPOSITS WHERE name = 'TestDeposit'", Integer.class), equalTo(1));
    }

    @Test public void testCreateNewMovement() throws Exception {
        String input = "{\"date\": \"2017-10-10T09:17:37.236+0000\", \"type\": \"IN\", \"amount\": \"3.00\", \"deposit\": \"/api/deposits/55555\"}";
        mvc.perform(post("/api/movements").content(input))
                .andExpect(status().isCreated());

        assertThat(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM MOVEMENTS WHERE fk_deposit=55555", Integer.class), equalTo(1));
    }

}
