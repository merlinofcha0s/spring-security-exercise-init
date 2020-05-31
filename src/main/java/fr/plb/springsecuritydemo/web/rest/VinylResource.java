package fr.plb.springsecuritydemo.web.rest;

import fr.plb.springsecuritydemo.service.VinylService;
import fr.plb.springsecuritydemo.service.dto.VinylDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.plb.springsecuritydemo.domain.Vinyl}.
 */
@RestController
@RequestMapping("/api")
public class VinylResource {

    private final Logger log = LoggerFactory.getLogger(VinylResource.class);

    private final VinylService vinylService;

    public VinylResource(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @PostMapping("/vinyls")
    public ResponseEntity<VinylDTO> createVinyl(@Valid @RequestBody VinylDTO vinylDTO) throws URISyntaxException {
        VinylDTO save = vinylService.save(vinylDTO);
        return ResponseEntity.created(new URI("/api/vinyls/" + save.getId())).body(save);
    }

    @PutMapping("/vinyls")
    public ResponseEntity<VinylDTO> updateVinyl(@Valid @RequestBody VinylDTO vinylDTO) {
        if (vinylDTO.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        VinylDTO vinylSaved = vinylService.save(vinylDTO);
        return ResponseEntity.ok().body(vinylSaved);
    }

    @GetMapping("/vinyls")
    public ResponseEntity<List<VinylDTO>> getAllVinyls() {
        return ResponseEntity.ok().body(vinylService.findAll());
    }

    @GetMapping("/vinyls/{id}")
    public ResponseEntity<VinylDTO> getVinyl(@PathVariable String id) {
        log.debug("REST request to get Vinyl : {}", id);
        Optional<VinylDTO> vinylDTO = vinylService.findOne(id);
        return vinylDTO.map(dto -> ResponseEntity.ok().body(dto)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/vinyls/{id}")
    public ResponseEntity<Void> deleteVinyl(@PathVariable String id) {
        log.debug("REST request to delete Vinyl : {}", id);
        vinylService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
