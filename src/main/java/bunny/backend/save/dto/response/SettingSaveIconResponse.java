package bunny.backend.save.dto.response;


public record SettingSaveIconResponse(
        Long firstCategoryId,
        Long secondCategoryId,
        String categoryName1,
        String categoryName2
) {
}
