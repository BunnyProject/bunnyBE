package bunny.backend.salary.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "salary")
public class Salary {
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

    public Salary(double monthMoney, double weekMoney, double dayMoney, double hourMoney, double minMoney, double secondMoney) {
        this.monthMoney = monthMoney;
        this.weekMoney = weekMoney;
        this.dayMoney = dayMoney;
        this.hourMoney = hourMoney;
        this.minMoney = minMoney;
        this.secondMoney = secondMoney;
    }

}
