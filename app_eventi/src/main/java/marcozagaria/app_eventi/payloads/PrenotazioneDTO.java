package marcozagaria.app_eventi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public record PrenotazioneDTO(
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La data deve essere nel formato yyyy-MM-dd")
        LocalDate dataDiPrenotazione,
        @NotEmpty(message = "L'utente è obbligatorio!")
        UUID utente_id,
        @NotEmpty(message = "L'evento è obbligatorio!")
        UUID evento_id) {
}
