package com.voter.sistema_votos.controller;

import com.voter.sistema_votos.modelo.Vote;
import com.voter.sistema_votos.Service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<String> castVote(@RequestParam Long voterId, @RequestParam Long candidateId) {
        try {
            Vote newVote = voteService.castVote(voterId, candidateId);
            return new ResponseEntity<>("Registro exitoso", HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> votes = voteService.getAllVotes();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getVotingStatistics() {
        Map<String, Object> stats = voteService.getVotingStatistics();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}