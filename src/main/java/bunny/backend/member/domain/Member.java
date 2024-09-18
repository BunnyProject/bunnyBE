package bunny.backend.member.domain;

import bunny.backend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "money", nullable = false)
    private Long money;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "work_day", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "work_day")
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> workDay;

    @Column(name = "working_time", nullable = false)
    private LocalDateTime workingTime;

    @Column(name = "quitting_time", nullable = false)
    private LocalDateTime quittingTime;

    public Member(String name, LocalDate birth, Gender gender, Job job, Long money, List<DayOfWeek> workDay, LocalDateTime workingTime, LocalDateTime quittingTime) {
        this.memberName = new MemberName(name);
        this.birth = birth;
        this.gender = gender;
        this.job = job;
        this.money = money;
        this.workDay = workDay;
        this.workingTime = workingTime;
        this.quittingTime = quittingTime;
    }
}
