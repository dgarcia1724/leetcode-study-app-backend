package danny.leetcode_study_app_backend.repository;

import danny.leetcode_study_app_backend.entity.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListRepository extends JpaRepository<ListEntity, Long> {

    // Fetch lists by folderId
    List<ListEntity> findByFolderId(Long folderId);

    // Case-insensitive search using ILIKE in PostgreSQL, filtered by folderId
    @Query("SELECT l FROM ListEntity l WHERE l.folder.id = :folderId AND l.name ILIKE CONCAT(:prefix, '%')")
    List<ListEntity> findByNameStartingWithAndFolderId(@Param("folderId") Long folderId, @Param("prefix") String prefix);

    // Sort lists by name (A-Z) for a specific folder
    @Query("SELECT l FROM ListEntity l WHERE l.folder.id = :folderId ORDER BY l.name ASC")
    List<ListEntity> findAllByFolderIdOrderByNameAsc(@Param("folderId") Long folderId);

    // Sort lists by editDate (most recent first) for a specific folder
    @Query("SELECT l FROM ListEntity l WHERE l.folder.id = :folderId ORDER BY l.editDate DESC")
    List<ListEntity> findAllByFolderIdOrderByEditDateDesc(@Param("folderId") Long folderId);
}
