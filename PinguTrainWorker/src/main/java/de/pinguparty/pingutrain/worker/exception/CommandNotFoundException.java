package de.pinguparty.pingutrain.worker.exception;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String message) {
        super(message);
    }
}
