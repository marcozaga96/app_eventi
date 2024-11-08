package marcozagaria.app_eventi.services;

import marcozagaria.app_eventi.entities.Utente;
import marcozagaria.app_eventi.exception.UnauthorizedException;
import marcozagaria.app_eventi.payloads.LoginDTO;
import marcozagaria.app_eventi.security.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWT jwt;
    @Autowired
    private PasswordEncoder bcrypt;

    public String controllaCredenziali(LoginDTO body) {
        Utente found = utenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            String accessToken = jwt.createToken(found);
            return accessToken;
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
