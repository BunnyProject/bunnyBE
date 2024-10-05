package bunny.backend.save.domain;

import bunny.backend.bunny.domain.Category;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "save")
public class Save {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "saving_chance")
    int savingChance;

    @Column(name = "saving_day")
    LocalDate savingDay;

    @Column(name = "saving_price")
    double savingPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",unique = true)
    Category category;
}
