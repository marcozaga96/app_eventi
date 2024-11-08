package marcozagaria.app_eventi.payloads;

import java.time.LocalDateTime;

public record ExceptionDTO(String message, LocalDateTime timestamp) {
}
