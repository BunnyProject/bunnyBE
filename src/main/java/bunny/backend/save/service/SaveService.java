package bunny.backend.save.service;

import bunny.backend.bunny.domain.Category;
import bunny.backend.bunny.domain.CategoryRepository;
import bunny.backend.common.ApiResponse;
import bunny.backend.exception.BunnyException;
import bunny.backend.member.domain.Member;
import bunny.backend.member.domain.MemberRepository;
import bunny.backend.save.domain.Save;
import bunny.backend.save.domain.SaveRepository;
import bunny.backend.save.dto.process.SaveMoney;
import bunny.backend.save.dto.request.SavingMoneyRequest;
import bunny.backend.save.dto.request.SettingSaveIconRequest;
import bunny.backend.save.dto.response.SavingMoneyResponse;
import bunny.backend.save.dto.response.SettingSaveIconResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class SaveService {
    private final CategoryRepository categoryRepository;
    private final SaveRepository saveRepository;
    private final MemberRepository memberRepository;

    // 아끼기 항목 설정
    public ApiResponse<SettingSaveIconResponse> settingSavingIcon(Long memberId, SettingSaveIconRequest request) {

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        Category firstCategory = new Category(request.categoryName1(), findMember);
        categoryRepository.save(firstCategory);
        Category secondCategory = new Category(request.categoryName2(),findMember);
        categoryRepository.save(secondCategory);

        Long firstCategoryId = firstCategory.getId();
        Long secondCategoryId = secondCategory.getId();

        SettingSaveIconResponse response = new SettingSaveIconResponse(
                firstCategoryId, secondCategoryId, firstCategory.getCategoryName(), secondCategory.getCategoryName()
        );

        return ApiResponse.success(response);
    }

    // 아끼기 금액 설정
    public ApiResponse<SavingMoneyResponse> savingMoney(Long memberId, SavingMoneyRequest request) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        List<Category> findCategory = categoryRepository.findByMemberId(memberId);
        if (findCategory.isEmpty()) {
            throw new BunnyException("아끼기 항목을 찾을 수 없어요.", HttpStatus.NOT_FOUND);
        }

        Category category = findCategory.stream()
                .filter(cat -> cat.getId().equals(request.categoryId()))
                .findFirst()
                .orElseThrow(() -> new BunnyException("해당 카테고리를 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        Save newSave = new Save(
                request.savingPrice(),
                category,
                request.savingChance()
        );

        saveRepository.save(newSave);

        List<SaveMoney> saveMoneyList = new ArrayList<>();
            SaveMoney saveMoney = new SaveMoney(
                    newSave.getId(),
                    newSave.getCategory().getCategoryName(),
                    newSave.getSavingChance(),
                    newSave.getSavingPrice());

            saveMoneyList.add(saveMoney);

        return ApiResponse.success(new SavingMoneyResponse(saveMoneyList));
    }


}
