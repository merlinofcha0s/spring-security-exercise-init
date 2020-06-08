package fr.plb.springsecuritydemo.service.mapper;

import fr.plb.springsecuritydemo.domain.Author;
import fr.plb.springsecuritydemo.service.dto.AuthorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {VinylMapper.class})
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {

    AuthorDTO toDto(Author author);

    Author toEntity(AuthorDTO authorDTO);

}
