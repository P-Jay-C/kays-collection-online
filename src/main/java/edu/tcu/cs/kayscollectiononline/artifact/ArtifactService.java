package edu.tcu.cs.kayscollectiononline.artifact;
import edu.tcu.cs.kayscollectiononline.artifact.utils.IdWorker;
import edu.tcu.cs.kayscollectiononline.system.exception.ObjectNotFoundException;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ArtifactService {

    private  final ArtifactRepository artifactRepository;
    private final IdWorker idWorker;

    public ArtifactService(ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }


    @Observed(name = "artifact", contextualName = "findByIdService")
    public Artifact findById(String artifactId){
        return artifactRepository.findById(artifactId).orElseThrow(
                () -> new ObjectNotFoundException("artifact",artifactId)
        );
    }

    @Timed("findAllArtifactsService.time")
    public List<Artifact> findAll() {
        return artifactRepository.findAll();
    }

    public  Artifact save(Artifact newArtifact){
        newArtifact.setId(idWorker.nextId()+"");
        return this.artifactRepository.save(newArtifact);
    }

    public Artifact update(String artifactId, Artifact update){

        return artifactRepository.findById(artifactId)
                        .map(oldArtifact -> {
                            oldArtifact.setName(update.getName());
                            oldArtifact.setDescription(update.getDescription());
                            oldArtifact.setImageUrl(update.getImageUrl());

                            return this.artifactRepository.save(oldArtifact);
                        })
                .orElseThrow(()-> new ObjectNotFoundException("artifact",artifactId));
    }

    public void delete(String artifactId) {
        Artifact artifact = artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ObjectNotFoundException("artifact",artifactId));

        artifactRepository.deleteById(artifactId);
    }
}
