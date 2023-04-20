package com.messenger.friendsapp.application.query.handler;

import com.messenger.friendsapp.application.dto.FriendDto;
import com.messenger.friendsapp.application.dto.FriendsListDto;
import com.messenger.friendsapp.application.query.SearchFriendsByParamsQuery;
import com.messenger.friendsapp.domain.repository.FriendshipRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SearchFriendsByParamsQueryHandler {
    private final FriendshipRepository friendshipRepository;

    public SearchFriendsByParamsQueryHandler(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public FriendsListDto handle(@NonNull SearchFriendsByParamsQuery query) {
        var friends = friendshipRepository.findAllFriendsPaginatedByParams(
                query.getPageNumber(),
                query.getPageSize(),
                query.getUserId(),
                query.getFullName(),
                query.getAdditionDate(),
                query.getFriendId()
        );

        var friendDtoList = friends.stream().map(FriendDto::fromFriendship).collect(Collectors.toList());

        return new FriendsListDto(
                friendDtoList.size(),
                query.getPageNumber(),
                friendDtoList
        );
    }
}
