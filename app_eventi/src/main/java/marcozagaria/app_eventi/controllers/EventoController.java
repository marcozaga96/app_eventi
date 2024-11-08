package marcozagaria.app_eventi.controllers;

import marcozagaria.app_eventi.entities.Evento;
import marcozagaria.app_eventi.payloads.EventoDTO;
import marcozagaria.app_eventi.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @GetMapping
    public Page<Evento> getEvento(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return eventoService.getAllEventoList(page, size, sortBy);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento createEvento(@RequestBody EventoDTO body) {
        return eventoService.saveEvento(body);
    }

    @GetMapping("/{Id}")
    public Evento cercaEventoId(@PathVariable UUID Id) {
        return eventoService.cercaId(Id);
    }

    @PutMapping("/{Id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Evento cercaEModifica(@PathVariable UUID Id, @RequestBody EventoDTO body) {
        return eventoService.cercaEventoEModifica(Id, body);
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void cercaECancella(@PathVariable UUID Id) {
        eventoService.cercaEventoECancella(Id);
    }
}
