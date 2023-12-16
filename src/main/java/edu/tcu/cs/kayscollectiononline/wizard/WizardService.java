package edu.tcu.cs.kayscollectiononline.wizard;

import edu.tcu.cs.kayscollectiononline.artifact.utils.IdWorker;
import edu.tcu.cs.kayscollectiononline.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService {
    private final WizardRepository wizardRepository;
    private final IdWorker idWorker;

    public WizardService(WizardRepository wizardRepository, IdWorker idWorker) {
        this.wizardRepository = wizardRepository;
        this.idWorker = idWorker;
    }

    public Wizard findById(Long wizardId){
        return  wizardRepository.findById(wizardId).orElseThrow(
                () -> new ObjectNotFoundException("wizard",wizardId)
        );
    }

    public List<Wizard> findAll(){
        return wizardRepository.findAll();
    }

    public Wizard save(Wizard wizard) {
        wizard.setId(idWorker.nextId());

        return wizardRepository.save(wizard);
    }

    public Wizard update(Long wizardId, Wizard update) {
        return wizardRepository.findById(wizardId)
                .map(oldWizard -> {
                    oldWizard.setName(update.getName());

                    return this.wizardRepository.save(oldWizard);
                })
                .orElseThrow(()-> new ObjectNotFoundException("wizard",wizardId));
        
    }

    public void delete(String wizardId) {
        Wizard wizard = wizardRepository.findById(Long.valueOf(wizardId))
                .orElseThrow(() -> new ObjectNotFoundException("wizard",wizardId));

        wizard.removeAllArtifacts();

        wizardRepository.deleteById(Long.valueOf(wizardId));
    }
}