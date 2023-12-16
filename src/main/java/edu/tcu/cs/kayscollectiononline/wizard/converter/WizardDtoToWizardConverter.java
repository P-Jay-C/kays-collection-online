package edu.tcu.cs.kayscollectiononline.wizard.converter;

import edu.tcu.cs.kayscollectiononline.wizard.Dto.WizardDto;
import edu.tcu.cs.kayscollectiononline.wizard.Wizard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardDtoToWizardConverter implements Converter<WizardDto, Wizard> {
    @Override
    public Wizard convert(WizardDto source) {

        Wizard wizard = new Wizard();

        wizard.setId(source.id());
        wizard.setName(source.name());

        return wizard;
    }
}
