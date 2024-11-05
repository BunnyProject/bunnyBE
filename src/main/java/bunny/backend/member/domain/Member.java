package bunny.backend.member.domain;

import bunny.backend.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MemberName memberName;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "job", nullable = false)
    private Job job;

    @Column(name = "month_money",nullable = false)
    private double monthMoney;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "work_day", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "work_day")
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> workDay;

    @Column(name = "working_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime workingTime;

    @Column(name = "quitting_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime quittingTime;

    public Member(String name, LocalDate birth, Gender gender, Job job, double monthMoney, List<DayOfWeek> workDay, LocalTime workingTime, LocalTime quittingTime) {
        this.memberName = new MemberName(name);
        this.birth = birth;
        this.gender = gender;
        this.job = job;
        this.monthMoney = monthMoney;
        this.workDay = workDay;
        this.workingTime = workingTime;
        this.quittingTime = quittingTime;
    }

}
