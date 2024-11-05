package bunny.backend.save.dto.request;

import java.time.LocalDate;

public record SavingMoneyRequest(Long categoryId, String CategoryName, LocalDate savingDay, double savingPrice, Integer savingChance) {
}
