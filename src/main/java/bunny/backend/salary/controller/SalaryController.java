package bunny.backend.salary.controller;

import bunny.backend.common.ApiResponse;
import bunny.backend.salary.dto.MoneyShowResponse;
import bunny.backend.salary.service.SalaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;
    @GetMapping("/user/money")
    public ApiResponse<MoneyShowResponse> getMoney(@RequestHeader(name = "member-no")Long memberId) {
        return salaryService.showMoney(memberId);
    }
}
