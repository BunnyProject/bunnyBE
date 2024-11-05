package bunny.backend.save.dto.response;


public record SettingSaveIconResponse(
        Long firstCategoryId,
        String categoryName1,
        Long secondCategoryId,
        String categoryName2,
        Long otherCategoryId,
        String otherCategoryName

) {
}
