// package danny.leetcode_study_app_backend.controller;

// import danny.leetcode_study_app_backend.entity.Folder;
// import danny.leetcode_study_app_backend.service.FolderService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/folders")
// public class FolderController {

//     @Autowired
//     private FolderService folderService;


//     // Create or update folder
//     @PostMapping
//     public ResponseEntity<Folder> createOrUpdateFolder(@RequestBody Folder folder) {
//         Folder savedFolder = folderService.saveFolder(folder);
//         return ResponseEntity.ok(savedFolder);
//     }

//     // Get folder by ID
//     @GetMapping("/{id}")
//     public ResponseEntity<Folder> getFolderById(@PathVariable Long id) {
//         Optional<Folder> folder = folderService.getFolderById(id);
//         return folder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     // Get all folders for a user
//     @GetMapping("/user/{userId}")
//     public ResponseEntity<List<Folder>> getFoldersByUserId(@PathVariable String userId) {
//         List<Folder> folders = folderService.getFoldersByUserId(userId);
//         return ResponseEntity.ok(folders);
//     }

//     // Delete a folder
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteFolder(@PathVariable Long id) {
//         folderService.deleteFolder(id);
//         return ResponseEntity.noContent().build();
//     }
// }
