package bunny.backend.member.service;

import bunny.backend.common.ApiResponse;
import bunny.backend.exception.BunnyException;
import bunny.backend.member.domain.Member;
import bunny.backend.member.domain.MemberName;
import bunny.backend.member.domain.MemberRepository;
import bunny.backend.member.dto.request.CheckMemberNameRequest;
import bunny.backend.member.dto.response.CheckMemberNameResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 유저 이름 중복체크
    public ApiResponse<CheckMemberNameResponse> checkMemberName(CheckMemberNameRequest request) {
        String memberName = request.name();
        checkMemberName(memberName);
        ApiResponse<CheckMemberNameResponse> response = ApiResponse.success(new CheckMemberNameResponse("success"));
        return response;
    }

    private void checkMemberName(String memberName) {
        Member member = memberRepository.findByMemberName(new MemberName(memberName));
        if (member != null) {
            throw new BunnyException("이름이 중복되었습니다", HttpStatus.BAD_REQUEST);
        }
    }

}
