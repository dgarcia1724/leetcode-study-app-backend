package danny.leetcode_study_app_backend.service;

import danny.leetcode_study_app_backend.entity.Folder;
import danny.leetcode_study_app_backend.entity.ListEntity;
import danny.leetcode_study_app_backend.repository.FolderRepository;
import danny.leetcode_study_app_backend.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ListService {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private FolderRepository folderRepository;

    // Get all lists for a specific folder
    public List<ListEntity> getListsByFolderId(Long folderId) {
        return listRepository.findByFolderId(folderId);
    }

    // Get list by ID
    public ListEntity getListById(Long id) {
        return listRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));
    }

    // Create a new list in a specific folder
    public ListEntity createList(Long folderId, ListEntity list) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder not found"));
        list.setFolder(folder);

        // Save the new list
        ListEntity savedList = listRepository.save(list);

        // Update folder confidence after adding a new list
        updateFolderConfidence(folderId);

        return savedList;
    }

    // Update an existing list
    public ListEntity updateList(Long id, ListEntity listDetails) {
        ListEntity list = listRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));
        list.setName(listDetails.getName());
        // Update other fields as necessary
        return listRepository.save(list);
    }

    // Delete a list
    public void deleteList(Long id) {
        ListEntity list = listRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));
        Long folderId = list.getFolder().getId(); // Get folder ID before deleting the list

        listRepository.delete(list);

        // Update folder confidence after deleting a list
        updateFolderConfidence(folderId);
    }

    // New method to update folder confidence percentage
    private void updateFolderConfidence(Long folderId) {
        List<ListEntity> lists = listRepository.findByFolderId(folderId);
        double totalConfidence = lists.stream().mapToDouble(ListEntity::getConfidencePercentage).sum();
        double averageConfidence = lists.isEmpty() ? 0 : totalConfidence / lists.size();

        // Update folder confidence
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder not found"));
        folder.setConfidencePercentage(averageConfidence);
        folderRepository.save(folder);
    }

    // Search lists by name prefix within a specific folder
    public List<ListEntity> searchListsByNameAndFolder(Long folderId, String prefix) {
        return listRepository.findByNameStartingWithAndFolderId(folderId, prefix);
    }

    // Sort lists by name (A-Z) within a specific folder
    public List<ListEntity> getListsSortedByNameAsc(Long folderId) {
        return listRepository.findAllByFolderIdOrderByNameAsc(folderId);
    }

    // Sort lists by edit date (most recent first) within a specific folder
    public List<ListEntity> getListsSortedByEditDateDesc(Long folderId) {
        return listRepository.findAllByFolderIdOrderByEditDateDesc(folderId);
    }
}
