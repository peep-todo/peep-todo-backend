package peep.com.todo_backend.team.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import peep.com.todo_backend.global.Response.ApiSuccessResponse;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerApiNotFoundError;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerApiSuccess;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerInternetServerError;
import peep.com.todo_backend.global.dto.ResultDto;
import peep.com.todo_backend.team.dto.TeamResponseDto;
import peep.com.todo_backend.team.dto.TeamSaveDto;
import peep.com.todo_backend.team.service.TeamService;

@RestController
@RequestMapping("/api/v1/team")
@Tag(name = "Team API", description = "팀 프로젝트 관련 API")
@Slf4j
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @SwaggerApiSuccess(summary = "팀 프로젝트 생성", description = "신규 프로젝트를 생성합니다.", value = ApiSuccessResponse.TEAM_SAVE)
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PostMapping
    public ResponseEntity<?> saveTeam(@Valid @RequestBody TeamSaveDto dto, @AuthenticationPrincipal Integer userId) {
        String inviteLink = teamService.saveTeam(dto, userId);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, "SUCCESS", "팀이 성공적으로 생성되었습니다.", inviteLink));
    }

    @SwaggerApiSuccess(summary = "팀 정보 조회", description = "팁 정보를 조회합니다.", value = ApiSuccessResponse.TEAM_GET)
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @GetMapping("/getTeam")
    public ResponseEntity<?> getTeam(
            @RequestParam(name = "teamToken") @Parameter(description = "조회할 팀의 토큰", required = true) String teamToken) {
        TeamResponseDto responseDto = teamService.getTeam(teamToken);

        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, "SUCCESS", "팀 정보 조회 성공", responseDto));
    }

    @SwaggerApiSuccess(summary = "팀 수정", description = "팀 정보를 수정합니다.", value = ApiSuccessResponse.TEAM_UPDATE)
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PutMapping("/updateTeam")
    public ResponseEntity<?> updateTeam(
        @RequestParam Integer teamId,
        @Valid @RequestBody TeamSaveDto dto,
        @AuthenticationPrincipal Integer userId) {

        TeamResponseDto updatedTeam = teamService.updateTeam(teamId, dto, userId);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, "SUCCESS", "팀 수정 성공", updatedTeam));
    }

    @SwaggerApiSuccess(summary = "팀 삭제", description = "팀을 삭제합니다.", value = ApiSuccessResponse.TEAM_DELETE)
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @DeleteMapping("/deleteTeam")
    public ResponseEntity<?> deleteTeam(
        @RequestParam Integer teamId,
        @AuthenticationPrincipal Integer userId) {

        teamService.deleteTeam(teamId, userId);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, "SUCCESS", "팀 삭제 성공"));
    }
}
