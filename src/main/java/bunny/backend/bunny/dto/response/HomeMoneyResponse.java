package bunny.backend.bunny.dto.response;

public record HomeMoneyResponse(
        double minMoney,
        double hourMoney,
        double secondMoney
) {
}
