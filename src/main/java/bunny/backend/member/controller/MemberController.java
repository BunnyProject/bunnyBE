package bunny.backend.member.controller;

import bunny.backend.common.ApiResponse;
import bunny.backend.member.dto.request.CheckMemberNameRequest;
import bunny.backend.member.dto.response.CheckMemberNameResponse;
import bunny.backend.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 사용자 이름 중복체크
    @PostMapping("/user/check")
    public ApiResponse<CheckMemberNameResponse> checkMemberName(
            @RequestBody CheckMemberNameRequest request
    ) {
        return memberService.checkMemberName(request);
    }

}
