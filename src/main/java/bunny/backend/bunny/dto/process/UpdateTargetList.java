package bunny.backend.bunny.dto.process;

import java.util.List;

public record UpdateTargetList(
        Long categoryId,
        List<TargetList> targetList
) {
}
