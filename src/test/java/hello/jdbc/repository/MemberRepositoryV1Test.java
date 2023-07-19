package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void save() throws SQLException {
        Member member = new Member("memberV0", 11111);
        repository.save(member);
    }

    @Test
    void findById() throws SQLException {
        Member member = new Member("memberV0_1", 213548);
        repository.save(member);

        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember = {}", member);
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void update() throws SQLException {
        Member member = new Member("memberV0_2", 54321);
        repository.save(member);

        repository.update(member.getMemberId(), 20000);

        Member findMember = repository.findById(member.getMemberId());

        assertThat(findMember.getMoney()).isEqualTo(20000);
    }

    @Test
    void delete() throws SQLException {
        Member member = new Member("memberV0_3", 213548);
        repository.save(member);

        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember = {}", member);
        assertThat(findMember).isEqualTo(member);

        repository.delete(findMember.getMemberId());

        assertThatThrownBy(() -> repository.findById(findMember.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}