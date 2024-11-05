package bunny.backend.save.dto.process;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record SaveMoney(
        Long savingId,
        String detail,
        String categoryName,
        Integer savingChance,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate savingDay,
        double savingPrice
) {
}
