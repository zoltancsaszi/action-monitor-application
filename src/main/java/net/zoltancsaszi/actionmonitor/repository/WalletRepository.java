package net.zoltancsaszi.actionmonitor.repository;

import net.zoltancsaszi.actionmonitor.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
