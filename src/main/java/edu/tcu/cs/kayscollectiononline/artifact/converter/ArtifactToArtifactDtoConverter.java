package edu.tcu.cs.kayscollectiononline.artifact.converter;
import edu.tcu.cs.kayscollectiononline.artifact.Artifact;
import edu.tcu.cs.kayscollectiononline.artifact.Dto.ArtifactDto;
import edu.tcu.cs.kayscollectiononline.wizard.converter.wizardToWizardDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDtoConverter implements Converter<Artifact, ArtifactDto> {

    private  final wizardToWizardDtoConverter wizardToWizardDtoConverter;

    public ArtifactToArtifactDtoConverter(edu.tcu.cs.kayscollectiononline.wizard.converter.wizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }

    @Override
    public ArtifactDto convert(Artifact source) {
        return new ArtifactDto(
                source.getId(),
                source.getName(),
                source.getDescription(),
                source.getImageUrl(),
                source.getOwner() != null ? this.wizardToWizardDtoConverter.convert(source.getOwner()) : null
        );
    }
}
