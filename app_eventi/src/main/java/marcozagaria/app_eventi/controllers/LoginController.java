package marcozagaria.app_eventi.controllers;

import marcozagaria.app_eventi.entities.Utente;
import marcozagaria.app_eventi.exception.BadRequestException;
import marcozagaria.app_eventi.payloads.LoginDTO;
import marcozagaria.app_eventi.payloads.LoginResponseDTO;
import marcozagaria.app_eventi.payloads.UtenteDTO;
import marcozagaria.app_eventi.services.LoginService;
import marcozagaria.app_eventi.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("autorizzato")
public class LoginController {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        return new LoginResponseDTO(loginService.controllaCredenziali(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente createUtente(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return utenteService.saveUtente(body);
    }
}
