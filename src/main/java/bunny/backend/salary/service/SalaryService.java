package bunny.backend.salary.service;

import bunny.backend.common.ApiResponse;
import bunny.backend.exception.BunnyException;
import bunny.backend.member.domain.Member;
import bunny.backend.member.domain.MemberRepository;
import bunny.backend.salary.domain.Salary;
import bunny.backend.salary.domain.SalaryRepository;
import bunny.backend.salary.dto.MoneyShowResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SalaryService {
    private final MemberRepository memberRepository;
    private final SalaryRepository salaryRepository;

    // 급여 조회
    public ApiResponse<MoneyShowResponse> showMoney(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        double monthMoney= findMember.getMonthMoney();
        double weekMoney = findMember.getMonthMoney() / 4.345;
        double dayMoney = weekMoney / 5;
        double hourMoney = dayMoney / 8;
        double minMoney = hourMoney / 60;
        double secondMoney = minMoney / 60;

        Salary salary = new Salary(
                monthMoney,
                weekMoney,
                dayMoney,
                hourMoney,
                minMoney,
                secondMoney
        );
        salaryRepository.save(salary);

        MoneyShowResponse response = new MoneyShowResponse(
                monthMoney,
                weekMoney,
                dayMoney,
                hourMoney,
                minMoney,
                secondMoney
        );
        return ApiResponse.success(response);
    }
}
