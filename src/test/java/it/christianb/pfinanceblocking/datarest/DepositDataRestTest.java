package it.christianb.pfinanceblocking.datarest;

import it.christianb.pfinanceblocking.model.Deposit;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class DepositDataRestTest {

    @Autowired
    private TestRestTemplate template;

    @Test public void testGetFullListOk() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        Resources<Deposit> result = template.withBasicAuth("user", "changeme")
                .exchange("/api/deposits", HttpMethod.GET, new HttpEntity<Void>(headers),
                    new ParameterizedTypeReference<Resources<Deposit>>() {}).getBody();

        assertThat(result.getContent().size(), Matchers.equalTo(1));
        Deposit theDeposit = result.iterator().next();
        assertEquals("Kraken", theDeposit.getName());
    }

}
