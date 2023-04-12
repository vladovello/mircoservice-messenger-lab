package com.messenger.authandprofile.domain.service.impl;

import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.repository.FriendRepository;
import com.messenger.authandprofile.domain.repository.UserRepository;
import com.messenger.authandprofile.domain.service.DomainUserService;

import java.util.UUID;

public class DomainUserServiceImpl implements DomainUserService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public DomainUserServiceImpl(UserRepository userRepository, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    @Override
    public User getOtherUserProfile(UUID selfUserId, UUID otherUserId) {
        var isSelfInBlackList = friendRepository.isUserInOthersBlacklist(selfUserId, otherUserId);
        return isSelfInBlackList ? null : userRepository.findUserById(otherUserId);
    }
}
