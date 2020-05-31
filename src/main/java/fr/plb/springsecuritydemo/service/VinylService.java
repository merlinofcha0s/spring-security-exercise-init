package fr.plb.springsecuritydemo.service;


import fr.plb.springsecuritydemo.domain.Vinyl;
import fr.plb.springsecuritydemo.repository.VinylRepository;
import fr.plb.springsecuritydemo.service.dto.VinylDTO;
import fr.plb.springsecuritydemo.service.mapper.VinylMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VinylService {

    private final Logger log = LoggerFactory.getLogger(VinylService.class);

    private final VinylRepository vinylRepository;
    private final VinylMapper vinylMapper;

    public VinylService(VinylRepository vinylRepository, VinylMapper vinylMapper) {
        this.vinylRepository = vinylRepository;
        this.vinylMapper = vinylMapper;
    }

    public VinylDTO save(VinylDTO vinylDTO) {
        log.debug("Request to save Vinyl : {}", vinylDTO);
        Vinyl vinylSaved = vinylRepository.save(vinylMapper.toEntity(vinylDTO));
        return vinylMapper.toDto(vinylSaved);
    }

    public List<VinylDTO> findAll() {
        log.debug("Request to get all Vinyls");
        return vinylRepository.findAll().stream()
                .map(vinylMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<VinylDTO> findOne(String id) {
        log.debug("Request to get Vinyl : {}", id);
        return vinylRepository.findById(id)
                .map(vinylMapper::toDto);
    }


    public void delete(String id) {
        log.debug("Request to delete Vinyl : {}", id);
        vinylRepository.deleteById(id);
    }
}
