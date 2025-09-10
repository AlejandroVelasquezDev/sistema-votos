package com.voter.sistema_votos.controller;

import com.voter.sistema_votos.modelo.Vote;
import com.voter.sistema_votos.Service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/votes")
@Tag(name = "Votos", description = "Gestión de votaciones y estadísticas")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    @Operation(summary = "Emitir un voto",
            description = "Permite a un votante registrar un voto para un candidato específico.")
    @ApiResponse(responseCode = "201", description = "Voto registrado exitosamente.")
    @ApiResponse(responseCode = "400", description = "El votante ya emitio su voto.")
    public ResponseEntity<String> castVote(@RequestParam Long voterId, @RequestParam Long candidateId) {
        try {
            Vote newVote = voteService.castVote(voterId, candidateId);
            return new ResponseEntity<>("Registro exitoso", HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los votos",
            description = "Recupera una lista completa de todos los votos emitidos.")
    @ApiResponse(responseCode = "200", description = "Lista de votos recuperada.")
    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> votes = voteService.getAllVotes();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }

    @GetMapping("/statistics")
    @Operation(summary = "Obtener estadísticas de la votación",
            description = "Proporciona un resumen con el total de votos por candidato y el porcentaje.")
    @ApiResponse(responseCode = "200", description = "Estadísticas recuperadas exitosamente.")
    public ResponseEntity<Map<String, Object>> getVotingStatistics() {
        Map<String, Object> stats = voteService.getVotingStatistics();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}