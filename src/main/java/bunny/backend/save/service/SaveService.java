package bunny.backend.save.service;

import bunny.backend.bunny.domain.Category;
import bunny.backend.bunny.domain.CategoryRepository;
import bunny.backend.common.ApiResponse;
import bunny.backend.exception.BunnyException;
import bunny.backend.member.domain.Member;
import bunny.backend.member.domain.MemberRepository;
import bunny.backend.save.domain.Save;
import bunny.backend.save.domain.SaveRepository;
import bunny.backend.save.dto.process.CategorySavingChance;
import bunny.backend.save.dto.process.DetailSaveMoney;
import bunny.backend.save.dto.process.SaveMoney;
import bunny.backend.save.dto.request.DeleteSaveMoneyRequest;
import bunny.backend.save.dto.request.SavingMoneyRequest;
import bunny.backend.save.dto.request.SettingSaveIconRequest;
import bunny.backend.save.dto.response.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class SaveService {
    private final CategoryRepository categoryRepository;
    private final SaveRepository saveRepository;
    private final MemberRepository memberRepository;

    @Transactional
    // 아끼기 항목 설정
    public ApiResponse<SettingSaveIconResponse> settingSavingIcon(Long memberId, SettingSaveIconRequest request) {

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        // 기타 항목 없으면 자동 생성되도록
        Category otherCategory = categoryRepository.findByMemberAndCategoryName(findMember, "기타")
                .orElseGet(() -> {
                    Category newOtherCategory = new Category("기타", findMember);
                    return categoryRepository.save(newOtherCategory);
                });

        Category firstCategory = new Category(request.categoryName1(), findMember);
        categoryRepository.save(firstCategory);
        Category secondCategory = new Category(request.categoryName2(), findMember);
        categoryRepository.save(secondCategory);

        Long firstCategoryId = firstCategory.getId();
        Long secondCategoryId = secondCategory.getId();

        SettingSaveIconResponse response = new SettingSaveIconResponse(
                firstCategoryId, firstCategory.getCategoryName(), secondCategoryId,secondCategory.getCategoryName(), otherCategory.getId(), otherCategory.getCategoryName()
        );

        return ApiResponse.success(response);
    }

    @Transactional
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

        // null일때 금액설정 날짜로 기본값 설정
        LocalDate savingDay = (request.savingDay() != null) ? request.savingDay() : LocalDate.now();

        List<Save> existSave = saveRepository.findByMemberIdAndCategoryIdAndSavingDay(memberId, category.getId(), savingDay);

        Save newSave = new Save(
                findMember,
                request.savingPrice(),
                request.detail(),
                category,
                request.savingDay(),
                existSave.isEmpty() ? 1 : (existSave.get(0).getSavingChance() + 1)
        );

        saveRepository.save(newSave);

        List<SaveMoney> saveMoneyList = new ArrayList<>();
        SaveMoney saveMoney = new SaveMoney(
                newSave.getId(),
                newSave.getDetail(),
                newSave.getCategory().getCategoryName(),
                newSave.getSavingChance(),
                newSave.getSavingDay(),
                newSave.getSavingPrice());

        saveMoneyList.add(saveMoney);

        return ApiResponse.success(new SavingMoneyResponse(saveMoneyList));
    }

    // 아끼기 추가한 금액 조회 (개발 api용)
    // 카테고리 이름과 매칭 되는지 확인 로직 필요할듯
    public ApiResponse<ShowSavingMoneyResponse> showSavingMoney(Long memberId, Long savingId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        List<Category> findCategory = categoryRepository.findByMemberId(memberId);
        if (findCategory.isEmpty()) {
            throw new BunnyException("항목을 찾을 수 없어요.", HttpStatus.NOT_FOUND);
        }
        Save findSave = saveRepository.findById(savingId)
                .orElseThrow(() -> new BunnyException("항목에 대한 아끼기 금액을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        List<SaveMoney> saveMoneyListDto = new ArrayList<>();

        SaveMoney saveMoneyDto = new SaveMoney(
                findSave.getId(),
                findCategory.getFirst().getCategoryName(),
                findSave.getDetail(),
                findSave.getSavingChance(),
                findSave.getSavingDay(),
                findSave.getSavingPrice()
        );
        saveMoneyListDto.add(saveMoneyDto);

        return ApiResponse.success(new ShowSavingMoneyResponse(saveMoneyListDto));
    }

    // 아끼기 금액 삭제 - 삭제하면 통계에서도 삭제
    @Transactional
    public ApiResponse<DeleteSaveMoneyResponse> deleteSaveMoney(Long memberId, Long savingId, DeleteSaveMoneyRequest request) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요", HttpStatus.NOT_FOUND));

        Save findSave = saveRepository.findById(savingId)
                .orElseThrow(() -> new BunnyException("항목에 대한 아끼기 금액을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        saveRepository.deleteById(savingId);

        return ApiResponse.success(new DeleteSaveMoneyResponse("아끼기가 삭제됐어요.", savingId));
    }

    // 먼슬리 일정 조회
    public List<ScheduleResponse> showMonthlySchedule(Long memberId, LocalDate startInclusive, LocalDate endInclusive) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요", HttpStatus.NOT_FOUND));

        List<Save> saveList = saveRepository.findAllByMemberAndSavingDayBetween(findMember, startInclusive, endInclusive);
        List<ScheduleResponse> scheduleList = new ArrayList<>();

        for (Save save : saveList) {
            scheduleList.add(new ScheduleResponse(
                    save.getId(),
                    save.getCategory().getCategoryName(),
                    save.getSavingChance(),
                    save.getSavingDay(),
                    save.getSavingPrice()
            ));
        }
        return scheduleList;
    }

    // 세부 일정 조회
    public ApiResponse<TargetDayScheduleResponse> showTargetSchedule(Long memberId, LocalDate targetDay) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));

        LocalDate nextDay = targetDay.plusDays(1); // 다음 날 00:00 전까지
        List<Save> saveList = saveRepository.findAllByMemberAndDate(findMember, targetDay, nextDay);

        List<DetailSaveMoney> detailSaveMoneyList = saveList.stream()
                .map(save -> new DetailSaveMoney(
                        save.getId(),
                        save.getCategory().getId(),
                        save.getDetail(),
                        save.getCategory().getCategoryName(),
                        save.getSavingPrice()
                ))
                .collect(Collectors.toList());

        // 카테고리별로 그룹화
        Map<Long, List<DetailSaveMoney>> groupedByCategoryId = detailSaveMoneyList.stream()
                .collect(Collectors.groupingBy(DetailSaveMoney::categoryId));

        List<CategorySavingChance> savingChanceList = new ArrayList<>();
        double totalSavingMoney = 0;

        for (Map.Entry<Long, List<DetailSaveMoney>> entry : groupedByCategoryId.entrySet()) {
            Long categoryId = entry.getKey();
            List<DetailSaveMoney> categoryItems = entry.getValue();

            double categoryTotalMoney = categoryItems.stream()
                    .mapToDouble(DetailSaveMoney::savingPrice)
                    .sum();

            totalSavingMoney += categoryTotalMoney;

            // 카테고리별 아낀 횟수 계산
            int totalSavingChance = categoryItems.size();

            // 카테고리별 CategorySavingChance
            savingChanceList.add(new CategorySavingChance(
                    categoryId,
                    categoryItems.get(0).categoryName(),
                    totalSavingChance
            ));
        }

        TargetDayScheduleResponse response = new TargetDayScheduleResponse(
                detailSaveMoneyList,
                totalSavingMoney,
                savingChanceList,
                totalSavingMoney
        );

        return ApiResponse.success(response);
    }

}