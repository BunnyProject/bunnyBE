package bunny.backend.bunny.dto.request;

import bunny.backend.bunny.dto.process.UpdateTargetList;

import java.util.List;

public record UpdateMonthTargetRequest(
        Long targetId,
        Long totalTargetAmount,
        List<UpdateTargetList> updateTargetList
) {
}
