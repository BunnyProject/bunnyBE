package bunny.backend.bunny.dto.process;

public record TargetList(
        Long categoryId,
        String categoryName,
        Long targetAmount,
        Long onePrice
) {

}
