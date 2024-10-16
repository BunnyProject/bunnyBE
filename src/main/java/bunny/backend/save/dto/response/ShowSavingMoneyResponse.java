package bunny.backend.save.dto.response;


import bunny.backend.save.dto.process.SaveMoney;

import java.util.List;

// 얘 자체를 리스트로 해서 뜨도록  변경해야함 ..
public record ShowSavingMoneyResponse(
        List<SaveMoney> saveMoneyList
) {
}
