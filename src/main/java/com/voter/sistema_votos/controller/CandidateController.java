package com.voter.sistema_votos.controller;

import com.voter.sistema_votos.modelo.Candidate;
import com.voter.sistema_votos.Service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@Tag(name = "Candidatos", description = "Gestión de candidatos")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo candidato",
            description = "Crea un nuevo candidato para las votaciones.")
    @ApiResponse(responseCode = "201", description = "Candidato creado exitosamente.")
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        Candidate newCandidate = candidateService.createCandidate(candidate);
        return new ResponseEntity<>(newCandidate, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener la lista de candidatos",
            description = "Recupera una lista de todos los candidatos disponibles.")
    @ApiResponse(responseCode = "200", description = "Lista de candidatos recuperada.")
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalles de un candidato por ID",
            description = "Recupera la información de un candidato específico.")
    @ApiResponse(responseCode = "200", description = "Candidato encontrado.")
    @ApiResponse(responseCode = "404", description = "Candidato no encontrado.")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        return candidateService.getCandidateById(id)
                .map(candidate -> new ResponseEntity<>(candidate, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un candidato",
            description = "Elimina un candidato registrado por su ID.")
    @ApiResponse(responseCode = "204", description = "Candidato eliminado exitosamente.")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
