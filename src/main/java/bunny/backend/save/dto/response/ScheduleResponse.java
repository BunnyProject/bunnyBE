package bunny.backend.save.dto.response;

import bunny.backend.save.dto.process.SaveMoney;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record ScheduleResponse(
        Long savingId,
        String categoryName,
        Integer savingChance,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate savingDay,
        double savingPrice
) {
}
