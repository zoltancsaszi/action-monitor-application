package net.zoltancsaszi.actionmonitor.controller;

import net.zoltancsaszi.actionmonitor.controller.exception.WalletNameException;
import net.zoltancsaszi.actionmonitor.controller.exception.WalletNotFoundException;
import net.zoltancsaszi.actionmonitor.model.Wallet;
import net.zoltancsaszi.actionmonitor.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Rest controller component to provide interface to the Wallet repository.
 *
 * @author Zoltan Csaszi
 */
@RestController
@RequestMapping("/api")
public class WalletController {

    @Autowired
    private WalletRepository repository;

    @GetMapping("/wallets")
    public List<Wallet> listWallets() {

        List<Wallet> wallets = repository.findAll();

        return wallets;
    }

    @PostMapping("/wallets")
    public ResponseEntity<Wallet> insertWallet(@RequestBody Wallet wallet) throws Exception {

        validateWallet(wallet);

        repository.save(wallet);

        ResponseEntity<Wallet> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(wallet);

        return responseEntity;
    }

    @PostMapping("/wallets/{id}")
    public ResponseEntity<Wallet> updateWallet(@PathVariable long id, @RequestBody Wallet wallet) throws Exception {

        Optional<Wallet> walletOptional = repository.findById(id);

        Wallet model = walletOptional.orElseThrow(WalletNotFoundException::new);

        model.setAmount(wallet.getAmount());
        model.setName(wallet.getName());

        validateWallet(wallet);

        repository.save(model);

        ResponseEntity<Wallet> responseEntity = ResponseEntity.ok(wallet);

        return responseEntity;
    }

    private void validateWallet(Wallet wallet) throws WalletNameException {
        if (wallet.getName() == null) {
            throw new WalletNameException("Wallet name cannot be null");
        }
    }
}