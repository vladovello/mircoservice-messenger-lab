package com.messenger.authandprofile.domain;

import com.messenger.authandprofile.domain.model.User;
import com.messenger.authandprofile.domain.valueobject.Email;
import com.messenger.authandprofile.domain.valueobject.Password;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @SneakyThrows
    @Test
    void when_CreateWithEmptyUUID_Expect_IdSet() {
        var user = new User(new Email("user@mail.com"), new Password("aAb@cC123"));
        assertNotNull(user.getId());
    }

    @SneakyThrows
    @Test
    void when_CreateWithUUID_Expect_IdEqualsSetId() {
        var id = UUID.randomUUID();
        var user = new User(id, new Email("user@mail.com"), new Password("aAb@cC123"));
        assertEquals(user.getId(), id);
    }

    @SneakyThrows
    @Test
    void when_DifferentIds_Expect_UsersNotEqual() {
        var id1 = UUID.fromString("e1c2a3c5-c1f6-4c21-adb2-44998ac8e7e8");
        var id2 = UUID.fromString("abcde544-c1f6-4c21-adb2-44998ac8e7e8");

        var user1 = new User(id1, new Email("user@mail.com"), new Password("aAb@cC123"));
        var user2 = new User(id2, new Email("user@mail.com"), new Password("aAb@cC123"));

        assertNotEquals(user1, user2);
    }

    @SneakyThrows
    @Test
    void when_EqualIds_Expect_UsersEqual() {
        var id1 = UUID.fromString("e1c2a3c5-c1f6-4c21-adb2-44998ac8e7e8");
        var id2 = UUID.fromString("e1c2a3c5-c1f6-4c21-adb2-44998ac8e7e8");

        var user1 = new User(id1, new Email("user1@mail.com"), new Password("aAb@cC123"));
        var user2 = new User(id2, new Email("user2@mail.com"), new Password("123aAb@cC12345"));

        assertEquals(user1, user2);
    }
}