package bunny.backend.save.dto.request;

import java.time.LocalDate;

public record SavingMoneyRequest(Long categoryId, String CategoryName,String detail,LocalDate savingDay, double savingPrice) {
}
