package net.zoltancsaszi.actionmonitor.controller.exception;

import lombok.NoArgsConstructor;

/**
 * Custom exception class for WalletController.
 *
 * @author Zoltan Csaszi
 */
@NoArgsConstructor
public class WalletNameException extends Exception {

    public WalletNameException(String message) {
        super(message);
    }

    public WalletNameException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
