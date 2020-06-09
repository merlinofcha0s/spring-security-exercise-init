package fr.plb.springsecuritydemo.service;


import fr.plb.springsecuritydemo.domain.Vinyl;
import fr.plb.springsecuritydemo.repository.VinylRepository;
import fr.plb.springsecuritydemo.service.dto.UserDTO;
import fr.plb.springsecuritydemo.service.dto.VinylDTO;
import fr.plb.springsecuritydemo.service.mapper.VinylMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VinylService {

    private final Logger log = LoggerFactory.getLogger(VinylService.class);

    private final VinylRepository vinylRepository;
    private final VinylMapper vinylMapper;
    private final UserService userService;

    public VinylService(VinylRepository vinylRepository, VinylMapper vinylMapper, UserService userService) {
        this.vinylRepository = vinylRepository;
        this.vinylMapper = vinylMapper;
        this.userService = userService;
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

    @PreFilter("hasRole('ADMIN') or filterObject.username == authencation.username")
    public List<VinylDTO> saveAll(List<VinylDTO> vinylDTOs) {
        List<Vinyl> collect = vinylDTOs.stream().map(vinylMapper::toEntity).collect(Collectors.toList());
        List<Vinyl> vinylSaved = vinylRepository.saveAll(collect);
        return vinylSaved.stream().map(vinylMapper::toDto).collect(Collectors.toList());
    }

    public List<VinylDTO> getVinylByUser(String username) {
        Optional<UserDTO> connectedUser = userService.getUserByUsername(username);
        return connectedUser.map(userDTO -> vinylRepository.findByUserId(userDTO.getId())
                .stream()
                .map(vinylMapper::toDto)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }
}
