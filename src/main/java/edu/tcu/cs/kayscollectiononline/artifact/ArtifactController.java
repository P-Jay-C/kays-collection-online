package edu.tcu.cs.kayscollectiononline.artifact;

import edu.tcu.cs.kayscollectiononline.artifact.Dto.ArtifactDto;
import edu.tcu.cs.kayscollectiononline.artifact.converter.ArtifactDtoToArtifactConverter;
import edu.tcu.cs.kayscollectiononline.artifact.converter.ArtifactToArtifactDtoConverter;
import edu.tcu.cs.kayscollectiononline.system.Result;
import edu.tcu.cs.kayscollectiononline.system.StatusCode;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("${api.endpoint.base-url}/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;
    private final MeterRegistry meterRegistry;

    @Autowired
    private ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;
    @Autowired
    private ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;

    public ArtifactController(ArtifactService artifactService, MeterRegistry meterRegistry) {
        this.artifactService = artifactService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId){
         Artifact foundArtifact = this.artifactService.findById(artifactId);

         meterRegistry.counter("artifact.id."+artifactId).increment();
        ArtifactDto artifactDto = artifactToArtifactDtoConverter.convert(foundArtifact);

         return new Result(true, StatusCode.SUCCESS,"Find One Success",artifactDto);
    }

    @GetMapping
    public Result findAllArtifacts(){
        List<Artifact> foundArtifacts = artifactService.findAll();
        // Convert
        List<ArtifactDto> artifactDtos = foundArtifacts.stream().map(
                foundArtifact -> artifactToArtifactDtoConverter.convert(foundArtifact)
        ).collect(Collectors.toList());

        return new  Result(true, StatusCode.SUCCESS,"Find All Success", artifactDtos);
    }

    @PostMapping
    public Result addArtifacts(@Valid @RequestBody ArtifactDto artifactDto){
        Artifact artifact = artifactDtoToArtifactConverter.convert(artifactDto);

        Artifact savedArtifact = artifactService.save(artifact);

        ArtifactDto savedArtifactDto = artifactToArtifactDtoConverter.convert(savedArtifact);

      return new Result(true, StatusCode.SUCCESS,"Add Success", savedArtifactDto);
    }

    @PutMapping("/{artifactId}")
     public Result updateArtifact(@PathVariable String artifactId, @Valid @RequestBody ArtifactDto artifactDto){
       Artifact update =  artifactDtoToArtifactConverter.convert(artifactDto);
       Artifact updatedArtifact = artifactService.update(artifactId,update);

       ArtifactDto updatedArtifactDto = artifactToArtifactDtoConverter.convert(updatedArtifact);

       return new Result(true, StatusCode.SUCCESS, "Update Success", updatedArtifactDto);
     }

     @DeleteMapping("/{artifactId}")
    public Result deleteArtifact(@PathVariable String artifactId){
        artifactService.delete(artifactId);

        return  new Result(true, StatusCode.SUCCESS,"Delete Success");
     }
}
