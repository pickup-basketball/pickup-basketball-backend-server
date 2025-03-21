package core.pickupbackend.match.fake;

import core.pickupbackend.match.domain.Match;
import core.pickupbackend.match.domain.MatchStatus;
import core.pickupbackend.match.application.out.MatchRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakeMatchRepository implements MatchRepository {

    private final Map<Long, Match> store = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    @Override
    public Match save(Match match) {
        if (match.getId() == null) {
            match.setId(sequence.getAndIncrement());
        }

        match.setCreatedAt(LocalDateTime.now());
        match.setUpdatedAt(LocalDateTime.now());
        store.put(match.getId(), match);
        return match;
    }

    @Override
    public Optional<Match> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Match> findAll(final Integer page, final Integer size) {
        return new ArrayList<>(store.values());
    }

    public List<Match> findByHostIdAndStatusOrderByCreatedAtDesc(Long hostId, MatchStatus status) {
        return store.values().stream()
                .filter(match -> Objects.equals(match.getHostId(), hostId) && match.getStatus() == status)
                .sorted(Comparator.comparing(Match::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public Match update(Long id, Match match) {
        if (!store.containsKey(match.getId())) {
            throw new NoSuchElementException("Match with ID " + match.getId() + " does not exist");
        }

        match.setUpdatedAt(LocalDateTime.now());
        store.put(match.getId(), match);

        return findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public List<Match> findByMemberId(final Long memberId) {
        return List.of();
    }

    @Override
    public Integer countAll() {
        return store.size();
    }

    @Override
    public List<String> findAllDistricts() {
        return store.values().stream().map(Match::getDistrict).toList();
    }

    public void clearStore() {
        store.clear();
        sequence.set(1L);
    }
}
