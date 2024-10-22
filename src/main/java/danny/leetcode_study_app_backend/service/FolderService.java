package danny.leetcode_study_app_backend.service;

import danny.leetcode_study_app_backend.entity.Folder;
import danny.leetcode_study_app_backend.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    // Create or update folder
    public Folder saveFolder(Folder folder) {
        return folderRepository.save(folder);
    }

    // Get folder by ID
    public Optional<Folder> getFolderById(Long id) {
        return folderRepository.findById(id);
    }

    // Get all folders for a user
    public List<Folder> getFoldersByUserId(String userId) {
        return folderRepository.findByUserId(userId);
    }

    // Delete a folder
    public void deleteFolder(Long id) {
        folderRepository.deleteById(id);
    }
}
