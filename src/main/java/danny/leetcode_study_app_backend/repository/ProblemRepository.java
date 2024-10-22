package danny.leetcode_study_app_backend.repository;

import danny.leetcode_study_app_backend.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("SELECT p FROM Problem p WHERE p.list.id = :listId ORDER BY p.confidencePercentage ASC, p.editDate ASC")
    List<Problem> findByListIdOrdered(@Param("listId") Long listId);
}
