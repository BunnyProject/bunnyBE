package bunny.backend.bunny.service;

import bunny.backend.bunny.domain.Category;
import bunny.backend.bunny.domain.Target;
import bunny.backend.bunny.domain.TargetRepository;
import bunny.backend.bunny.dto.process.TargetList;
import bunny.backend.bunny.dto.request.MonthTargetRequest;
import bunny.backend.bunny.dto.response.MonthTargetResponse;
import bunny.backend.common.ApiResponse;
import bunny.backend.exception.BunnyException;
import bunny.backend.member.domain.Member;
import bunny.backend.member.domain.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                    findMember
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
                    category.getOnePrice(),
                    request.targetList().stream()
                            .filter(t -> t.categoryName().equals(category.getCategoryName()))
                            .findFirst().orElseThrow(() -> new BunnyException("해당 카테고리를 찾을 수 없어요.", HttpStatus.BAD_REQUEST))
                            .targetAmount()
            );
            targetListDto.add(targetDto);
        }

        targetRepository.save(target);
        return ApiResponse.success(new MonthTargetResponse(targetListDto));
    }
}
