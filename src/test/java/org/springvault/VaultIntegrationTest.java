package org.springvault;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URISyntaxException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CredentialsService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = VaultTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class VaultIntegrationTest {

    @Autowired
    private CredentialsService credentialsService;

    @Test
    public void givenCredentials_whenSecureCredentials_thenCredentialsSecured() throws URISyntaxException {
        try {
            Credentials credentials = new Credentials("username", "password");

            credentialsService.secureCredentials(credentials);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void whenAccessCredentials_thenCredentialsRetrieved() throws URISyntaxException {

        Credentials credentials = credentialsService.accessCredentials();

        assertNotNull(credentials);
        assertEquals("username", credentials.getUsername());
        assertEquals("password", credentials.getPassword());
    }

}
