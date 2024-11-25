package bunny.backend.save.domain;

import bunny.backend.bunny.domain.Category;
import bunny.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaveRepository extends JpaRepository<Save,Long> {
    List<Save> findAllByMemberId(Long memberId);
    List<Save> findAllByMemberAndSavingDayBetween(Member member, LocalDate startInclusive, LocalDate endInclusive);
    @Query("SELECT s FROM Save s WHERE s.member = :member AND s.savingDay >= :targetDay AND s.savingDay < :nextDay")
    List<Save> findAllByMemberAndDate(
            @Param("member") Member member,
            @Param("targetDay") LocalDate targetDay,
            @Param("nextDay") LocalDate nextDay
    );

    List<Save> findByMemberIdAndCategoryIdAndSavingDay(Long memberId, Long categoryId, LocalDate savingDay);

    List<Save> findAllByMemberAndSavingDayBetweenOrderBySavingDayAsc(Member member, LocalDate start, LocalDate end);

}
