package bunny.backend.salary.controller;

import bunny.backend.common.ApiResponse;
import bunny.backend.salary.dto.MoneyShowResponse;
import bunny.backend.salary.service.SalaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "급여 조회 서비스 모음", description = "사용자 급여조회 API 입니다.")
public class SalaryController {
    private final SalaryService salaryService;
    @GetMapping("/user/money")
    @Operation(summary = "사용자 급여조회", description = "사용자의 급여를 조회하는 api입니다.")
    public ApiResponse<MoneyShowResponse> getMoney(@RequestHeader(name = "member-no")Long memberId) {
        return salaryService.showMoney(memberId);
    }
}
