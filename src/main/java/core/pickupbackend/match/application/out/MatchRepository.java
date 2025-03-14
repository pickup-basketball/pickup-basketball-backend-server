package core.pickupbackend.match.application.out;

import core.pickupbackend.match.domain.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {

    Match save(Match match);

    Optional<Match> findById(Long matchId);

    List<Match> findAll(Integer page, Integer size);

    Match update(Long matchId, Match match);

    void deleteById(Long matchId);

    List<Match> findByMemberId(Long memberId);

    Integer countAll();

    List<String> findAllDistricts();
}
