package marcozagaria.app_eventi.payloads;

import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(@NotEmpty(message = "La mail è obbligatoria!")
                       String email,
                       @NotEmpty(message = "La password è obbligatoria!")
                       String password) {
}
