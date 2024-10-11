package bunny.backend.save.domain;

import bunny.backend.bunny.domain.Category;
import bunny.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaveRepository extends JpaRepository<Save,Long> {
    List<Save> findAllByCategoryIn(List<Category> categories);
    List<Save> findAllByMemberAndStartBetween(Member member, LocalDate startInclusive, LocalDate endInclusive);

}
