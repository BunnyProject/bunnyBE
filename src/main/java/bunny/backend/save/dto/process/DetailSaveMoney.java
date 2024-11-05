package bunny.backend.save.dto.process;

public record DetailSaveMoney(
        Long savingId,
        String categoryName,
        double savingPrice
) {

}
