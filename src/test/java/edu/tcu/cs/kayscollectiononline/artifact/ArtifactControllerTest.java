package edu.tcu.cs.kayscollectiononline.artifact;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.kayscollectiononline.artifact.dto.ArtifactDto;
import edu.tcu.cs.kayscollectiononline.system.StatusCode;
import edu.tcu.cs.kayscollectiononline.system.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "dev")
class ArtifactControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ArtifactService artifactService;
    @Mock
    private ArtifactRepository artifactRepository;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @Autowired
    ObjectMapper objectMapper;

    List<Artifact> artifacts;


    @BeforeEach
    void setUp() {

        artifacts = new ArrayList<>();
        Artifact a1 = new Artifact();

        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a1.setImageUrl("ImageUrl");

        artifacts.add(a1);

        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");

        artifacts.add(a2);

        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder Wand");
        a3.setDescription("The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.");
        a3.setImageUrl("imageUrl");
        artifacts.add(a3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindArtifactByIdSuccess() throws Exception {

        // Given
        when(artifactService.findById("1250808601744904191")).thenReturn(artifacts.get(0));

        // When then
        mockMvc.perform(get(baseUrl+"/artifacts/1250808601744904191")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))

                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data.name").value("Deluminator"));
    }

    @Test
    void testFindArtifactByIdNotFound() throws Exception {

        String artifactId = "1250808601744904191";
        // Given
        when(artifactService.findById("1250808601744904191")).thenThrow(new ObjectNotFoundException("artifact",artifactId));

        mockMvc.perform(get(baseUrl+"/artifacts/"+artifactId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindAllArtifactsSuccess() throws Exception {
        // Given
        when(artifactService.findAll()).thenReturn(artifacts);

        // When then
        mockMvc.perform(get(baseUrl+"/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(artifacts.size())));
    }

    @Test
    void testAddArtifactSuccess() throws Exception {
        // given
        ArtifactDto artifactDto = new ArtifactDto(null,
                "Remembrall",
                "A remembrall makes you remember past event way back to childhoot",
                "ImageUrl",
                null);

        String json = this.objectMapper.writeValueAsString(artifactDto);

        Artifact savedArtifact = new Artifact();
        savedArtifact.setId("125008372395907375035835");
        savedArtifact.setName("Remembrall");
        savedArtifact.setDescription("A remembrall makes you remember past event way back to childhoot");
        savedArtifact.setImageUrl("ImageUrl");

        when(artifactService.save(Mockito.any(Artifact.class))).thenReturn(savedArtifact);
        // when
        mockMvc.perform(post(baseUrl+"/artifacts").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.description").value(savedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.name").value(savedArtifact.getName()))
                .andExpect(jsonPath("$.data.imageUrl").value(savedArtifact.getImageUrl()));
    }

    @Test
    void testUpdateArtifactSuccess() throws Exception {
        // Given
        ArtifactDto artifactDto = new ArtifactDto(
                "125008372395907375035835",
                "Remembrall",
                "A remembrall makes you remember past event way back to childhood",
                "ImageUrl",
                null); // Ensure that the DTO fields are correctly aligned with your model

        String json = this.objectMapper.writeValueAsString(artifactDto);

        Artifact updatedArtifact = new Artifact();
        updatedArtifact.setId("125008372395907375035835");
        updatedArtifact.setName("Invisibility Cloak");
        updatedArtifact.setDescription("A new description");
        updatedArtifact.setImageUrl("ImageUrl");

        // When updating, you typically pass the DTO, not the entity directly
        when(artifactService.update(ArgumentMatchers.eq("125008372395907375035835"), Mockito.any(Artifact.class)))
                .thenReturn(updatedArtifact);

        // When
        mockMvc.perform(put(baseUrl+"/artifacts/125008372395907375035835")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value(updatedArtifact.getId()))
                .andExpect(jsonPath("$.data.description").value(updatedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.name").value(updatedArtifact.getName()))
                .andExpect(jsonPath("$.data.imageUrl").value(updatedArtifact.getImageUrl()));

        verify(artifactService, times(1)).update(ArgumentMatchers.eq("125008372395907375035835"),any(Artifact.class));
    }

    @Test
    void testUpdateNonExistentArtifact() throws Exception {
        // Given
        String nonExistentArtifactId = "nonExistentId";
        ArtifactDto artifactDto = new ArtifactDto(nonExistentArtifactId, "NonExistentArtifact",
                "This is a non-existent artifact", "NonExistentImageUrl", null);

        String json = objectMapper.writeValueAsString(artifactDto);

        when(artifactService.update(eq(nonExistentArtifactId), any(Artifact.class)))
                .thenThrow(new ObjectNotFoundException("artifact","nonExistentId"));

        // When & Then
        mockMvc.perform(put(baseUrl+"/artifacts/" + nonExistentArtifactId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());

        verify(artifactService, times(1)).update(eq(nonExistentArtifactId), any(Artifact.class));
    }

    @Test
    public void testDeleteArtifactSuccess() throws Exception {
        String artifactId = "125008372395907375035835";
        doNothing().when(artifactService).delete(artifactId);

        mockMvc.perform(delete(baseUrl+"/artifacts/" + artifactId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }


    @Test
    public void testDeleteArtifactFailure() throws Exception {
        String artifactId = "testArtifactId";
        doThrow(new ObjectNotFoundException("artifact",artifactId)).when(artifactService).delete(anyString());

        mockMvc.perform(delete(baseUrl + "/artifacts/" + artifactId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false));
    }
}