package bunny.backend.save.dto.response;

import bunny.backend.save.dto.process.SaveMoney;

import java.util.List;

public record SavingMoneyResponse(List<SaveMoney> saveMoneyList) {

}
