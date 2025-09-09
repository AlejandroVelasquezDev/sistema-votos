package com.voter.sistema_votos.Service;

import com.voter.sistema_votos.modelo.Candidate;
import com.voter.sistema_votos.modelo.Voter;
import com.voter.sistema_votos.modelo.Vote;
import com.voter.sistema_votos.repository.CandidateRepository;
import com.voter.sistema_votos.repository.VoterRepository;
import com.voter.sistema_votos.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;

    public VoteService(VoteRepository voteRepository, VoterRepository voterRepository, CandidateRepository candidateRepository) {
        this.voteRepository = voteRepository;
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

    @Transactional
    public Vote castVote(Long voterId, Long candidateId) {
        // 1. Validar que el votante existe y no ha votado
        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() -> new IllegalArgumentException("Votante no encontrado."));

        if (voter.isHasVoted()) {
            throw new IllegalStateException("El votante ya ha emitido su voto.");
        }

        // 2. Validar que el candidato existe
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Candidato no encontrado."));

        // 3. Crear el voto
        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setCandidate(candidate);

        // 4. Actualizar el estado del votante
        voter.setHasVoted(true);
        voterRepository.save(voter);

        // 5. Incrementar los votos del candidato
        candidate.setVotes(candidate.getVotes() + 1);
        candidateRepository.save(candidate);

        // 6. Guardar el voto
        return voteRepository.save(vote);
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public Map<String, Object> getVotingStatistics() {
        List<Candidate> candidates = candidateRepository.findAll();
        long totalVotes = candidates.stream().mapToLong(Candidate::getVotes).sum();
        long totalVotersWhoVoted = voterRepository.findAll().stream().filter(Voter::isHasVoted).count();

        Map<String, Object> statistics = new HashMap<>();

        Map<String, Long> votesByCandidate = candidates.stream()
                .collect(Collectors.toMap(Candidate::getName, Candidate::getVotes));

        Map<String, String> percentageByCandidate = candidates.stream()
                .collect(Collectors.toMap(
                        Candidate::getName,
                        c -> String.format("%.2f%%", (double) c.getVotes() / totalVotes * 100)
                ));

        statistics.put("totalVotersWhoVoted", totalVotersWhoVoted);
        statistics.put("totalVotes", totalVotes);
        statistics.put("votesByCandidate", votesByCandidate);
        statistics.put("percentageByCandidate", percentageByCandidate);

        return statistics;
    }
}
