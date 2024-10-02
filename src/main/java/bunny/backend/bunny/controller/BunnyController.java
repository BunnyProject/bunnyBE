package bunny.backend.bunny.controller;

import bunny.backend.bunny.dto.request.MonthTargetRequest;
import bunny.backend.bunny.dto.request.UpdateMonthTargetRequest;
import bunny.backend.bunny.dto.response.DeleteTargetResponse;
import bunny.backend.bunny.dto.response.MonthTargetResponse;
import bunny.backend.bunny.dto.response.TodayResponse;
import bunny.backend.bunny.dto.response.UpdateMonthTargetResponse;
import bunny.backend.bunny.service.BunnyService;
import bunny.backend.common.ApiResponse;
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
    public ApiResponse<MonthTargetResponse> monthTarget(
            @RequestHeader("member-no") Long memberId, @RequestBody MonthTargetRequest monthTargetRequest) {
        return bunnyService.monthTarget(memberId, monthTargetRequest);
    }

    // 오늘의 버니 조회 : 현재 퇴근시간만 보이도록 설정해놓음
    @GetMapping("/bunny")
    public ApiResponse<TodayResponse> todayBunny(
            @RequestHeader("member-no") Long memberId) {
        return bunnyService.todayBunny(memberId);
    }

    // 한달 목표 수정
    @PutMapping("/bunny")
    public ApiResponse<UpdateMonthTargetResponse> updateMonthTarget(
            @RequestHeader("member-no") Long memberId,
            @RequestBody UpdateMonthTargetRequest request) {
        return bunnyService.updateMonthTarget(memberId, request);
    }

    // 한달 목표 삭제
    @DeleteMapping("/bunny/{targetId}")
    public ApiResponse<DeleteTargetResponse> deleteTarget(
            @RequestHeader(name = "member-no", required = false) Long memberId,
            @PathVariable("targetId") Long targetId
    ) {
        return bunnyService.deleteTarget(memberId, targetId);
    }

}
