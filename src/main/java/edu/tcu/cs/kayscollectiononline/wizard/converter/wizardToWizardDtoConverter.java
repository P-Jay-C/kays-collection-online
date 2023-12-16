package edu.tcu.cs.kayscollectiononline.wizard.converter;

import edu.tcu.cs.kayscollectiononline.wizard.Dto.WizardDto;
import edu.tcu.cs.kayscollectiononline.wizard.Wizard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class wizardToWizardDtoConverter implements Converter<Wizard, WizardDto> {

    @Override
    public WizardDto convert(Wizard source) {
        return new WizardDto(source.getId(),
                source.getName(),
                source.getNumberOfArtifacts()
        );
    }
}
