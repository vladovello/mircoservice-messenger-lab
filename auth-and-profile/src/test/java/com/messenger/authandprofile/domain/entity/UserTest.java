package com.messenger.authandprofile.domain.entity;

import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.model.valueobject.Email;
import com.messenger.authandprofile.domain.model.valueobject.FullName;
import com.messenger.authandprofile.domain.model.valueobject.Login;
import com.messenger.authandprofile.domain.model.valueobject.RegexPassword;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @SneakyThrows
    @Test
    void when_CreateWithEmptyUUID_Expect_IdSet() {
        var user = User.builder(
                new Login("ahah"),
                new Email("user@mail.com"),
                new FullName("Peter", "Petrov", "Petrovich"),
                new RegexPassword("aAb@cC123")
        ).registerUser();
        assertNotNull(user.getId());
    }

    @SneakyThrows
    @Test
    void when_CreateWithUUID_Expect_IdEqualsSetId() {
        var id = UUID.randomUUID();
        var user = createBasicUserWithId(id);
        assertEquals(user.getId(), id);
    }

    @SneakyThrows
    @Test
    void when_DifferentIds_Expect_UsersNotEqual() {
        var id1 = UUID.fromString("e1c2a3c5-c1f6-4c21-adb2-44998ac8e7e8");
        var id2 = UUID.fromString("abcde544-c1f6-4c21-adb2-44998ac8e7e8");

        var user1 = createBasicUserWithId(id1);
        var user2 = createBasicUserWithId(id2);

        assertNotEquals(user1, user2);
    }

    @SneakyThrows
    @Test
    void when_EqualIds_Expect_UsersEqual() {
        var id1 = UUID.fromString("e1c2a3c5-c1f6-4c21-adb2-44998ac8e7e8");
        var id2 = UUID.fromString("e1c2a3c5-c1f6-4c21-adb2-44998ac8e7e8");

        var user1 = createBasicUserWithId(id1);
        var user2 = createBasicUserWithId(id2);

        assertEquals(user1, user2);
    }

    @SneakyThrows
    private User createBasicUserWithId(UUID id) {
        return User.builder(
                new Login("ahah"),
                new Email("user@mail.com"),
                new FullName("Peter", "Petrov", "Petrovich"),
                new RegexPassword("aAb@cC123")
        ).reconstructUser(id, LocalDate.now());
    }
}