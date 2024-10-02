package bunny.backend.salary.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary,Long> {
    Optional<Salary> findByMemberId(Long memberId);
}
