package marcozagaria.app_eventi.repositories;

import marcozagaria.app_eventi.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    Optional<Prenotazione> findByUtenteIdAndDataDiPrenotazione(UUID utenteId, LocalDate dataDiPrenotazione);

    Optional<Prenotazione> findByUtenteIdAndEventoId(UUID utenteId, UUID eventoId);
}
