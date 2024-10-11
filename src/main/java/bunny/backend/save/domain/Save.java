package bunny.backend.save.domain;

import bunny.backend.bunny.domain.Category;
import bunny.backend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "save")
@NoArgsConstructor
public class Save extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "saving_chance")
    Integer savingChance;

    @Column(name = "saving_day")
    LocalDate savingDay;

    @Column(name = "saving_price")
    double savingPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",unique = true)
    Category category;

    public Save(double savingPrice,Category category,LocalDate savingDay,Integer savingChance) {
        this.savingPrice = savingPrice;
        this.savingChance = (savingChance != null) ? savingChance : 0;
        this.category = category;
        this.savingDay = savingDay;
    }
}
