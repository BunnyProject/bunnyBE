package bunny.backend.bunny.service;

import bunny.backend.bunny.domain.Category;
import bunny.backend.bunny.domain.Target;
import bunny.backend.bunny.domain.TargetRepository;
import bunny.backend.bunny.dto.process.TargetList;
import bunny.backend.bunny.dto.request.MonthTargetRequest;
import bunny.backend.bunny.dto.request.UpdateMonthTargetRequest;
import bunny.backend.bunny.dto.response.MonthTargetResponse;
import bunny.backend.bunny.dto.response.TodayResponse;
import bunny.backend.bunny.dto.response.UpdateMonthTargetResponse;
import bunny.backend.common.ApiResponse;
import bunny.backend.exception.BunnyException;
import bunny.backend.member.domain.Member;
import bunny.backend.member.domain.MemberRepository;
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

    // 이번달 목표 설정
    @Transactional
    public ApiResponse<MonthTargetResponse> monthTarget(Long memberId,MonthTargetRequest request) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        List<Category> targetList = new ArrayList<>();
        for (TargetList target : request.targetList()) {
            Category category = new Category(
                    target.categoryName(),
                    target.onePrice(),
                    findMember,
                    target.targetAmount()
            );
            targetList.add(category);
        }

        Target target = new Target();
        target.setTotalTargetAmount(request.totalTargetAmount());
        target.setTargetList(targetList);
        target.setMember(findMember);

        List<TargetList> targetListDto = new ArrayList<>();
        for (Category category : targetList) {
            TargetList targetDto = new TargetList(
                    category.getCategoryName(),
                    category.getTargetAmount(),
                    category.getOnePrice()
            );
            targetListDto.add(targetDto);
        }

        targetRepository.save(target);
        return ApiResponse.success(new MonthTargetResponse(targetListDto));
    }
    // 오늘의 버니 조회
    public ApiResponse<TodayResponse> todayBunny(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));
        LocalTime quttingTime = findMember.getQuittingTime();
        return ApiResponse.success(new TodayResponse(quttingTime));
    }
    // 버니 목표 수정
    public ApiResponse<UpdateMonthTargetResponse> updateMonthTarget(Long memberId,UpdateMonthTargetRequest request) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        Target findTarget = targetRepository.findById(request.targetId())
                .orElseThrow(() -> new BunnyException("이번달 목표를 찾을 수 없어요",HttpStatus.NOT_FOUND));
        // 해당 회원의 한달목표가 맞는지 확인
        checkMemberRelationMonthTarget(findMember,findTarget);

        findTarget.setTotalTargetAmount(request.totalTargetAmount());

        List<Category> updatedCategoryList = new ArrayList<>();
        for (TargetList target : request.targetList()) {
            Category updatedCategory = new Category(
                    target.categoryName(),
                    target.onePrice(),
                    findMember,
                    target.targetAmount()
            );
            updatedCategory.setTargetAmount(target.targetAmount());
            updatedCategoryList.add(updatedCategory);
        }

        findTarget.setTargetList(updatedCategoryList);

        targetRepository.save(findTarget);

        List<TargetList> targetListDto = new ArrayList<>();
        for (Category category : updatedCategoryList) {
            TargetList targetDto = new TargetList(
                    category.getCategoryName(),
                    category.getTargetAmount(),
                    category.getOnePrice()
            );
            targetListDto.add(targetDto);
        }

        return ApiResponse.success(new UpdateMonthTargetResponse( findTarget.getTotalTargetAmount(),targetListDto));
    }

    private static void checkMemberRelationMonthTarget(Member findMember, Target findTarget) {
        if (!Objects.equals(findMember.getId(), findTarget.getMember().getId())) {
            throw new BunnyException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.FORBIDDEN);
        }
    }
}
