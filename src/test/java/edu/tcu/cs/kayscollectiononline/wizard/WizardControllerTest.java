package edu.tcu.cs.kayscollectiononline.wizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.kayscollectiononline.system.StatusCode;
import edu.tcu.cs.kayscollectiononline.system.exception.ObjectNotFoundException;
import edu.tcu.cs.kayscollectiononline.wizard.Dto.WizardDto;
import edu.tcu.cs.kayscollectiononline.wizard.converter.WizardDtoToWizardConverter;
import edu.tcu.cs.kayscollectiononline.wizard.converter.WizardToWizardDtoConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "dev")
class WizardControllerTest {
    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Mocks for the services and converters
    @MockBean
    private WizardService wizardService;

    @MockBean
    private WizardToWizardDtoConverter wizardToWizardDtoConverter;

    @MockBean
    private WizardDtoToWizardConverter wizardDtoToWizardConverter;

    // Example data setup
    private WizardDto exampleWizardDto;
    private Wizard exampleWizard;

    @BeforeEach
    void setUp() {
        // Initialize example data here
        exampleWizardDto = new WizardDto(1L,"Jerry",2);
        exampleWizard = new Wizard();
        exampleWizard.setId(1L);

        // Configure the mock behavior
        when(wizardToWizardDtoConverter.convert(any(Wizard.class))).thenReturn(exampleWizardDto);
        when(wizardDtoToWizardConverter.convert(any(WizardDto.class))).thenReturn(exampleWizard);
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test if needed
    }

    @Test
    void findWizardById_Success() throws Exception {
        Long wizardId = 1L;
        when(wizardService.findById(wizardId)).thenReturn(exampleWizard);

        mockMvc.perform(get(baseUrl+"/wizards/" + wizardId))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.data.name").value("Jerry"));
    }


    @Test
    void findWizardById_NotFound() throws Exception {
        Long wizardId = 1L;
        when(wizardService.findById(wizardId)).thenThrow(new ObjectNotFoundException("wizard",wizardId));

        mockMvc.perform(get(baseUrl + "/wizards/" + wizardId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND)) // Assuming ERROR is the code for failures
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    void addWizard_Success() throws Exception {
        when(wizardService.save(any(Wizard.class))).thenReturn(exampleWizard);

        mockMvc.perform(post(baseUrl + "/wizards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exampleWizardDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.data.name").value("Jerry"));
    }


    @Test
    void updateWizard_Success() throws Exception {
        Long wizardId = 1L;
        when(wizardService.update(eq(wizardId), any(Wizard.class))).thenReturn(exampleWizard);

        mockMvc.perform(put(baseUrl + "/wizards/" + wizardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exampleWizardDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.data.name").value("Jerry"));
    }

    @Test
    void updateWizard_NotFound() throws Exception {
        Long wizardId = 1L;
        when(wizardService.update(eq(wizardId), any(Wizard.class))).thenThrow(new ObjectNotFoundException("Wizard", wizardId));

        mockMvc.perform(put(baseUrl + "/wizards/" + wizardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exampleWizardDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteWizard_Success() throws Exception {
        String wizardId = "1";

        mockMvc.perform(delete(baseUrl + "/wizards/" + wizardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS));
    }

    @Test
    void deleteWizard_NotFound() throws Exception {
        String wizardId = "1";
        Mockito.doThrow(new ObjectNotFoundException("wizard",wizardId)).when(wizardService).delete(wizardId);

        mockMvc.perform(delete(baseUrl + "/wizards/" + wizardId))
                .andExpect(status().isNotFound());
    }



}