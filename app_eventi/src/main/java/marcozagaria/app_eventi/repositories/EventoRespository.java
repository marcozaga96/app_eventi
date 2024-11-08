package marcozagaria.app_eventi.repositories;

import marcozagaria.app_eventi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventoRespository extends JpaRepository<Evento, UUID> {
}
