package danny.leetcode_study_app_backend.controller;

import danny.leetcode_study_app_backend.entity.ListEntity;
import danny.leetcode_study_app_backend.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders/{folderId}/lists")
@CrossOrigin(origins = "*")
public class ListController {

    @Autowired
    private ListService listService;

    // Create a new list in a specific folder
    @PostMapping
    public ResponseEntity<ListEntity> createList(
            @PathVariable Long folderId, @RequestBody ListEntity list) {
        ListEntity savedList = listService.createList(folderId, list);
        return ResponseEntity.ok(savedList);
    }

    // Get all lists for a specific folder
    @GetMapping
    public ResponseEntity<List<ListEntity>> getListsByFolderId(@PathVariable Long folderId) {
        List<ListEntity> lists = listService.getListsByFolderId(folderId);
        return ResponseEntity.ok(lists);
    }

    // Get list by ID
    @GetMapping("/{id}")
    public ResponseEntity<ListEntity> getListById(@PathVariable Long folderId, @PathVariable Long id) {
        ListEntity list = listService.getListById(id);
        if (list.getFolder().getId().equals(folderId)) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update an existing list
    @PutMapping("/{id}")
    public ResponseEntity<ListEntity> updateList(
            @PathVariable Long folderId, @PathVariable Long id, @RequestBody ListEntity listDetails) {
        ListEntity updatedList = listService.updateList(id, listDetails);
        return ResponseEntity.ok(updatedList);
    }

    // Delete a list
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteList(@PathVariable Long folderId, @PathVariable Long id) {
        listService.deleteList(id);
        return ResponseEntity.noContent().build();
    }

    // Search lists by name prefix
    @GetMapping("/search")
    public ResponseEntity<List<ListEntity>> searchListsByPrefix(
            @PathVariable Long folderId, @RequestParam String prefix) {
        List<ListEntity> lists = listService.searchListsByNameAndFolder(folderId, prefix);
        return ResponseEntity.ok(lists);
    }

    // Sort lists by name (A-Z)
    @GetMapping("/sort/name")
    public ResponseEntity<List<ListEntity>> sortListsByNameAsc(@PathVariable Long folderId) {
        List<ListEntity> lists = listService.getListsSortedByNameAsc(folderId);
        return ResponseEntity.ok(lists);
    }

    // Sort lists by edit date (most recent first)
    @GetMapping("/sort/editDate")
    public ResponseEntity<List<ListEntity>> sortListsByEditDateDesc(@PathVariable Long folderId) {
        List<ListEntity> lists = listService.getListsSortedByEditDateDesc(folderId);
        return ResponseEntity.ok(lists);
    }
}
