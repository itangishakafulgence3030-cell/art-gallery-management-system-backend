package kigali.gallery.rw.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "art_style")
public class ArtStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "artStyles")
    @JsonIgnore
    private List<Curator> curators = new ArrayList<>();

    public ArtStyle() {
    }

    public ArtStyle(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Curator> getCurators() {
        return curators;
    }

    public void setCurators(List<Curator> curators) {
        this.curators = curators;
    }
}
