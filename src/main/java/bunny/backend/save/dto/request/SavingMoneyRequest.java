package bunny.backend.save.dto.request;

public record SavingMoneyRequest(Long categoryId,String CategoryName, double savingPrice,Integer savingChance) {
}
