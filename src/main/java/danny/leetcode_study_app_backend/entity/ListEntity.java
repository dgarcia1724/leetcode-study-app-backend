package danny.leetcode_study_app_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String userId;

    private LocalDateTime editDate;

    private double confidencePercentage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
    private List<Problem> problems;

    // Set initial creation time as editDate
    @PrePersist
    protected void onCreate() {
        editDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        editDate = LocalDateTime.now();
    }

    public void updateConfidencePercentage() {
        double totalConfidence = problems.stream()
            .mapToDouble(Problem::getConfidencePercentage)
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

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }
}
