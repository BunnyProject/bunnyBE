package bunny.backend.member.controller;

import bunny.backend.common.ApiResponse;
import bunny.backend.member.dto.request.CheckMemberNameRequest;
import bunny.backend.member.dto.request.CreateMemberRequest;
import bunny.backend.member.dto.response.CheckMemberNameResponse;
import bunny.backend.member.dto.response.CreateMemberResponse;
import bunny.backend.member.dto.response.FindMemberNameResponse;
import bunny.backend.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "사용자 서비스 모음", description = "사용자 관련한 API 입니다.")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 사용자 이름 중복체크
    @PostMapping("/user/check")
    @Operation(summary = "사용자 닉네임 중복체크", description = "사용자 닉네임 중복체크하는 api입니다.")
    public ApiResponse<CheckMemberNameResponse> checkMemberName(
            @RequestBody CheckMemberNameRequest request
    ) {
        return memberService.checkMemberName(request);
    }

    // 이름으로 사용자 조회
    @GetMapping("/user/find")
    @Operation(summary = "사용자 닉네임으로 조회", description = "닉네임으로 사용자 조회하는 api입니다.")
    public ApiResponse<FindMemberNameResponse> findMember(
            @RequestParam(name = "name") String name
    ) {
        return memberService.findMember(name);
    }

    // 사용자 생성
    @PostMapping("/user")
    @Operation(summary = "사용자 생성", description = "사용자 생성하는 api입니다.")
    public ApiResponse<CreateMemberResponse> createMember(
            @RequestBody CreateMemberRequest request
    ) {
        return memberService.createMember(request);
    }

    // 사용자 삭제
    @DeleteMapping("/user/{id}")
    @Operation(summary = "사용자 삭제", description = "사용자 삭제하는 api입니다.")
    public ApiResponse<String> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return ApiResponse.success("Member deleted: " + id);
    }
}
