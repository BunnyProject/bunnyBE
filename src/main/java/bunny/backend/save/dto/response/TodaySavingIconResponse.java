package bunny.backend.save.dto.response;

import bunny.backend.save.dto.process.TodaySavingCategory;
import java.util.List;

public record TodaySavingIconResponse(double todayTotalMoney, List<TodaySavingCategory> todaySavingCategoryList) {

}
