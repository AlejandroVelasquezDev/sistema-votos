package com.voter.sistema_votos.controller;

import com.voter.sistema_votos.modelo.Voter;
import com.voter.sistema_votos.Service.VoterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voters")
@Tag(name = "Votantes", description = "Gestión de votantes")
public class VoterController {

    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @Operation(summary = "Registrar un nuevo votante",
            description = "Crea un nuevo votante con un nombre y correo electrónico único.")
    @ApiResponse(responseCode = "201", description = "Votante creado exitosamente.")
    @ApiResponse(responseCode = "400", description = "El correo electrónico ya está en uso.")
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
    @Operation(summary = "Obtener la lista de votantes",
            description = "Recupera una lista de todos los votantes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de votantes recuperada.")
    public ResponseEntity<List<Voter>> getAllVoters() {
        List<Voter> voters = voterService.getAllVoters();
        return new ResponseEntity<>(voters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalles de un votante por ID",
            description = "Recupera la información de un votante específico.")
    @ApiResponse(responseCode = "200", description = "Votante encontrado.")
    @ApiResponse(responseCode = "404", description = "Votante no encontrado.")
    public ResponseEntity<Voter> getVoterById(@PathVariable Long id) {
        return voterService.getVoterById(id)
                .map(voter -> new ResponseEntity<>(voter, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")

    @Operation(summary = "Eliminar un votante",
            description = "Elimina un votante registrado por su ID.")
    @ApiResponse(responseCode = "204", description = "Votante eliminado exitosamente.")
    public ResponseEntity<Void> deleteVoter(@PathVariable Long id) {
        voterService.deleteVoter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}