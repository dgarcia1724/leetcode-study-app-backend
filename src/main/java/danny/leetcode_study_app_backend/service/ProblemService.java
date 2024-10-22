package danny.leetcode_study_app_backend.service;

import danny.leetcode_study_app_backend.entity.Folder;
import danny.leetcode_study_app_backend.entity.ListEntity;
import danny.leetcode_study_app_backend.entity.Problem;
import danny.leetcode_study_app_backend.repository.FolderRepository;
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

    @Autowired
    private FolderRepository folderRepository;  // Add FolderRepository

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
        Problem savedProblem = problemRepository.save(problem);

        // Update list and folder confidence after creating a new problem
        updateListAndFolderConfidence(listId);

        return savedProblem;
    }

    public Problem updateProblem(Long id, Problem problemDetails) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));

        // Update problem fields
        problem.setName(problemDetails.getName());
        problem.setConfidencePercentage(problemDetails.getConfidencePercentage());
        problem.setUrl(problemDetails.getUrl());

        // Save the updated problem
        problemRepository.save(problem);

        // Update list and folder confidence
        updateListAndFolderConfidence(problem.getList().getId());

        return problem;
    }

    public void deleteProblem(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
        Long listId = problem.getList().getId(); // Get the associated list ID before deleting

        problemRepository.delete(problem);

        // Update list and folder confidence after deleting the problem
        updateListAndFolderConfidence(listId);
    }

    // New method to calculate and update list and folder confidence
    public void updateListAndFolderConfidence(Long listId) {
        // 1. Calculate the average confidence of problems in the list
        List<Problem> problems = problemRepository.findByListIdOrdered(listId);
        double totalConfidence = problems.stream().mapToDouble(Problem::getConfidencePercentage).sum();
        double averageConfidence = problems.isEmpty() ? 0 : totalConfidence / problems.size();

        // 2. Update list confidence
        ListEntity list = listRepository.findById(listId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));
        list.setConfidencePercentage(averageConfidence);
        listRepository.save(list);

        // 3. Calculate the average confidence of lists in the folder
        Folder folder = list.getFolder();
        if (folder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder not found");
        }

        List<ListEntity> lists = listRepository.findByFolderId(folder.getId());
        double totalListConfidence = lists.stream().mapToDouble(ListEntity::getConfidencePercentage).sum();
        double folderAverageConfidence = lists.isEmpty() ? 0 : totalListConfidence / lists.size();

        // 4. Update folder confidence
        folder.setConfidencePercentage(folderAverageConfidence);
        folderRepository.save(folder);
    }
}
