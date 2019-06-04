package org.springvault;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;


@Service
public class CredentialsService {

    @Autowired
    private VaultTemplate vaultTemplate;


    public void secureCredentials(Credentials credentials) throws URISyntaxException {
        VaultKeyValueOperations keyValueOperations = vaultTemplate.opsForKeyValue("secret", VaultKeyValueOperationsSupport.KeyValueBackend.versioned());
        keyValueOperations.put("credentials/myapp",credentials);
        //vaultTemplate.write("credentials/myapp", credentials);
    }


    public Credentials accessCredentials() throws URISyntaxException {

       // VaultResponseSupport<Credentials> response = vaultTemplate.read("secret/credentials/myapp", Credentials.class);
       VaultResponse vr =  vaultTemplate.opsForKeyValue("secret", VaultKeyValueOperationsSupport.KeyValueBackend.versioned()).get("credentials/myapp");

        return new Credentials(vr.getData().get("username").toString(),vr.getData().get("password").toString());
    }

}
