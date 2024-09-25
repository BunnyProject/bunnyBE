package bunny.backend.salary.domain;

import bunny.backend.common.BaseEntity;
import bunny.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "salary")
public class Salary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month_money")
    private double monthMoney;

    @Column(name = "week_money")
    private double weekMoney;

    @Column(name = "day_money")
    private double dayMoney;

    @Column(name = "hour_money")
    private double hourMoney;

    @Column(name = "min_money")
    private double minMoney;

    @Column(name = "second_money")
    private double secondMoney;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",unique = true) // member_id가 unique값 가지므로 한명에 대해 여러번 조회 불가능
    private Member member;

    public Salary(double monthMoney, double weekMoney, double dayMoney, double hourMoney, double minMoney, double secondMoney) {
        this.monthMoney = monthMoney;
        this.weekMoney = weekMoney;
        this.dayMoney = dayMoney;
        this.hourMoney = hourMoney;
        this.minMoney = minMoney;
        this.secondMoney = secondMoney;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
