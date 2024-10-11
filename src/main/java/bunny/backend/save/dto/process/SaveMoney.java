package bunny.backend.save.dto.process;

public record SaveMoney(
        Long savingId,
        String categoryName,
        Integer savingChance,
        double savingPrice
) {
}
