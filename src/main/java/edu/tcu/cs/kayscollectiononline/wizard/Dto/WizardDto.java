package edu.tcu.cs.kayscollectiononline.wizard.Dto;

import jakarta.validation.constraints.NotEmpty;

public record WizardDto(Long id,
                        @NotEmpty(message = "name can't be empty")
                        String name,
                        Integer numberOfArtifacts) {
}
