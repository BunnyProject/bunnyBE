package bunny.backend.bunny.dto.response;

import bunny.backend.bunny.dto.process.TargetList;

import java.util.List;

public record MonthTargetResponse(List<TargetList> targetList) {
}
