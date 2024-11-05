package bunny.backend.save.domain;

import bunny.backend.bunny.domain.Category;
import bunny.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaveRepository extends JpaRepository<Save,Long> {
    List<Save> findAllByCategoryIn(List<Category> categories);
    List<Save> findAllByMemberAndSavingDayBetween(Member member, LocalDate startInclusive, LocalDate endInclusive);
    @Query("SELECT s FROM Save s WHERE  s.savingDay >= :targetDay AND s.savingDay < :nextDay")
    List<Save> findAllByMemberAndDate(Member member, LocalDate targetDay, LocalDate nextDay);

    List<Save> findByMemberIdAndCategoryIdAndSavingDay(Long memberId, Long categoryId, LocalDate savingDay);

}
