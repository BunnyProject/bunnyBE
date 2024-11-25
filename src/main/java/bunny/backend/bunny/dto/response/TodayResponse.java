package bunny.backend.bunny.dto.response;

import java.time.LocalTime;

public record TodayResponse(double minMoney,LocalTime workingTime,LocalTime quttingTime
                            ) {
}
