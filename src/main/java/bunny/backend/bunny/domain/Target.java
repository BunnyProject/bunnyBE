package bunny.backend.bunny.domain;

import bunny.backend.common.BaseEntity;
import bunny.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "target")
@NoArgsConstructor
public class Target extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_target_amount")
    private Long totalTargetAmount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // member_id가 unique값 가지므로 한명에 대해 여러번 조회 불가능
    private Member member;


    public void setTotalTargetAmount(Long totalTargetAmount) {
        this.totalTargetAmount = totalTargetAmount;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
