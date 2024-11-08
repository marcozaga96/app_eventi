package marcozagaria.app_eventi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import marcozagaria.app_eventi.enums.Ruolo;

public record UtenteDTO(@NotEmpty(message = "Username obbligatorio!")
                        @Size(min = 2, max = 40, message = "L'username deve essere compreso tra 2 e 40 caratteri!")
                        String username,
                        @NotEmpty(message = "Il ruolo è obbligatorio!")
                        @Size(min = 2, max = 40, message = "Il ruolo può essere utente o organizzatore!")
                        Ruolo ruolo,
                        @NotEmpty(message = "L'email è un campo obbligatorio!")
                        @Email(message = "L'email inserita non è un'email valida")
                        String email,
                        @NotEmpty(message = "La password è un campo obbligatorio!")
                        @Size(min = 4, message = "La password deve avere almeno 4 caratteri!")
                        String password) {
}
