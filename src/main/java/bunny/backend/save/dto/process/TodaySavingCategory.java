package bunny.backend.save.dto.process;

public record TodaySavingCategory(Long categoryId,String categoryName,
                                  double totalSavingChance,double totalSavingCategoryMoney) {

}
