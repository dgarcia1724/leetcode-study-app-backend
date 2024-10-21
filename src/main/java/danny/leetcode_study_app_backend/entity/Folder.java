package danny.leetcode_study_app_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String userId;

    private LocalDateTime editDate;

    private double confidencePercentage;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<ListEntity> lists;

    // Set initial creation time as editDate
    @PrePersist
    protected void onCreate() {
        editDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        editDate = LocalDateTime.now();
    }

    // Getters and Setters
    // Add methods to update confidencePercentage based on lists' confidence values
    public void updateConfidencePercentage() {
        double totalConfidence = lists.stream()
            .mapToDouble(ListEntity::getConfidencePercentage)
            .average()
            .orElse(0.0);
        this.confidencePercentage = totalConfidence;
    }

    // other getters and setters
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public double getConfidencePercentage() {
        return confidencePercentage;
    }

    public void setConfidencePercentage(double confidencePercentage) {
        this.confidencePercentage = confidencePercentage;
    }

    public List<ListEntity> getLists() {
        return lists;
    }

    public void setLists(List<ListEntity> lists) {
        this.lists = lists;
    }
}
