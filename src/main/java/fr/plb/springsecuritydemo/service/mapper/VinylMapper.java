package fr.plb.springsecuritydemo.service.mapper;

import fr.plb.springsecuritydemo.domain.Vinyl;
import fr.plb.springsecuritydemo.service.dto.VinylDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface VinylMapper extends EntityMapper<VinylDTO, Vinyl> {

    @Mapping(source = "author.id", target = "authorId")
    VinylDTO toDto(Vinyl vinyl);

    @Mapping(source = "authorId", target = "author.id")
    Vinyl toEntity(VinylDTO vinylDTO);

    default Vinyl fromId(String id) {
        if (id == null) {
            return null;
        }
        Vinyl vinyl = new Vinyl();
        vinyl.setId(id);
        return vinyl;
    }

}
