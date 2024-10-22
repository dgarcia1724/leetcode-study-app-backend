package danny.leetcode_study_app_backend.service;

import danny.leetcode_study_app_backend.entity.ListEntity;
import danny.leetcode_study_app_backend.entity.Problem;
import danny.leetcode_study_app_backend.repository.ListRepository;
import danny.leetcode_study_app_backend.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ListRepository listRepository;

    public List<Problem> getProblemsByListId(Long listId) {
        return problemRepository.findByListIdOrdered(listId);
    }

    public Problem getProblemById(Long id) {
        return problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
    }

    public Problem createProblem(Long listId, Problem problem) {
        ListEntity list = listRepository.findById(listId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));
        problem.setList(list);
        return problemRepository.save(problem);
    }

    public Problem updateProblem(Long id, Problem problemDetails) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
        problem.setName(problemDetails.getName());
        problem.setConfidencePercentage(problemDetails.getConfidencePercentage());
        problem.setUrl(problemDetails.getUrl());
        // Update other fields as necessary
        return problemRepository.save(problem);
    }

    public void deleteProblem(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
        problemRepository.delete(problem);
    }

    // Work to do:
    // 1. Update list and folder confidence percentage

    // Additional method to calculate list and folder confidence
    public void updateListAndFolderConfidence(Long listId) {
        // Logic to calculate and update list and folder confidence based on problems
    }
}
