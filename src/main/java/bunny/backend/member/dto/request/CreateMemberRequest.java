package bunny.backend.member.dto.request;

import bunny.backend.member.domain.Gender;
import bunny.backend.member.domain.Job;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CreateMemberRequest(
        String name,
        LocalDate birth,
        Gender gender,
        Job job,
        double monthMoney,
        List<DayOfWeek> workDay,
        LocalTime workingTime,
        LocalTime quittingTime
) {}
