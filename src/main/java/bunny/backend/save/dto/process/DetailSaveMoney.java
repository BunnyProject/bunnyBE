package bunny.backend.save.dto.process;

public record DetailSaveMoney(
        Long savingId,
        Long categoryId,
        String categoryName,
        String detail,
        double savingPrice
) {

}
