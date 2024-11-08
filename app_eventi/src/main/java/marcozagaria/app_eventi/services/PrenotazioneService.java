package marcozagaria.app_eventi.services;

import marcozagaria.app_eventi.entities.Evento;
import marcozagaria.app_eventi.entities.Prenotazione;
import marcozagaria.app_eventi.entities.Utente;
import marcozagaria.app_eventi.exception.NotFoundException;
import marcozagaria.app_eventi.payloads.PrenotazioneDTO;
import marcozagaria.app_eventi.repositories.EventoRespository;
import marcozagaria.app_eventi.repositories.PrenotazioneRepository;
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
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private EventoRespository eventoRespository;
    @Autowired
    private UtenteRepository utenteRepository;

    public Page<Prenotazione> getAllPrenotazioneList(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }


    public Prenotazione savePrenotazione(PrenotazioneDTO body) {
        Optional<Evento> evento = eventoRespository.findById(body.evento_id());
        if (!evento.isPresent()) {
            throw new RuntimeException("Evento non trovato con ID: " + body.evento_id());
        }
        Evento evento1 = evento.get();

        Optional<Utente> utente = utenteRepository.findById(body.utente_id());
        if (!utente.isPresent()) {
            throw new RuntimeException("Utente non trovato con ID: " + body.utente_id());
        }
        Utente utente1 = utente.get();

        Optional<Prenotazione> prenotazioniDipendentePerData = prenotazioneRepository.findByUtenteIdAndDataDiPrenotazione(body.utente_id(), body.dataDiPrenotazione());
        if (!prenotazioniDipendentePerData.isEmpty()) {
            throw new RuntimeException("L'utente ha già una prenotazione per questa data.");
        }
        Optional<Prenotazione> prenotazioniDipendente = prenotazioneRepository.findByUtenteIdAndEventoId(body.utente_id(), body.evento_id());
        if (!prenotazioniDipendente.isEmpty()) {
            throw new RuntimeException("L'utente ha già una prenotazione.");
        }

        Prenotazione newPrenotazione = new Prenotazione(utente1, evento1, body.dataDiPrenotazione());

        return prenotazioneRepository.save(newPrenotazione);
    }


    public Prenotazione cercaId(UUID id) {

        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Prenotazione cercaPrenotazioneEModifica(UUID id, PrenotazioneDTO body) {
        Prenotazione cerca = cercaId(id);
        Optional<Evento> evento = eventoRespository.findById(body.evento_id());
        if (!evento.isPresent()) {
            throw new RuntimeException("Evento non trovato con ID: " + body.evento_id());
        }
        Evento evento1 = evento.get();
        cerca.setEvento(evento1);
        cerca.setDataDiPrenotazione(body.dataDiPrenotazione());
        if (cerca == null) throw new NotFoundException(id);
        return prenotazioneRepository.save(cerca);
    }

    public void cercaPrenotazioneECancella(UUID id) {
        Prenotazione cerca = cercaId(id);
        if (cerca == null) throw new NotFoundException(id);
        prenotazioneRepository.delete(cerca);
    }
}
