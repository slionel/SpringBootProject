package com.zsj.repository;

import com.zsj.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zsj55
 */
public interface MemberRepository extends JpaRepository<Member, String> {
    public List<Member> findByUserName(String userName);
    public List<Member> findByUserNameAndPassword(String userName, String password);
}
