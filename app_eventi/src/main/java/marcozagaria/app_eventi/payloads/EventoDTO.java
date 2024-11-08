package marcozagaria.app_eventi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record EventoDTO(@NotEmpty(message = "Il titolo è obbligatorio!")
                        @Size(min = 2, max = 40, message = "Il titolo deve essere compreso tra 2 e 40 caratteri!")
                        String titolo,
                        @NotEmpty(message = "La descrizione è obbligatorio!")
                        @Size(min = 2, max = 40, message = "La descrizione deve essere compresa tra 2 e 40 caratteri!")
                        String descrizione,
                        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La data deve essere nel formato yyyy-MM-dd")
                        LocalDate dataEvento,
                        @NotEmpty(message = "Il luogo è obbligatorio!")
                        @Size(min = 2, max = 40, message = "Il luogo deve essere compreso tra 2 e 40 caratteri!")
                        String luogo,
                        @NotEmpty(message = "I posti disponibili sono obbligatori!")
                        Integer postiDisponibili,
                        @NotEmpty(message = "L'organizzatore è obbligatorio!")
                        UUID organizzatore_id) {
}
