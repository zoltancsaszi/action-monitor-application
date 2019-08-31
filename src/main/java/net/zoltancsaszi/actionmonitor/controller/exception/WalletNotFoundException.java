package net.zoltancsaszi.actionmonitor.controller.exception;

import lombok.NoArgsConstructor;

/**
 * Custom exception class for WalletController.
 *
 * @author Zoltan Csaszi
 */
@NoArgsConstructor
public class WalletNotFoundException extends Exception {

    public WalletNotFoundException(String message) {
        super(message);
    }

    public WalletNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
