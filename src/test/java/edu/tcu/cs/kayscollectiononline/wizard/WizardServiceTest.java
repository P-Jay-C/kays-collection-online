package edu.tcu.cs.kayscollectiononline.wizard;

import edu.tcu.cs.kayscollectiononline.artifact.Artifact;
import edu.tcu.cs.kayscollectiononline.artifact.ArtifactRepository;
import edu.tcu.cs.kayscollectiononline.artifact.utils.IdWorker;
import edu.tcu.cs.kayscollectiononline.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = "dev")
class WizardServiceTest {
    @Mock
    WizardRepository wizardRepository;

    @Mock
    ArtifactRepository artifactRepository;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    WizardService wizardService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findById_Success() {
        Long wizardId = 1L;
        Wizard mockWizard = new Wizard();
        mockWizard.setId(wizardId);

        when(wizardRepository.findById(wizardId)).thenReturn(Optional.of(mockWizard));

        Wizard found = wizardService.findById(wizardId);

        assertNotNull(found);
        assertEquals(wizardId, found.getId());
    }

    @Test
    void findById_NotFound() {
        Long wizardId = 1L;
        when(wizardRepository.findById(wizardId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> wizardService.findById(wizardId));
    }

    @Test
    void findAll_Success() {
        List<Wizard> mockWizards = Arrays.asList(new Wizard(), new Wizard());
        when(wizardRepository.findAll()).thenReturn(mockWizards);

        List<Wizard> wizards = wizardService.findAll();

        assertNotNull(wizards);
        assertEquals(2, wizards.size());
    }

    @Test
    void save_Success() {
        Wizard newWizard = new Wizard();
        when(idWorker.nextId()).thenReturn(1L);
        when(wizardRepository.save(any(Wizard.class))).thenAnswer(i -> i.getArguments()[0]);

        Wizard savedWizard = wizardService.save(newWizard);

        assertNotNull(savedWizard);
        assertEquals(1L, savedWizard.getId());
    }

    @Test
    void update_Success() {
        Long wizardId = 1L;
        Wizard existingWizard = new Wizard();
        existingWizard.setId(wizardId);
        existingWizard.setName("Old Name");

        Wizard updatedWizard = new Wizard();
        updatedWizard.setName("New Name");

        when(wizardRepository.findById(wizardId)).thenReturn(Optional.of(existingWizard));
        when(wizardRepository.save(any(Wizard.class))).thenAnswer(i -> i.getArguments()[0]);

        Wizard result = wizardService.update(wizardId, updatedWizard);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
    }

    @Test
    void update_NotFound() {
        Long wizardId = 1L;
        when(wizardRepository.findById(wizardId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> wizardService.update(wizardId, new Wizard()));
    }

    @Test
    void delete_Success() {
        String wizardId = "1";
        Wizard mockWizard = new Wizard();
        mockWizard.setId(Long.valueOf(wizardId));

        when(wizardRepository.findById(Long.valueOf(wizardId))).thenReturn(Optional.of(mockWizard));
        doNothing().when(wizardRepository).deleteById(Long.valueOf(wizardId));

        assertDoesNotThrow(() -> wizardService.delete(wizardId));
    }


    @Test
    void delete_NotFound() {
        String wizardId = "1";
        when(wizardRepository.findById(Long.valueOf(wizardId))).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> wizardService.delete(wizardId));
    }

    @Test
    void testAssignArtifactSuccess(){
        // Given
            Artifact a = new Artifact();
            a.setId("123456");

            Wizard w2 = new Wizard();
            w2.setId(2L);
            w2.setName("Harry Porter");
            w2.addArtifact(a);

            Wizard w3 = new Wizard();
            w3.setId(3L);
            w3.setName("Neville Longbottom");
//            w3.addArtifact(a);


            given(artifactRepository.findById("123456")).willReturn(Optional.of(a));
            given(wizardRepository.findById(3L)).willReturn(Optional.of(w3));
        // When

        this.wizardService.assignArtifact(3L,"123456");

        // Then

        assertEquals(3,a.getOwner().getId());
        assert(w3.getArtifacts()).contains(a);
    }

    @Test
    void testAssignArtifactErrorWithNonExistentWizardId(){
        // Given
        Artifact a = new Artifact();
        a.setId("123456");

        Wizard w2 = new Wizard();
        w2.setId(2L);
        w2.setName("Harry Porter");
        w2.addArtifact(a);

        Wizard w3 = new Wizard();
        w3.setId(3L);
        w3.setName("Neville Longbottom");

        given(wizardRepository.findById(3L)).willReturn(Optional.empty());

        // When & Then
        assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.assignArtifact(3L,"123456");
        });
    }

    @Test
    void testAssignArtifactErrorWithNonExistentArtifactId(){
        // Given
        Artifact a = new Artifact();
        a.setId("123456");

        Wizard w2 = new Wizard();
        w2.setId(2L);
        w2.setName("Harry Porter");
        w2.addArtifact(a);

        Wizard w3 = new Wizard();
        w3.setId(3L);
        w3.setName("Neville Longbottom");

        given(artifactRepository.findById("654321")).willReturn(Optional.empty());
        given(wizardRepository.findById(3L)).willReturn(Optional.of(w3));

        // When & Then
        assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.assignArtifact(3L,"654321");
        });
    }


}