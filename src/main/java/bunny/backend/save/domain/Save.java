package bunny.backend.save.domain;

import bunny.backend.bunny.domain.Category;
import bunny.backend.common.BaseEntity;
import bunny.backend.member.domain.Member;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name="detail")
    String detail;

    public Save(Member member,double savingPrice,String detail,Category category,LocalDate savingDay,Integer savingChance) {
        this.member = member;
        this.savingPrice = savingPrice;
        this.detail = detail;
        this.savingChance = (savingChance != null) ? savingChance : 0;
        this.category = category;
        this.savingDay = savingDay;
    }
}
