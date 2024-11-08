package marcozagaria.app_eventi.services;

import marcozagaria.app_eventi.entities.Utente;
import marcozagaria.app_eventi.exception.BadRequestException;
import marcozagaria.app_eventi.exception.NotFoundException;
import marcozagaria.app_eventi.payloads.UtenteDTO;
import marcozagaria.app_eventi.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder bcrypt;


    public Page<Utente> getAllUtenteList(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return utenteRepository.findAll(pageable);
    }

    public Utente saveUtente(UtenteDTO body) {
        utenteRepository.findByEmail(body.email()).ifPresent(dipendente -> {
            throw new BadRequestException("eamil " + body.email() + " gia in uso!");
        });

        Utente newUtente = new Utente(body.username(), body.email(), body.password(), body.ruolo());

        newUtente.setPassword(bcrypt.encode(body.password()));
        Utente saveUtente = utenteRepository.save(newUtente);


        return saveUtente;
    }

    public Utente cercaId(UUID id) {

        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Utente cercaUtenteEModifica(UUID id, UtenteDTO body) {
        Utente cerca = cercaId(id);
        cerca.setUsername(body.username());
        cerca.setEmail(body.email());
        cerca.setPassword(body.password());
        cerca.setRuolo(body.ruolo());
        if (cerca == null) throw new NotFoundException(id);
        return utenteRepository.save(cerca);
    }


    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }

    public void cercaUtenteECancella(UUID id) {
        Utente cerca = cercaId(id);
        if (cerca == null) throw new NotFoundException(id);
        utenteRepository.delete(cerca);
    }
}
