package bunny.backend.bunny.domain;

import bunny.backend.common.BaseEntity;
import bunny.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "category")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
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
    private Long targetAmout;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Category(String categoryName, Long onePrice, Member member,Long targetAmout) {
        this.categoryName = categoryName;
        this.onePrice = onePrice;
        this.member = member;
        this.targetAmout = targetAmout;
    }
}
