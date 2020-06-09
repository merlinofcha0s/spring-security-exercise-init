package fr.plb.springsecuritydemo.web.rest;

import fr.plb.springsecuritydemo.service.VinylService;
import fr.plb.springsecuritydemo.service.dto.VinylDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS))
                .body(vinylService.findAll());
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

    @GetMapping("/vinyls/{username}/get")
    @PreAuthorize("@isVinylOwner.check(#username)")
    public ResponseEntity<List<VinylDTO>> getVinylsbyUser(@PathVariable String username) {
        return ResponseEntity.ok(vinylService.getVinylByUser(username));
    }

    @GetMapping("/vinyls-for-admin/{username}")
    public ResponseEntity<List<VinylDTO>> getVinylUser(@PathVariable String username) {
        return ResponseEntity.ok(vinylService.getVinylByUser(username));
    }

    @GetMapping("/vinyls-user")
    public ResponseEntity<List<VinylDTO>> getVinylUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(vinylService.getVinylByUser(userDetails.getUsername()));
    }
}
