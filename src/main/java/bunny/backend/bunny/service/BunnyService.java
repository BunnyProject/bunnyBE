package bunny.backend.bunny.service;

import bunny.backend.bunny.domain.Category;
import bunny.backend.bunny.domain.CategoryRepository;
import bunny.backend.bunny.domain.Target;
import bunny.backend.bunny.domain.TargetRepository;
import bunny.backend.bunny.dto.process.TargetList;
import bunny.backend.bunny.dto.process.UpdateTargetList;
import bunny.backend.bunny.dto.request.MonthTargetRequest;
import bunny.backend.bunny.dto.request.UpdateMonthTargetRequest;
import bunny.backend.bunny.dto.response.*;
import bunny.backend.common.ApiResponse;
import bunny.backend.exception.BunnyException;
import bunny.backend.member.domain.Member;
import bunny.backend.member.domain.MemberRepository;
import bunny.backend.salary.domain.Salary;
import bunny.backend.salary.domain.SalaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BunnyService {
    private final TargetRepository targetRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final SalaryRepository salaryRepository;

    // 이번달 목표 설정
    @Transactional
    public ApiResponse<MonthTargetResponse> monthTarget(Long memberId, MonthTargetRequest request) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        List<Category> targetList = new ArrayList<>();
        for (TargetList target : request.targetList()) {
            Category existingCategory = categoryRepository.findById(target.categoryId())
                    .orElseThrow(() -> new BunnyException("존재하지 않는 카테고리입니다.", HttpStatus.NOT_FOUND));

            existingCategory.setTargetAmount(target.targetAmount());
            existingCategory.setOnePrice(target.onePrice());

            targetList.add(existingCategory);
            categoryRepository.save(existingCategory);
        }

        Target target = new Target();
        target.setTotalTargetAmount(request.totalTargetAmount());
        target.setMember(findMember);

        List<TargetList> targetListDto = new ArrayList<>();
        for (Category category : targetList) {
            TargetList targetDto = new TargetList(
                    category.getId(),
                    category.getCategoryName(),
                    category.getTargetAmount(),
                    category.getOnePrice()
            );
            targetListDto.add(targetDto);
        }

        targetRepository.save(target);
        return ApiResponse.success(new MonthTargetResponse(target.getTotalTargetAmount(), targetListDto));
    }


    // 오늘의 버니 조회
    public ApiResponse<TodayResponse> todayBunny(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        Salary findSalary = salaryRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BunnyException("급여를 찾을 수 없어요.",HttpStatus.NOT_FOUND));

        double minMoney = findSalary.getMinMoney();
        LocalTime quttingTime = findMember.getQuittingTime();
        return ApiResponse.success(new TodayResponse(minMoney,quttingTime));
    }
    // 버니 홈 급여 조회
    public ApiResponse<HomeMoneyResponse> findHomeMoney(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        Salary findSalary = salaryRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BunnyException("급여를 찾을 수 없어요.",HttpStatus.NOT_FOUND));

        double minMoney = findSalary.getMinMoney();
        double hourMoney = findSalary.getHourMoney();
        double secondMoney = findSalary.getSecondMoney();
        return ApiResponse.success(new HomeMoneyResponse(minMoney,hourMoney,secondMoney));
    }

    // 한달 목표 수정
    @Transactional
    public ApiResponse<UpdateMonthTargetResponse> updateMonthTarget(Long memberId, UpdateMonthTargetRequest request) {

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        Target findTarget = targetRepository.findById(request.targetId())
                .orElseThrow(() -> new BunnyException("이번달 목표를 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        checkMemberRelationMonthTarget(findMember, findTarget);

        List<TargetList> updatedTargetList = new ArrayList<>();

        for (UpdateTargetList updateTarget : request.updateTargetList()) {

            Category existingCategory = categoryRepository.findById(updateTarget.categoryId())
                    .orElseThrow(() -> new BunnyException("존재하지 않는 카테고리입니다.", HttpStatus.NOT_FOUND));

            if (!Objects.equals(existingCategory.getMember().getId(), memberId)) {
                throw new BunnyException("해당 카테고리에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }

            TargetList target = updateTarget.targetList().get(0);
            existingCategory.setTargetAmount(target.targetAmount());
            existingCategory.setOnePrice(target.onePrice());
            existingCategory.setCategoryName(target.categoryName());

            categoryRepository.save(existingCategory);

            updatedTargetList.add(new TargetList(
                    existingCategory.getId(),
                    existingCategory.getCategoryName(),
                    existingCategory.getTargetAmount(),
                    existingCategory.getOnePrice()
            ));
        }

        findTarget.setTotalTargetAmount(request.totalTargetAmount());
        targetRepository.save(findTarget);

        return ApiResponse.success(new UpdateMonthTargetResponse(findTarget.getTotalTargetAmount(), updatedTargetList));
    }



    // 목표 삭제
    public ApiResponse<DeleteTargetResponse> deleteTarget(Long memberId, Long targetId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        Target findTarget = targetRepository.findById(targetId)
                .orElseThrow(() -> new BunnyException("이번달 목표를 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        checkMemberRelationMonthTarget(findMember, findTarget);

        targetRepository.deleteById(targetId);

        return ApiResponse.success(new DeleteTargetResponse("한달 목표가 삭제 됐습니다.", targetId));
    }

    private static void checkMemberRelationMonthTarget(Member findMember, Target findTarget) {
        if (!Objects.equals(findMember.getId(), findTarget.getMember().getId())) {
            throw new BunnyException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.FORBIDDEN);
        }
    }
}
