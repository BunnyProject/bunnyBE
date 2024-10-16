package bunny.backend.save.service;

import bunny.backend.bunny.domain.Category;
import bunny.backend.bunny.domain.CategoryRepository;
import bunny.backend.common.ApiResponse;
import bunny.backend.exception.BunnyException;
import bunny.backend.member.domain.Member;
import bunny.backend.member.domain.MemberRepository;
import bunny.backend.save.domain.Save;
import bunny.backend.save.domain.SaveRepository;
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

        Category firstCategory = new Category(request.categoryName1(), findMember);
        categoryRepository.save(firstCategory);
        Category secondCategory = new Category(request.categoryName2(), findMember);
        categoryRepository.save(secondCategory);

        Long firstCategoryId = firstCategory.getId();
        Long secondCategoryId = secondCategory.getId();

        SettingSaveIconResponse response = new SettingSaveIconResponse(
                firstCategoryId, secondCategoryId, firstCategory.getCategoryName(), secondCategory.getCategoryName()
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

        Save newSave = new Save(
                request.savingPrice(),
                category,
                request.savingDay(),
                request.savingChance()
        );

        saveRepository.save(newSave);

        List<SaveMoney> saveMoneyList = new ArrayList<>();
        SaveMoney saveMoney = new SaveMoney(
                newSave.getId(),
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

        List<Save> saveList = saveRepository.findAllByMemberAndStartBetween(findMember, startInclusive, endInclusive);
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
//    public List<ApiResponse<TargetDayScheduleResponse>> showTargetSchedule(Long memberId, LocalDate targetDay) {
//        Member findMember = memberRepository.findById(memberId)
//                .orElseThrow(() -> new BunnyException("회원을 찾을 수 없어요.", HttpStatus.NOT_FOUND));
//
//        LocalDate nextDay = targetDay.plusDays(1); // 다음 날 00:00 전까지
//        List<Save> list = saveRepository.findAllByMemberAndDate(findMember, targetDay, nextDay);
//        List<ApiResponse<TargetDayScheduleResponse>> saveList = new ArrayList<>();
//
//        // 카테고리별로 그룹화: categoryName을 기준으로 그룹화
//        Map<String, List<DetailSaveMoney>> groupedByCategory = list.stream()
//                .flatMap(save -> save.getDetailSaveMoneyList().stream())
//                .collect(Collectors.groupingBy(DetailSaveMoney::categoryName));
//
//        // 각 카테고리별로 처리
//        for (Map.Entry<String, List<DetailSaveMoney>> entry : groupedByCategory.entrySet()) {
//            String categoryName = entry.getKey();
//            List<DetailSaveMoney> categoryItems = entry.getValue();
//
//            // 총 아낀 금액 계산
//            double totalSavingMoney = categoryItems.stream()
//                    .mapToDouble(DetailSaveMoney::savingPrice)
//                    .sum();
//
//            // 횟수 계산
//            int totalSavingCount = categoryItems.size();
//
//            // 카테고리별 세부 항목 리스트 생성
//            List<TargetDayScheduleResponse> targetDayScheduleResponses = categoryItems.stream()
//                    .map(detail -> new TargetDayScheduleResponse(
//                            List.of(detail),
//                            detail.savingPrice(),
//                            1, // 각 항목에 대해 1회로 처리 (횟수는 각 항목당 1로 간주)
//                            totalSavingMoney // 카테고리별 총 금액
//                    ))
//                    .collect(Collectors.toList());
//
//            // ApiResponse로 감싸서 추가
//            saveList.add(new ApiResponse<>(targetDayScheduleResponses));
//        }
//
//        return saveList;
//    }
}