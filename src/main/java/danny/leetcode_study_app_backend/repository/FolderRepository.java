package danny.leetcode_study_app_backend.repository;

import danny.leetcode_study_app_backend.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    // Fetch folders by userId
    List<Folder> findByUserId(String userId);

    // Case-insensitive search using ILIKE in PostgreSQL, filtered by userId
    @Query("SELECT f FROM Folder f WHERE f.userId = :userId AND f.name ILIKE CONCAT(:prefix, '%')")
    List<Folder> findByNameStartingWithAndUserId(@Param("prefix") String prefix, @Param("userId") String userId);

    // Sort folders by name (A-Z) for a specific user
    @Query("SELECT f FROM Folder f WHERE f.userId = :userId ORDER BY f.name ASC")
    List<Folder> findAllByUserIdOrderByNameAsc(@Param("userId") String userId);

    // Sort folders by editDate (most recent first) for a specific user
    @Query("SELECT f FROM Folder f WHERE f.userId = :userId ORDER BY f.editDate DESC")
    List<Folder> findAllByUserIdOrderByEditDateDesc(@Param("userId") String userId);
}
