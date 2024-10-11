package bunny.backend.save.dto.process;

import java.time.LocalDate;

public record SaveMoney(
        Long savingId,
        String categoryName,
        Integer savingChance,
        LocalDate savingDay,
        double savingPrice
) {
}
