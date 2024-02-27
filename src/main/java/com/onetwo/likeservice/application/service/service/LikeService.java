package com.onetwo.likeservice.application.service.service;

import com.onetwo.likeservice.application.port.in.command.*;
import com.onetwo.likeservice.application.port.in.response.*;
import com.onetwo.likeservice.application.port.in.usecase.DeleteLikeUseCase;
import com.onetwo.likeservice.application.port.in.usecase.ReadLikeUseCase;
import com.onetwo.likeservice.application.port.in.usecase.RegisterLikeUseCase;
import com.onetwo.likeservice.application.port.out.ReadLikePort;
import com.onetwo.likeservice.application.port.out.RegisterLikePort;
import com.onetwo.likeservice.application.port.out.UpdateLikePort;
import com.onetwo.likeservice.application.service.converter.LikeUseCaseConverter;
import com.onetwo.likeservice.common.exceptions.ResourceAlreadyExistsException;
import com.onetwo.likeservice.domain.Like;
import lombok.RequiredArgsConstructor;
import onetwo.mailboxcommonconfig.common.exceptions.BadRequestException;
import onetwo.mailboxcommonconfig.common.exceptions.NotFoundResourceException;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService implements RegisterLikeUseCase, DeleteLikeUseCase, ReadLikeUseCase {

    private final RegisterLikePort registerLikePort;
    private final ReadLikePort readLikePort;
    private final UpdateLikePort updateLikePort;
    private final LikeUseCaseConverter likeUseCaseConverter;

    /**
     * Register like use case,
     * register like data to persistence
     *
     * @param registerLikeCommand data about register like with user id and target id
     * @return Boolean about register success
     */
    @Override
    @Transactional
    public RegisterLikeResponseDto registerLike(RegisterLikeCommand registerLikeCommand) {
        Optional<Like> optionalLike = readLikePort.findByUserIdAndCategoryAndTargetId(
                registerLikeCommand.getUserId(),
                registerLikeCommand.getCategory(),
                registerLikeCommand.getTargetId()
        );

        if (optionalLike.isPresent())
            throw new ResourceAlreadyExistsException("like already exist. user can like target only ones");

        Like newLike = Like.createNewLikeByCommand(registerLikeCommand);

        Like savedLike = registerLikePort.registerLike(newLike);

        return likeUseCaseConverter.likeToRegisterResponseDto(savedLike);
    }

    /**
     * Delete like use case,
     * delete like data to persistence
     *
     * @param deleteLikeCommand request delete data about like's userid, category, target id
     * @return Boolean about delete like success
     */
    @Override
    @Transactional
    public DeleteLikeResponseDto deleteLike(DeleteLikeCommand deleteLikeCommand) {
        Optional<Like> optionalLike = readLikePort.findByUserIdAndCategoryAndTargetId(
                deleteLikeCommand.getUserId(),
                deleteLikeCommand.getCategory(),
                deleteLikeCommand.getTargetId()
        );

        if (optionalLike.isEmpty()) throw new NotFoundResourceException("like does not exist");

        Like like = optionalLike.get();

        if (!like.isSameUserId(deleteLikeCommand.getUserId()))
            throw new BadRequestException("Like's Register does not match with request user");

        like.deleteLike();

        updateLikePort.updateLike(like);

        return likeUseCaseConverter.likeToDeleteResponseDto(like);
    }

    /**
     * Get Target's Like count use case,
     * get target's like count on persistence
     *
     * @param countLikeCommand request count target data
     * @return About target's like count
     */
    @Override
    @Transactional(readOnly = true)
    public CountLikeResponseDto getLikeCount(CountLikeCommand countLikeCommand) {
        int countLike = readLikePort.countLikeByCategoryAndTargetId(countLikeCommand.getCategory(), countLikeCommand.getTargetId());

        return likeUseCaseConverter.resultToCountResponseDto(countLike);
    }

    /**
     * Get Filtered like use case,
     * Get Filtered slice like data
     *
     * @param likeFilterCommand filter condition and pageable
     * @return content and slice data
     */
    @Override
    @Transactional(readOnly = true)
    public Slice<FilteredLikeResponseDto> filterLike(LikeFilterCommand likeFilterCommand) {
        if (isAtLeastConditionNotExist(likeFilterCommand))
            throw new BadRequestException("condition must have user id or target information");

        List<Like> likeList = readLikePort.filterLike(likeFilterCommand);

        boolean hasNext = likeList.size() > likeFilterCommand.getPageable().getPageSize();

        if (hasNext) likeList.removeLast();

        List<FilteredLikeResponseDto> filteredLikeResponseDtoList = likeList.stream()
                .map(likeUseCaseConverter::likeToFilteredResponse).toList();

        return new SliceImpl<>(filteredLikeResponseDtoList, likeFilterCommand.getPageable(), hasNext);
    }

    /**
     * Get Boolean about User like Target use case
     *
     * @param likeTargetCheckCommand request target data and user data
     * @return Boolean about user like target
     */
    @Override
    @Transactional(readOnly = true)
    public LikeTargetCheckResponseDto userLikeTargetCheck(LikeTargetCheckCommand likeTargetCheckCommand) {
        int countLike = readLikePort.countLikeByUserIdAndCategoryAndTargetId(likeTargetCheckCommand.getUserId(),
                likeTargetCheckCommand.getCategory(), likeTargetCheckCommand.getTargetId());

        return likeUseCaseConverter.resultToLikeTargetCheckResponseDto(countLike > 0);
    }

    private boolean isAtLeastConditionNotExist(LikeFilterCommand likeFilterCommand) {
        boolean isUserIdConditionExist = likeFilterCommand.getUserId() != null;
        boolean isTargetConditionExist = likeFilterCommand.getCategory() != null && likeFilterCommand.getTargetId() != null;

        return !(isUserIdConditionExist || isTargetConditionExist);
    }
}
