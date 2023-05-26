package com.messenger.friendsapp.application.query.handler;

import com.messenger.friendsapp.application.dto.FriendDto;
import com.messenger.friendsapp.application.query.FriendsListQuery;
import com.messenger.friendsapp.domain.repository.FriendshipRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendsListQueryHandler {
    private final FriendshipRepository friendshipRepository;

    public FriendsListQueryHandler(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    /**
     * Makes a paginated search in user's friend list by full name
     * @param query CQS query for appropriate handler
     * @return list of representations of user's friends
     */
    public List<FriendDto> handle(@NonNull FriendsListQuery query) {
        var friendshipList = friendshipRepository.findAllFriendsPaginatedByFullName(
                query.getPageNumber(),
                query.getPageSize(),
                query.getUserId(),
                query.getFullName()
        );

        return friendshipList.stream().map(FriendDto::fromFriendship).collect(Collectors.toList());
    }
}
