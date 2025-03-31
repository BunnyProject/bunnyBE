package bunny.backend.member.dto.request;

import bunny.backend.member.domain.Gender;
import bunny.backend.member.domain.Job;

import bunny.backend.member.domain.Member;
import bunny.backend.member.domain.MemberName;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CreateMemberRequest(
        String memberName,
        LocalDate birth,
        Gender gender,
        Job job,
        double monthMoney,
        List<DayOfWeek> workDay,
        LocalTime workingTime,
        LocalTime quittingTime
) {
    public Member toEntity(MemberName memberName) {
        return Member.builder()
                .memberName(memberName)
                .birth(birth())
                .gender(gender())
                .job(job())
                .monthMoney(monthMoney())
                .workDay(workDay())
                .workingTime(workingTime())
                .build();

    }
}
