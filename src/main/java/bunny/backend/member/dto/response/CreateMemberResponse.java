package bunny.backend.member.dto.response;

import bunny.backend.member.domain.Member;
import bunny.backend.member.domain.MemberName;
import lombok.Builder;

@Builder
public record CreateMemberResponse(MemberName memberName, Long id) {

    public static CreateMemberResponse from(final Member member) {
        return CreateMemberResponse.builder()
                .id(member.getId())
                .memberName(member.getMemberName())
                .build();
    }
}
