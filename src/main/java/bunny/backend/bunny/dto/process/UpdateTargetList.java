package bunny.backend.bunny.dto.process;

public record UpdateTargetList(
        Long categoryId,
        Long targetAmount,
        Long onePrice

) {
}
