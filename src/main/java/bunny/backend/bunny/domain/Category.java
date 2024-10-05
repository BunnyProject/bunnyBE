package bunny.backend.bunny.domain;

import bunny.backend.common.BaseEntity;
import bunny.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "category")
@NoArgsConstructor
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "detail")
    private String detail;

    @Column(name = "one_price")
    private Long onePrice;

    @Column(name = "target_amount")
    private Long targetAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",unique = true)
    private Member member;

    public Category(String categoryName, Long onePrice, Member member,Long targetAmount) {
        this.categoryName = categoryName;
        this.onePrice = onePrice;
        this.member = member;
        this.targetAmount = targetAmount;
    }

    public Category(String categoryName, Member member) {
        this.categoryName = categoryName;
        this.member = member;
    }
    public void setTargetAmount(Long targetAmount) {
        this.targetAmount = targetAmount;
    }
    public void setOnePrice(Long onePrice) {
        this.onePrice = onePrice;
    }

    public void setCategoryName(String categoryName) {this.categoryName = categoryName;}
}
