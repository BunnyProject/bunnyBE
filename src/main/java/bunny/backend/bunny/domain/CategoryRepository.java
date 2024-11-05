package bunny.backend.bunny.domain;

import bunny.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByMemberId(Long memberId);

    Optional<Category> findByMemberAndCategoryName(Member member, String categoryName);
}
