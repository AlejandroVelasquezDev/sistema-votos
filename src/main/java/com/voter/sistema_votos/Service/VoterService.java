package com.voter.sistema_votos.Service;

import com.voter.sistema_votos.modelo.Voter;
import com.voter.sistema_votos.repository.VoterRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VoterService {

    private final VoterRepository voterRepository;

    public VoterService(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    public Optional<Voter> getVoterById(Long id) {
        return voterRepository.findById(id);
    }

    public Voter createVoter(Voter voter) {
        // Validación: Asegurar que el email no esté ya registrado
        if (voterRepository.findByEmail(voter.getEmail()) != null) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }
        return voterRepository.save(voter);
    }

    // Método para verificar si el email de un votante existe
    public Voter findVoterByEmail(String email) {
        return voterRepository.findByEmail(email);
    }

    public void deleteVoter(Long id) {
        voterRepository.deleteById(id);
    }
}