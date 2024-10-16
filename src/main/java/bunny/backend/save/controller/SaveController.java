package bunny.backend.save.controller;

import bunny.backend.common.ApiResponse;
import bunny.backend.save.dto.request.DeleteSaveMoneyRequest;
import bunny.backend.save.dto.request.SavingMoneyRequest;
import bunny.backend.save.dto.request.SettingSaveIconRequest;
import bunny.backend.save.dto.response.*;
import bunny.backend.save.service.SaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Tag(name = "아끼기 서비스 모음", description = "아끼기 관련한 API 입니다.")
@RestController
@AllArgsConstructor
public class SaveController {
    private final SaveService saveService;

    // 아끼기 항목 설정
    @PostMapping("/save/icon")
    @Operation(summary = "아끼기 항목 설정", description = "아끼기 항목을 설정하는 API입니다.")
    public ApiResponse<SettingSaveIconResponse> settingSaveIcon(
            @RequestHeader("member-no")Long memberId, @RequestBody SettingSaveIconRequest request) {
        return saveService.settingSavingIcon(memberId,request);
    }
    // 아끼기 금액 설정
    @PostMapping("/save/money")
    @Operation(summary = "아끼기 금액 설정", description = "아낀 금액을 설정하는 API입니다.")
    public ApiResponse<SavingMoneyResponse> savingMoney(
            @RequestHeader("member-no")Long memberId,
            @RequestBody SavingMoneyRequest request
            ) {
        return saveService.savingMoney(memberId,request);
    }

    // 추가한 아끼기 금액 조회
    @GetMapping("/save/{savingId}")
    @Operation(summary = "추가한 아끼기 금액 조회", description = "아낀 금액을 조회하는 API입니다.")
    public ApiResponse<ShowSavingMoneyResponse> showSavingMoney(
            @RequestHeader("member-no")Long memberId,
            @PathVariable("savingId")Long savingId
    )
    {
        return saveService.showSavingMoney(memberId,savingId);
    }

    // 아끼기 삭제
    @DeleteMapping("/save/{savingId}")
    @Operation(summary = "아낀 내역 삭제", description = "아낀 내역을 삭제하는 API입니다.")
    public ApiResponse<DeleteSaveMoneyResponse> deleteSaveMoney(
            @RequestHeader("member-no")Long memberId,
            @PathVariable("savingId")Long savingId,
            @RequestBody DeleteSaveMoneyRequest request
            ) {
        return saveService.deleteSaveMoney(memberId,savingId,request);
    }
    // 먼슬리 일정 조회
    @GetMapping("/save")
    @Operation(summary = "먼슬리 아끼기 조회", description = "한달 단위로 아낀 내역을 확인할 수 있는 API입니다.")
    public ApiResponse<List<ScheduleResponse>> showMonthlySchedule(
            @RequestHeader(value = "member-no", required = false) Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startInclusive,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endInclusive
    ) {
        List<ScheduleResponse> result = saveService.showMonthlySchedule(memberId, startInclusive, endInclusive);

        return ApiResponse.success(result);
    }
    // 상세 일정 조회

}
