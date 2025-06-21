/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.Lecon;
import com.techwave.elasticsearch.service.LeconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lecons")
public class LeconController {

    @Autowired
    private LeconService leconService;

   /* @GetMapping
    public List<Lecon> getAllLecons() {
        return leconService.getAllLecons();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Lecon> getLeconById(@PathVariable String id) {
        Optional<Lecon> lecon = leconService.getLeconById(id);
        return lecon.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lecon> addLecon(@RequestBody Lecon lecon) throws IOException {
        Lecon saved = leconService.addLecon(lecon);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecon> updateLecon(@PathVariable String id, @RequestBody Lecon lecon) throws IOException {
        Lecon updated = leconService.updateLecon(id, lecon);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecon(@PathVariable String id) throws IOException {
        leconService.deleteLecon(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Lecon> searchLecons(@RequestParam String q) {
        return leconService.searchLecons(q);
    }
}