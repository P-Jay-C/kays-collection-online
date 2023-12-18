package edu.tcu.cs.kayscollectiononline.wizard;

import edu.tcu.cs.kayscollectiononline.artifact.Artifact;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Wizard implements Serializable {

    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Artifact> artifacts = new ArrayList<>();

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addArtifact(Artifact artifact) {
        artifact.setOwner(this);
        this.artifacts.add(artifact);
    }



    public Integer getNumberOfArtifacts() {
        return artifacts.size();
    }

    public void removeAllArtifacts() {
        if (artifacts != null) {
            artifacts.forEach(artifact -> artifact.setOwner(null));
            artifacts.clear();
        }
    }


    public void removeArtifact(Artifact artifact) {
        if (artifact != null && artifacts.contains(artifact)) {
            artifact.setOwner(null);
            artifacts.remove(artifact);
        }
    }

}
