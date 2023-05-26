package com.messenger.friendsapp.application.query.handler;

import com.messenger.friendsapp.application.dto.FriendDto;
import com.messenger.friendsapp.application.query.GetFriendInfoQuery;
import com.messenger.friendsapp.domain.repository.FriendshipRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetFriendInfoQueryHandler {
    private final FriendshipRepository friendshipRepository;

    public GetFriendInfoQueryHandler(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    /**
     * @param query CQS command for appropriate handler
     * @return Representation of a single user's friend
     */
    public Optional<FriendDto> handle(@NonNull GetFriendInfoQuery query) {
        var friendship = friendshipRepository.findFriend(query.getUserId(), query.getFriendId());
        return friendship.map(FriendDto::fromFriendship);
    }
}
