package edu.tcu.cs.kayscollectiononline.artifact.dto;

import edu.tcu.cs.kayscollectiononline.wizard.Dto.WizardDto;
import jakarta.validation.constraints.NotEmpty;

public record ArtifactDto(String id,
                          @NotEmpty(message = "name is required")
                          String name,
                          @NotEmpty(message = "description is required")
                          String description,
                          @NotEmpty(message = "imageUrl is required")
                          String imageUrl,
                          WizardDto owner) {
}
