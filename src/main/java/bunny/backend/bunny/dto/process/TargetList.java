package bunny.backend.bunny.dto.process;

public record TargetList(
        Long memberId,
        String categoryName,
        Long targetAmount,
        Long onePrice
) {

}
