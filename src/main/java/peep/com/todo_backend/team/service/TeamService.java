package peep.com.todo_backend.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import peep.com.todo_backend.global.Exception.BadRequestException;
import peep.com.todo_backend.global.dto.ResultDto;
import peep.com.todo_backend.global.enums.TeamUserRole;
import peep.com.todo_backend.team.domain.Team;
import peep.com.todo_backend.team.domain.TeamUser;
import peep.com.todo_backend.team.dto.TeamResponseDto;
import peep.com.todo_backend.team.dto.TeamSaveDto;
import peep.com.todo_backend.team.dto.dtoConverter.TeamDtoConverter;
import peep.com.todo_backend.team.repository.TeamJpaRepository;
import peep.com.todo_backend.team.repository.TeamUserJpaRepository;
import peep.com.todo_backend.user.domain.User;
import peep.com.todo_backend.user.repository.UserJpaRepository;

@RequiredArgsConstructor
@Slf4j
@Service
public class TeamService {

        private final TeamJpaRepository teamJpaRepository;
        private final TeamUserJpaRepository teamUserJpaRepository;
        private final UserJpaRepository userJpaRepository;

        public String saveTeam(TeamSaveDto dto, Integer userId) {

                User user = userJpaRepository.findById(userId)
                                .orElseThrow(() -> new BadRequestException("존재하지 않는 유저입니다."));

                String teamToken = UUID.randomUUID().toString();

                Team team = Team.builder()
                                .name(dto.getName())
                                .projectName(dto.getProjectName())
                                .description(dto.getDescription())
                                .startDate(dto.getStartDate())
                                .endDate(dto.getEndDate())
                                .teamToken(teamToken)
                                .inviteLink("https://localhost:8080/invite/" + teamToken)
                                .inviteExpiry(LocalDateTime.now().plusDays(7))
                                .isDeleted(false)
                                .build();

                team = teamJpaRepository.save(team);

                TeamUser teamUser = TeamUser.builder()
                                .user(user)
                                .team(team)
                                .teamUserRole(TeamUserRole.ADMIN)
                                .build();

                teamUserJpaRepository.save(teamUser);

                return team.getInviteLink();
        }

        // 팀 정보 조회
        public TeamResponseDto getTeam(String teamToken) {
                Team team = teamJpaRepository.findByTeamToken(teamToken)
                                .orElseThrow(() -> new BadRequestException("존재하지 않거나 삭제된 팀입니다."));

                TeamResponseDto responseDto = TeamDtoConverter.toResponseDto(team);

                return responseDto;
        }

        // 팀 정보 수정

        // 팀에 멤버 초대
        public ResponseEntity<?> inviteFriendToTeam(Integer teamId, Integer inviterId, List<Integer> friendIds) {
                Team team = teamJpaRepository.findById(teamId)
                                .orElseThrow(() -> new BadRequestException("존재하지 않는 팀입니다."));

                User inviter = userJpaRepository.findById(inviterId)
                                .orElseThrow(() -> new BadRequestException("존재하지 않는 사용자입니다."));

                // 사용자가 ADMIN인지 확인
                boolean isAdmin = teamUserJpaRepository.existsByTeamAndUserAndTeamUserRole(team, inviter,
                                TeamUserRole.ADMIN);
                if (!isAdmin) {
                        throw new BadRequestException("관리자만 초대를 보낼 수 있습니다.");
                }

                // 친구 초대 처리
                for (Integer friendId : friendIds) {
                        User friend = userJpaRepository.findById(friendId)
                                        .orElseThrow(() -> new BadRequestException("초대할 사용자를 찾을 수 없습니다."));

                        TeamUser teamUser = TeamUser.builder()
                                        .user(friend)
                                        .team(team)
                                        .teamUserRole(TeamUserRole.MEMBER)
                                        .build();

                        teamUserJpaRepository.save(teamUser);
                }

                return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, "친구 초대 성공", null));
        }

        public List<Team> findPersonalTeamList(Integer userId) {
                return teamUserJpaRepository.findTeamsByUserIdAndRole(userId, TeamUserRole.ADMIN);
        }

        // 팀 정보 수정
        public TeamResponseDto updateTeam(Integer teamId, TeamSaveDto dto, Integer userId) {
                Team team = teamJpaRepository.findById(teamId)
                    .orElseThrow(() -> new BadRequestException("존재하지 않는 팀입니다."));

                // 관리자인지 확인
                boolean isAdmin = teamUserJpaRepository.existsByTeamAndUserAndTeamUserRole(team,
                    userJpaRepository.findById(userId).orElseThrow(() -> new BadRequestException("유저를 찾을 수 없습니다.")),
                    TeamUserRole.ADMIN);
                if (!isAdmin) {
                        throw new BadRequestException("관리자만 팀 정보를 수정할 수 있습니다.");
                }

                team.setName(dto.getName());
                team.setProjectName(dto.getProjectName());
                team.setDescription(dto.getDescription());

                return TeamDtoConverter.toResponseDto(team);
        }

        // 팀 삭제
        public void deleteTeam(Integer teamId, Integer userId) {
                Team team = teamJpaRepository.findById(teamId)
                    .orElseThrow(() -> new BadRequestException("존재하지 않는 팀입니다."));

                boolean isAdmin = teamUserJpaRepository.existsByTeamAndUserAndTeamUserRole(team,
                    userJpaRepository.findById(userId).orElseThrow(() -> new BadRequestException("유저를 찾을 수 없습니다.")),
                    TeamUserRole.ADMIN);
                if (!isAdmin) {
                        throw new BadRequestException("관리자만 팀을 삭제할 수 있습니다.");
                }

                teamJpaRepository.delete(team);
        }

}
