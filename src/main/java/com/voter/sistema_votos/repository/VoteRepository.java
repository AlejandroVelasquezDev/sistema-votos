package com.voter.sistema_votos.repository;

import com.voter.sistema_votos.modelo.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Long> {
}
