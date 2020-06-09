package fr.plb.springsecuritydemo;

import fr.plb.springsecuritydemo.domain.Author;
import fr.plb.springsecuritydemo.domain.Vinyl;
import fr.plb.springsecuritydemo.repository.VinylRepository;
import fr.plb.springsecuritydemo.service.VinylService;
import fr.plb.springsecuritydemo.service.dto.VinylDTO;
import fr.plb.springsecuritydemo.service.mapper.VinylMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringSecurityDemoApplication.class)
@AutoConfigureMockMvc
public class VinylResourceIT {

    private static final String DEFAULT_SONG_NAME = "AAAAAAAAAA";

    private static final Instant DEFAULT_RELEASE_DATE = Instant.ofEpochMilli(0L);

    @Autowired
    private VinylRepository vinylRepository;

    @Autowired
    private VinylMapper vinylMapper;

    @Autowired
    private MockMvc restAccountMockMvc;

    private Vinyl vinyl;

    public static Vinyl createEntity() {
        return new Vinyl(DEFAULT_SONG_NAME, DEFAULT_RELEASE_DATE, new Author("id", "Toto", "", Instant.now()));
    }

    @BeforeEach
    public void initTest() {
        vinylRepository.deleteAll();
        vinyl = createEntity();
    }

    @Test
    @WithAnonymousUser
    public void createVinyl() throws Exception {
        // Prepare data
        int databaseSizeBeforeCreate = vinylRepository.findAll().size();
        VinylDTO vinylToCreate = vinylMapper.toDto(vinyl);

        // Call the endpoint
        restAccountMockMvc.perform(
                MockMvcRequestBuilders.post("/api/vinyls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vinylToCreate)))
                .andExpect(status().isCreated());

        // Validate
        List<Vinyl> vinylList = vinylRepository.findAll();
        assertThat(vinylList).hasSize(databaseSizeBeforeCreate + 1);
        Optional<Vinyl> testVinyl = vinylList.stream().findFirst();
        AssertionsForClassTypes.assertThat(testVinyl).isPresent();
        AssertionsForClassTypes.assertThat(testVinyl.get().getSongName()).isEqualTo(DEFAULT_SONG_NAME);
        AssertionsForClassTypes.assertThat(testVinyl.get().getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
    }


}
