package com.messenger.friendsapp.application.query;

import com.messenger.sharedlib.infra.pagination.PageNumberPaginatedQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class FriendsListQuery extends PageNumberPaginatedQuery {
    private UUID userId;
    private String fullName;

    private static int maxPageSize = 200;

    public FriendsListQuery(int pageNumber, int pageSize, UUID userId, String fullName) {
        super(pageNumber, pageSize);
        this.userId = userId;
        this.fullName = fullName;
    }

    @Override
    public int getMaxPageSize() {
        return 50;
    }
}
