package bunny.backend.bunny.domain;

import bunny.backend.common.BaseEntity;
import bunny.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "target")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Target extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_target_amount")
    private Long totalTargetAmout;

    @Column(name = "target_amount")
    private Long targetAmout;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",unique = true) // member_id가 unique값 가지므로 한명에 대해 여러번 조회 불가능
    private Member member;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "target_category",
            joinColumns = @JoinColumn(name = "target_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> targetNameList;

    public void setTotalTargetAmount(Long totalTargetAmout) {
        this.totalTargetAmout = totalTargetAmout;
    }
    public void setTargetList(List<Category> targetNameList) {
        this.targetNameList = targetNameList;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
