package danny.leetcode_study_app_backend.controller;

import danny.leetcode_study_app_backend.entity.Folder;
import danny.leetcode_study_app_backend.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/{userId}/folders")
@CrossOrigin(origins = "*")
public class FolderController {

    @Autowired
    private FolderService folderService;

    // Create or update folder for a specific user
    @PostMapping
    public ResponseEntity<Folder> createOrUpdateFolder(
            @PathVariable String userId, @RequestBody Folder folder) {
        folder.setUserId(userId);  // Ensure the userId is set
        Folder savedFolder = folderService.saveFolder(folder);
        return ResponseEntity.ok(savedFolder);
    }

    // Get folder by ID for a specific user
    @GetMapping("/{id}")
    public ResponseEntity<Folder> getFolderById(
            @PathVariable String userId, @PathVariable Long id) {
        Optional<Folder> folder = folderService.getFolderById(id);
        if (folder.isPresent() && folder.get().getUserId().equals(userId)) {
            return ResponseEntity.ok(folder.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all folders for a specific user
    @GetMapping
    public ResponseEntity<List<Folder>> getFoldersByUserId(@PathVariable String userId) {
        List<Folder> folders = folderService.getFoldersByUserId(userId);
        return ResponseEntity.ok(folders);
    }

    // Delete a folder for a specific user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable String userId, @PathVariable Long id) {
        Optional<Folder> folder = folderService.getFolderById(id);
        if (folder.isPresent() && folder.get().getUserId().equals(userId)) {
            folderService.deleteFolder(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Search folders by prefix for a specific user
    @GetMapping("/search")
    public ResponseEntity<List<Folder>> searchFoldersByPrefix(
            @PathVariable String userId, @RequestParam String prefix) {
        List<Folder> folders = folderService.searchFoldersByPrefix(prefix, userId);
        return ResponseEntity.ok(folders);
    }

    // Sort folders by name (A-Z) for a specific user
    @GetMapping("/sort/name")
    public ResponseEntity<List<Folder>> sortFoldersByName(@PathVariable String userId) {
        List<Folder> folders = folderService.sortFoldersByName(userId);
        return ResponseEntity.ok(folders);
    }

    // Sort folders by editDate (most recent first) for a specific user
    @GetMapping("/sort/editDate")
    public ResponseEntity<List<Folder>> sortFoldersByEditDate(@PathVariable String userId) {
        List<Folder> folders = folderService.sortFoldersByEditDate(userId);
        return ResponseEntity.ok(folders);
    }
}
