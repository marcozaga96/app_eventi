package marcozagaria.app_eventi.payloads;

import jakarta.validation.constraints.NotEmpty;

public record LoginResponseDTO(
        @NotEmpty(message = "Token obbligatorio!")
        String accessToken) {
}
