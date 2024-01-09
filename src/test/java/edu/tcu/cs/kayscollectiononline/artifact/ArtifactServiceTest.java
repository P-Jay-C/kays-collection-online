package edu.tcu.cs.kayscollectiononline.artifact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.kayscollectiononline.artifact.client.ai.chat.ChatClient;
import edu.tcu.cs.kayscollectiononline.artifact.client.ai.chat.dto.ChatRequest;
import edu.tcu.cs.kayscollectiononline.artifact.client.ai.chat.dto.ChatResponse;
import edu.tcu.cs.kayscollectiononline.artifact.client.ai.chat.dto.Choice;
import edu.tcu.cs.kayscollectiononline.artifact.client.ai.chat.dto.Message;
import edu.tcu.cs.kayscollectiononline.artifact.dto.ArtifactDto;
import edu.tcu.cs.kayscollectiononline.artifact.utils.IdWorker;
import edu.tcu.cs.kayscollectiononline.system.exception.ObjectNotFoundException;
import edu.tcu.cs.kayscollectiononline.wizard.Dto.WizardDto;
import edu.tcu.cs.kayscollectiononline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = "dev")
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @Mock
    IdWorker idWorker;

    @Mock
    ChatClient chatClient;

    @InjectMocks
    ArtifactService artifactService;


    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        artifacts = new ArrayList<>();

        Artifact a1 = new Artifact();

        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a1.setImageUrl("ImageUrl");


        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");

        artifacts.add(a1);
        artifacts.add(a2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {

        //Given. Arrange inputs and targets.
        Artifact artifact = new Artifact();

        artifact.setId("1234567");
        artifact.setName("Invisibility Cloak");
        artifact.setDescription("An invisibility cloak is used to make the wearer invisible");
        artifact.setImageUrl("imageUrl");

        Wizard wizard = new Wizard();

        wizard.setId(2L);
        wizard.setName("Harry Porter");

        artifact.setOwner(wizard);


        when(artifactRepository.findById("1234567")).thenReturn(Optional.of(artifact));
        // When. Act on the target behaviour

        Artifact returnArtifact = artifactService.findById("1234567");
        // Then. Assert expected outcomes
        assert(returnArtifact.getId().equals(artifact.getId()));
        assert(returnArtifact.getName().equals(artifact.getName()));
        assert(returnArtifact.getDescription().equals(artifact.getDescription()));
        assert(returnArtifact.getImageUrl().equals(artifact.getImageUrl())) ;

        verify(artifactRepository, times(1)).findById("1234567");
    }

    @Test
    void testFindByIdNotFound() {
        when(artifactRepository.findById(any(String.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ObjectNotFoundException.class,() -> {
            Artifact returnArtifact = artifactService.findById("1234567");
        });

        String expectedMessage = "Could not find artifact";
        String actualMessage = exception.getMessage();


        assertTrue(actualMessage.contains(expectedMessage));
        verify(artifactRepository,times(1)).findById("1234567");

    }

    @Test
    void testFindAllSuccess(){
        // Given
        when(artifactRepository.findAll()).thenReturn(artifacts);

        // When
        List<Artifact> actualArtifacts = artifactService.findAll();

        // then
        assertEquals(artifacts.size(), actualArtifacts.size());
        verify(artifactRepository, times(1)).findAll();

    }

    @Test
    void testSaveSuccess(){
        Artifact newArtifact = new Artifact()   ;
        newArtifact.setName("Artifact 3");
        newArtifact.setDescription("Description...");
        newArtifact.setImageUrl("ImageUrl...");

        when(idWorker.nextId()).thenReturn(123456L);
        when(artifactRepository.save(newArtifact)).thenReturn(newArtifact);

        Artifact savedArtifact = artifactService.save(newArtifact);

        assertEquals(savedArtifact.getId(), newArtifact.getId());
        assertEquals(savedArtifact.getName(), newArtifact.getName());
        assertEquals(savedArtifact.getDescription(), newArtifact.getDescription());
        assertEquals(savedArtifact.getImageUrl(), newArtifact.getImageUrl());

        verify(artifactRepository, times(1)).save(newArtifact);
    }

    @Test
    void testUpdateSuccess(){
        // Given
        Artifact oldArtifact = new Artifact();

        oldArtifact.setId("1250808601744904192");
        oldArtifact.setName("Invisibility Cloak");
        oldArtifact.setDescription("An invisibility cloak is used to make the wearer invisible");
        oldArtifact.setImageUrl("imageUrl");


        Artifact update = new Artifact();

        update.setId("1250808601744904192");
        update.setName("Invisibility Cloak");
        update.setDescription("A new description");
        update.setImageUrl("imageUrl");

        when(artifactRepository.findById("1250808601744904192")).thenReturn(Optional.of(oldArtifact));
        when(artifactRepository.save(oldArtifact)).thenReturn(oldArtifact);
        // When


        Artifact updatedArtifact = artifactService.update("1250808601744904192", update );

        assertEquals(update.getId(), updatedArtifact.getId());
        assertEquals(update.getDescription(), updatedArtifact.getDescription());

        verify(artifactRepository, times(1)).findById("1250808601744904192");
        verify(artifactRepository, times(1)).save(oldArtifact);

    }

    @Test
     void testUpdateNoFound(){
        Artifact update = new Artifact();

        update.setName("Invisibility Cloak");
        update.setDescription("A new description");
        update.setImageUrl("imageUrl");

        when(artifactRepository.findById("1250808601744904192")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ObjectNotFoundException.class, () ->{
            artifactService.update("1250808601744904192", update);
        });

        verify(artifactRepository,times(1)).findById("1250808601744904192");
    }

    @Test
    public void testDeleteArtifactSuccess() {
        // Given
        String existingArtifactId = "1250808601744904192";
        Artifact existingArtifact = new Artifact();
        existingArtifact.setId(existingArtifactId);

        when(artifactRepository.findById(existingArtifactId)).thenReturn(Optional.of(existingArtifact));
        doNothing().when(artifactRepository).deleteById("1250808601744904192");

        // When
        artifactService.delete(existingArtifactId);

        // Then
        verify(artifactRepository, times(1)).deleteById(existingArtifactId);
    }

    @Test
    public void testDeleteArtifactNotFound() {
        // Given
        String nonExistentArtifactId = "nonExistentId";
        when(artifactRepository.findById(nonExistentArtifactId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ObjectNotFoundException.class, () -> {
            artifactService.delete(nonExistentArtifactId);
        });

        verify(artifactRepository, never()).deleteById(nonExistentArtifactId);
    }

    @Test
    void testSummarizeSuccess() throws JsonProcessingException {
        // Given:
        WizardDto wizardDto = new WizardDto(1L, "Albus Dombledore", 2);
        List<ArtifactDto> artifactDtos = List.of(
                new ArtifactDto("1250808601744904191", "Deluminator", "A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.", "ImageUrl", wizardDto),
                new ArtifactDto("1250808601744904193", "Elder Wand", "The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.", "ImageUrl", wizardDto)
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArray = objectMapper.writeValueAsString(artifactDtos);

        List<Message> messages = List.of(
                new Message("system", "Your task is to generate a short summary of a given JSON array in at most 100 words. The summary must include the number of artifacts, each artifact's description, and the ownership information. Don't mention that the summary is from a given JSON array."),
                new Message("user", jsonArray)
        );

        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", messages);

        ChatResponse chatResponse = new ChatResponse(List.of(
                new Choice(0, new Message("assistant", "A summary of two artifacts owned by Albus Dumbledore."))));

        given(this.chatClient.generate(chatRequest)).willReturn(chatResponse);

        // When:
        String summary = this.artifactService.summarize(artifactDtos);

        // Then:
        assertThat(summary).isEqualTo("A summary of two artifacts owned by Albus Dumbledore.");
        verify(this.chatClient, times(1)).generate(chatRequest);
    }
}