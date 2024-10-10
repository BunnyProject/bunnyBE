package bunny.backend.save.controller;

import bunny.backend.common.ApiResponse;
import bunny.backend.save.dto.request.SavingMoneyRequest;
import bunny.backend.save.dto.request.SettingSaveIconRequest;
import bunny.backend.save.dto.response.SavingMoneyResponse;
import bunny.backend.save.dto.response.SettingSaveIconResponse;
import bunny.backend.save.dto.response.ShowSavingMoneyResponse;
import bunny.backend.save.service.SaveService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SaveController {
    private final SaveService saveService;

    // 아끼기 항목 설정
    @PostMapping("/save/icon")
    public ApiResponse<SettingSaveIconResponse> settingSaveIcon(
            @RequestHeader("member-no")Long memberId, @RequestBody SettingSaveIconRequest request) {
        return saveService.settingSavingIcon(memberId,request);
    }
    // 아끼기 금액 설정
    @PostMapping("/save/money")
    public ApiResponse<SavingMoneyResponse> savingMoney(
            @RequestHeader("member-no")Long memberId,
            @RequestBody SavingMoneyRequest request
            ) {
        return saveService.savingMoney(memberId,request);
    }

    // 추가한 아끼기 금액 조회
    @GetMapping("/save/{savingId}")
    public ApiResponse<ShowSavingMoneyResponse> showSavingMoney(
            @RequestHeader("member-no")Long memberId,
            @PathVariable("savingId")Long savingId
    )
    {
        return saveService.showSavingMoney(memberId,savingId);
    }
}
