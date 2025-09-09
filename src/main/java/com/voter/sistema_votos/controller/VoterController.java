package com.voter.sistema_votos.controller;

import com.voter.sistema_votos.modelo.Voter;
import com.voter.sistema_votos.Service.VoterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voters")
public class VoterController {

    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @PostMapping
    public ResponseEntity<Voter> createVoter(@RequestBody Voter voter) {
        try {
            Voter newVoter = voterService.createVoter(voter);
            return new ResponseEntity<>(newVoter, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Voter>> getAllVoters() {
        List<Voter> voters = voterService.getAllVoters();
        return new ResponseEntity<>(voters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voter> getVoterById(@PathVariable Long id) {
        return voterService.getVoterById(id)
                .map(voter -> new ResponseEntity<>(voter, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoter(@PathVariable Long id) {
        voterService.deleteVoter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}