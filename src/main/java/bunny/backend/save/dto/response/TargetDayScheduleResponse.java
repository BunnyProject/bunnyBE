package bunny.backend.save.dto.response;

import bunny.backend.save.dto.process.DetailSaveMoney;

import java.util.List;

/**
 * 오늘 아낀 금액 ,오늘 항목별 지금끼지 아낀 금액 , 오늘 항목별 몇회 아꼈는지 total
 * 세부 아낀 금액, 항목 이름
 */
public record TargetDayScheduleResponse(
        List<DetailSaveMoney> detailSaveMoneyList,
        double totalSavingMoney,
        double totalSavingChance,
        double totalCategorySavingMoney


) {
}
