package bunny.backend.bunny.dto.process;

public record TargetList(
        String categoryName,
        Long targetAmount,
        Long onePrice
) {

}
