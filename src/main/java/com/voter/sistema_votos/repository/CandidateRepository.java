package com.voter.sistema_votos.repository;

import com.voter.sistema_votos.modelo.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate,Long> {
}
