package bunny.backend.bunny.dto.response;

import bunny.backend.bunny.dto.process.TargetList;
import bunny.backend.bunny.dto.process.UpdateTargetList;

import java.util.List;

public record UpdateMonthTargetResponse(Long totalTargetAmount,List<TargetList> updateTargetLists) {
}