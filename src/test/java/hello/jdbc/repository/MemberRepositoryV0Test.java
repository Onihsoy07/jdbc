package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

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
}