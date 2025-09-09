package com.voter.sistema_votos.repository;

import com.voter.sistema_votos.modelo.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter,Long> {
    Voter findByEmail(String email);
}
