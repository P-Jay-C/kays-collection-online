package edu.tcu.cs.kayscollectiononline.wizard;

import edu.tcu.cs.kayscollectiononline.system.Result;
import edu.tcu.cs.kayscollectiononline.system.StatusCode;
import edu.tcu.cs.kayscollectiononline.wizard.Dto.WizardDto;
import edu.tcu.cs.kayscollectiononline.wizard.converter.WizardDtoToWizardConverter;
import edu.tcu.cs.kayscollectiononline.wizard.converter.WizardToWizardDtoConverter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/wizards")
public class WizardController {

    private final WizardService wizardService;
    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;
    private  final  WizardDtoToWizardConverter wizardDtoToWizardConverter;

    public WizardController(WizardService wizardService, WizardToWizardDtoConverter wizardToWizardDtoConverter, WizardDtoToWizardConverter wizardDtoToWizardConverter) {
        this.wizardService = wizardService;
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
        this.wizardDtoToWizardConverter = wizardDtoToWizardConverter;
    }

    @GetMapping("/{wizardId}")
    public Result findWizardById(@PathVariable Long wizardId){

        Wizard wizard = wizardService.findById(wizardId);
        WizardDto wizardDto = wizardToWizardDtoConverter.convert(wizard);

        return new Result(true, StatusCode.SUCCESS,"Find Success", wizardDto);
    }



    @GetMapping
    public Result findAllWizards(){

        List<Wizard> wizards = wizardService.findAll();

        List<WizardDto> wizardDtos = wizards.stream().map(
                wizardToWizardDtoConverter::convert
        ).toList();

        return new Result(true, StatusCode.SUCCESS,"Find All Success", wizardDtos);
    }

    @PostMapping
    public Result addWizard(@Valid @RequestBody WizardDto wizardDto){

        Wizard wizard = wizardDtoToWizardConverter.convert(wizardDto);
        Wizard savedWizard = wizardService.save(wizard);

        WizardDto savedWizardDto = wizardToWizardDtoConverter.convert(savedWizard);

        return new Result(true, StatusCode.SUCCESS,"Add Success", savedWizardDto);
    }

    @PutMapping("/{wizardId}")
    public Result updateWizard(@PathVariable Long wizardId, @Valid @RequestBody WizardDto wizardDto){

        Wizard update = wizardDtoToWizardConverter.convert(wizardDto);

        Wizard updatedwizard = wizardService.update(wizardId, update);

        WizardDto updatedWizardDto = wizardToWizardDtoConverter.convert(updatedwizard);

        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedWizardDto);
    }

    @DeleteMapping("/{wizardId}")
    public Result deleteWizard(@PathVariable String wizardId){

        wizardService.delete(wizardId);

        return  new Result(true, StatusCode.SUCCESS,"Delete Success");
    }
}
