package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.Instructeur;
import com.techwave.elasticsearch.service.InstructeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instructeurs")
public class InstructeurController {

    @Autowired
    private InstructeurService instructeurService;

    /*@GetMapping
    public List<Instructeur> getAllInstructeurs() {
        return instructeurService.getAllInstructeurs();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Instructeur> getInstructeurById(@PathVariable String id) {
        Optional<Instructeur> instructeur = instructeurService.getInstructeurById(id);
        return instructeur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Instructeur> addInstructeur(@RequestBody Instructeur instructeur) throws IOException {
        Instructeur saved = instructeurService.addInstructeur(instructeur);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructeur> updateInstructeur(@PathVariable String id, @RequestBody Instructeur instructeur) throws IOException {
        Instructeur updated = instructeurService.updateInstructeur(id, instructeur);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructeur(@PathVariable String id) throws IOException {
        instructeurService.deleteInstructeur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Instructeur> searchInstructeurs(@RequestParam String q) {
        return instructeurService.searchInstructeurs(q);
    }
}