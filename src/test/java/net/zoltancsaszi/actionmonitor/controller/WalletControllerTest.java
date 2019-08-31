package net.zoltancsaszi.actionmonitor.controller;

import net.zoltancsaszi.actionmonitor.model.Wallet;
import net.zoltancsaszi.actionmonitor.repository.WalletRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WalletControllerTest {

    private static final String BASE_PATH = "/api/wallets";

    @LocalServerPort
    private int port;
    private String url;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private WalletRepository walletRepository;

    @Before
    public void setup() {
        url = "http://localhost:" + port + BASE_PATH;
    }

    @Test
    public void insertWalletTest_withNameIsNull() {

        // given a wallet entity

        Wallet wallet = new Wallet(1L, 0.0, null);

        // when we post the entity to the WalletService

        ResponseEntity<Wallet> responseEntity =
                restTemplate.postForEntity(url, wallet, Wallet.class);

        // then it won't save into the repository

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

        verify(walletRepository, never()).save(wallet);
    }

    @Test
    public void insertWalletTest() {

        // given a wallet entity

        Wallet wallet = new Wallet(Math.random(), "John");

        // when we post the entity to the WalletService

        ResponseEntity<Wallet> responseEntity =
                restTemplate.postForEntity(url, wallet, Wallet.class);

        // then it will be saved to the repository

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));

        verify(walletRepository).save(wallet);
    }

    @Test
    public void updateWalletTest_notFound() {

        // given a non persisted wallet entity

        Wallet wallet = new Wallet(0L, Math.random(), "John");

        // when we post the entity to the WalletService updateWallet

        String updateUrl = url + "/" + wallet.getId();

        ResponseEntity<Wallet> responseEntity =
                restTemplate.postForEntity(updateUrl, wallet, Wallet.class);

        // then it won't found an existing wallet to update

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

        verify(walletRepository, never()).save(wallet);
    }

    @Test
    public void updateWalletTest() {

        // given a persisted wallet entity

        Wallet wallet = new Wallet(1L, Math.random(), "John");

        when(walletRepository.findById(eq(1L))).thenReturn(Optional.of(wallet));

        // when we post the entity to the WalletService updateWallet

        String updateUrl = url + "/" + wallet.getId();

        ResponseEntity<Wallet> responseEntity =
                restTemplate.postForEntity(updateUrl, wallet, Wallet.class);

        // then it will be saved to the repository

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        verify(walletRepository).save(wallet);
    }

    @Test
    public void listEntitiesTest_noResult() {

        // given no wallets in the repository

        when(walletRepository.findAll()).thenReturn(Collections.emptyList());

        // when we request all the entities of the WalletService

        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);

        // then we get an empty list

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        List result = responseEntity.getBody();

        assertTrue(result.isEmpty());
        verify(walletRepository).findAll();
    }

    @Test
    public void listEntitiesTest_oneResult() {

        // given a list of one wallet in the repository

        List<Wallet> wallets = List.of(new Wallet(Math.random(), "Jack"));

        when(walletRepository.findAll()).thenReturn(wallets);

        // when we request all the entities of the WalletService

        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);

        // then we get the exact wallet

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        List result = responseEntity.getBody();

        assertThat(result.size(), equalTo(1));
        verify(walletRepository).findAll();
    }
}