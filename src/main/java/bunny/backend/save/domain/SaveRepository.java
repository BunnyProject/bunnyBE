package bunny.backend.save.domain;

import bunny.backend.bunny.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveRepository extends JpaRepository<Save,Long> {
    List<Save> findAllByCategoryIn(List<Category> categories);
}
