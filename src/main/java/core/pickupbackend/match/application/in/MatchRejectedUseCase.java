package core.pickupbackend.match.application.in;

import core.pickupbackend.match.domain.Participation;
import core.pickupbackend.match.dto.request.UpdateParticipationRequest;

public interface MatchRejectedUseCase {
    Participation matchRejected(UpdateParticipationRequest updateParticipationRequest);
}
