package com.otus.homework.repositoryTest;

import com.otus.homework.domain.Authority;
import com.otus.homework.domain.User;
import com.otus.homework.repository.UserRepository;
import com.otus.homework.service.MongoUserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

import static com.otus.homework.security.AuthoritiesConstants.USER;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"com.otus.homework.config", "com.otus.homework.events"})
@DisplayName("Dao для работы с пользователями ")
class UserRepositoryTest {
    private static final String USERNAME = "firstUser";
    private static final User EXPECT_USER = new User(
            "2",
            "firstUser",
            new BCryptPasswordEncoder().encode("firstUser"),
            "Aleksandr",
            "Ivanov",
            Set.of(new Authority(USER))
    );

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private MongoUserDetailsServiceImpl userDetailsService;

    @DisplayName("возвращать пользователя по логину")
    @Test
    void shouldFindUserByLogin() {
        User actualUser = userRepository.findByLogin(USERNAME);
        assertThat(actualUser ).isEqualTo(EXPECT_USER);
    }
}
