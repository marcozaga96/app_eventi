package marcozagaria.app_eventi.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("l'id: " + id + " no è stato trovato");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
