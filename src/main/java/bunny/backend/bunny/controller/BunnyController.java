package bunny.backend.bunny.controller;

import bunny.backend.bunny.dto.request.MonthTargetRequest;
import bunny.backend.bunny.dto.request.UpdateMonthTargetRequest;
import bunny.backend.bunny.dto.response.*;
import bunny.backend.bunny.service.BunnyService;
import bunny.backend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.server.RequestPath;
import org.springframework.web.bind.annotation.*;

@RestController
public class BunnyController {
    private final BunnyService bunnyService;

    public BunnyController(BunnyService bunnyService) {
        this.bunnyService = bunnyService;
    }

    // 한달 목표 설정
    @PostMapping("/bunny/target")
    @Operation(summary = "한달 목표 설정", description = "한달 아끼기 목표를 설정하는 api입니다.")
    public ApiResponse<MonthTargetResponse> monthTarget(
            @RequestHeader("member-no") Long memberId, @RequestBody MonthTargetRequest monthTargetRequest) {
        return bunnyService.monthTarget(memberId, monthTargetRequest);
    }

    // 오늘의 버니 조회
    @GetMapping("/bunny")
    @Operation(summary = "오늘의 버니 조회", description = "오늘의 버니를 조회하는 api입니다.")
    public ApiResponse<TodayResponse> todayBunny(
            @RequestHeader("member-no") Long memberId) {
        return bunnyService.todayBunny(memberId);
    }

    // 한달 목표 수정
    @PutMapping("/bunny")
    @Operation(summary = "한달 목표 수정", description = "한달 목푤르 수정하는 api입니다.")
    public ApiResponse<UpdateMonthTargetResponse> updateMonthTarget(
            @RequestHeader("member-no") Long memberId,
            @RequestBody UpdateMonthTargetRequest request) {
        return bunnyService.updateMonthTarget(memberId, request);
    }

    // 한달 목표 삭제
    @DeleteMapping("/bunny/{targetId}")
    @Operation(summary = "한달 목표 삭제", description = "한달 목표 삭제 api입니다.")
    public ApiResponse<DeleteTargetResponse> deleteTarget(
            @RequestHeader(name = "member-no", required = false) Long memberId,
            @PathVariable("targetId") Long targetId
    ) {
        return bunnyService.deleteTarget(memberId, targetId);
    }
    // 버니 홈 급여관련 조회
    @GetMapping("/bunny/homeMoney")
    @Operation(summary = "홈화면 급여 조회", description = "현재 얼마나 벌고 있는지 확인할 수 있는 api입니다.")
    public ApiResponse<HomeMoneyResponse> findHomeMoney(
            @RequestHeader("member-no") Long memberId) {
        return bunnyService.findHomeMoney(memberId);
    }

}
