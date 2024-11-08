package marcozagaria.app_eventi.services;

import marcozagaria.app_eventi.entities.Evento;
import marcozagaria.app_eventi.entities.Utente;
import marcozagaria.app_eventi.exception.NotFoundException;
import marcozagaria.app_eventi.payloads.EventoDTO;
import marcozagaria.app_eventi.repositories.EventoRespository;
import marcozagaria.app_eventi.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EventoService {
    @Autowired
    private EventoRespository eventoRespository;
    @Autowired
    private UtenteRepository utenteRepository;

    public Page<Evento> getAllEventoList(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return eventoRespository.findAll(pageable);
    }

    public Evento saveEvento(EventoDTO body) {
        Optional<Utente> utente = utenteRepository.findById(body.organizzatore_id());
        if (!utente.isPresent()) {
            throw new RuntimeException("Dipendente non trovato con ID: " + body.organizzatore_id());
        }
        Utente utente1 = utente.get();
        Evento newEvento = new Evento(body.titolo(), body.descrizione(), body.dataEvento(), body.luogo(), body.postiDisponibili(), utente1);
        return eventoRespository.save(newEvento);
    }


    public Evento cercaId(UUID id) {
        return eventoRespository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Evento cercaEventoEModifica(UUID id, EventoDTO body) {
        Evento cerca = cercaId(id);
        cerca.setTitolo(body.titolo());
        cerca.setDescrizione(body.descrizione());
        cerca.setDataEvento(body.dataEvento());
        cerca.setLuogo(body.luogo());
        cerca.setPostiDisponibili(body.postiDisponibili());
        if (cerca == null) throw new NotFoundException(id);
        return eventoRespository.save(cerca);
    }

    public void cercaEventoECancella(UUID id) {
        Evento cerca = cercaId(id);
        if (cerca == null) throw new NotFoundException(id);
        eventoRespository.delete(cerca);
    }
}
