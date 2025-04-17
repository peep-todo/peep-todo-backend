package peep.com.todo_backend.category.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peep.com.todo_backend.category.domain.Category;
import peep.com.todo_backend.category.dto.CategoryResponseDto;
import peep.com.todo_backend.category.dto.CategorySaveDto;
import peep.com.todo_backend.category.dto.CategoryUpdateDto;
import peep.com.todo_backend.category.repository.CategoryRepository;
import peep.com.todo_backend.global.Exception.BadRequestException;
import peep.com.todo_backend.global.Exception.ForbiddenException;
import peep.com.todo_backend.global.Exception.NotFoundException;
import peep.com.todo_backend.global.enums.CategoryType;
import peep.com.todo_backend.team.domain.Team;
import peep.com.todo_backend.team.repository.TeamJpaRepository;
import peep.com.todo_backend.user.domain.User;
import peep.com.todo_backend.user.repository.UserJpaRepository;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserJpaRepository userJpaRepository;
    private final TeamJpaRepository teamJpaRepository;

    @Transactional
    public void saveCategory(CategorySaveDto dto, Integer userId, Integer teamId) {
        User user = userJpaRepository.findById(userId)
            .orElseThrow(() -> new BadRequestException("존재하지 않는 유저입니다."));

        if (teamId == null) {
            categoryRepository.findByUser_UserIdAndName(userId, dto.getName())
                .ifPresent(c -> { throw new BadRequestException("이미 등록된 카테고리명입니다."); });
        } else {
            Team team = teamJpaRepository.findById(teamId)
                .orElseThrow(() -> new BadRequestException("유효하지 않은 팀입니다."));
            categoryRepository.findByTeam_TeamIdAndName(teamId, dto.getName())
                .ifPresent(c -> { throw new BadRequestException("이미 등록된 팀 카테고리명입니다."); });
        }

        Category.CategoryBuilder builder = Category.builder()
            .name(dto.getName())
            .color(dto.getColor())
            .categoryType(teamId == null ? CategoryType.PERSONAL : CategoryType.TEAM);

        if (teamId == null) {
            builder.user(user);
        } else {
            builder.team(teamJpaRepository.getOne(teamId));
        }

        Category category = builder.build();
        categoryRepository.save(category);

        if (dto.isPinned()) {
            pinCategory(category.getCategoryId());
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getCategories(
        Integer userId,
        Integer teamId
    ) {
        userJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<Category> categories;
        if (teamId == null) {
            categories = categoryRepository
                .findByUser_UserIdAndCategoryType(
                    userId, CategoryType.PERSONAL);
        } else {
            // Team
            teamJpaRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("유효하지 않은 팀입니다."));
            categories = categoryRepository
                .findByTeam_TeamIdAndCategoryType(
                    teamId, CategoryType.TEAM);
        }

        return categories.stream()
            .map(cat -> new CategoryResponseDto(
                cat.getCategoryId(),
                cat.getName(),
                cat.getColor(),
                cat.isPinned(),
                cat.getCategoryType()
            ))
            .collect(Collectors.toList());
    }


    @Transactional
    public void updateCategory(
        CategoryUpdateDto dto,
        Integer userId,
        Integer teamId,
        Integer categoryId
    ) {
        User user = userJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new NotFoundException("카테고리를 찾을 수 없습니다."));

        if (teamId == null) {
            // ● Personal
            if (category.getCategoryType() != CategoryType.PERSONAL ||
                !category.getUser().getUserId().equals(userId)) {
                throw new ForbiddenException("해당 유저의 카테고리가 아닙니다.");
            }
            if (!category.getName().equals(dto.getName())) {
                Optional<Category> dup = categoryRepository
                    .findByUser_UserIdAndName(userId, dto.getName());
                dup.ifPresent(c -> {
                    throw new BadRequestException("이미 등록된 카테고리명입니다.");
                });
            }
        } else {
            // ● Team
            if (category.getCategoryType() != CategoryType.TEAM ||
                category.getTeam() == null ||
                !category.getTeam().getTeamId().equals(teamId)) {
                throw new ForbiddenException("해당 팀의 카테고리가 아닙니다.");
            }
            teamJpaRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("유효하지 않은 팀입니다."));
            if (!category.getName().equals(dto.getName())) {
                Optional<Category> dup = categoryRepository
                    .findByTeam_TeamIdAndName(teamId, dto.getName());
                dup.ifPresent(c -> {
                    throw new BadRequestException("이미 등록된 팀 카테고리명입니다.");
                });
            }
        }

        category.setName(dto.getName());
        category.setColor(dto.getColor());

        boolean beforePinned = category.isPinned();
        if (dto.isPinned() && !beforePinned) {
            pinCategory(categoryId);
        } else if (!dto.isPinned() && beforePinned) {
            unpinCategory(categoryId);
        }

    }

    @Transactional
    public void deleteCategory(
        Integer userId,
        Integer teamId,
        Integer categoryId
    ) {
        User user = userJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new NotFoundException("카테고리를 찾을 수 없습니다."));

        if (teamId == null) {
            if (category.getCategoryType() != CategoryType.PERSONAL ||
                !category.getUser().getUserId().equals(userId)) {
                throw new ForbiddenException("해당 유저의 카테고리가 아닙니다.");
            }
        } else {
            Team team = teamJpaRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("유효하지 않은 팀입니다."));
            if (category.getCategoryType() != CategoryType.TEAM ||
                category.getTeam() == null ||
                !category.getTeam().getTeamId().equals(teamId)) {
                throw new ForbiddenException("해당 팀의 카테고리가 아닙니다.");
            }
        }

        if (category.isPinned()) {
            unpinCategory(categoryId);
        }

        categoryRepository.delete(category);
    }

    /**
     * 해당 카테고리를 상단 고정(pinned) 처리합니다. 이전에 고정된 카테고리가 있으면 해제하고, 요청한 카테고리를 고정합니다.
     */
    public void pinCategory(Integer categoryId) {
        // 1) 기존에 pinned=true 였던 카테고리 해제
        categoryRepository.findByIsPinnedTrue()
            .ifPresent(existing -> {
                existing.setPinned(false);
                categoryRepository.save(existing);
            });

        // 2) 대상 카테고리 조회 및 pinned 설정
        categoryRepository.findById(categoryId)
            .ifPresentOrElse(category -> {
                category.setPinned(true);
                categoryRepository.save(category);
            }, () -> {
                throw new NotFoundException("카테고리를 찾을 수 없습니다. id=" + categoryId);
            });
    }

    /**
     * 카테고리 상단 해제
     */
    public void unpinCategory(Integer categoryId) {
        categoryRepository.findById(categoryId)
            .ifPresentOrElse(category -> {
                category.setPinned(false);
                categoryRepository.save(category);
            }, () -> {
                throw new NotFoundException("카테고리를 찾을 수 없습니다. id=" + categoryId);
            });
    }



}
