package bunny.backend.salary.dto;

public record MoneyShowResponse(
        double monthMoney,
        double weekMoney,
        double dayMoney,
        double hourMoney,
        double minMoney,
        double secondMoney

) {
}
