package bunny.backend.member.controller;

import bunny.backend.common.ApiResponse;
import bunny.backend.member.dto.request.CheckMemberNameRequest;
import bunny.backend.member.dto.request.CreateMemberRequest;
import bunny.backend.member.dto.response.CheckMemberNameResponse;
import bunny.backend.member.dto.response.CreateMemberResponse;
import bunny.backend.member.dto.response.FindMemberNameResponse;
import bunny.backend.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

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

    // 이름으로 사용자 조회
    @GetMapping("/user/find")
    public ApiResponse<FindMemberNameResponse> findMember(
            @RequestParam(name = "name") String name
    ) {
        return memberService.findMember(name);
    }

    // 사용자 생성
    @PostMapping("/user")
    public ApiResponse<CreateMemberResponse> createMember(
            @RequestBody CreateMemberRequest request
    ) {
        return memberService.createMember(request);
    }

    // 사용자 삭제
    @DeleteMapping("/user/{id}")
    public ApiResponse<String> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return ApiResponse.success("Member deleted: " + id);
    }
}
