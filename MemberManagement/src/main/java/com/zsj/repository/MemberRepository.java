package com.zsj.repository;

import com.zsj.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zsj55
 */
public interface MemberRepository extends JpaRepository<Member, String> {
    public List<Member> findByUserName(String userName);
    public List<Member> findByUserNameAndPassword(String userName, String password);
    public Member findByInviteCode(String inviteCode);

    @Query(value = "update member set tel=?1,sex=?2 where id=?3", nativeQuery = true)
    @Modifying
    @Transactional
    public int updateMemberDetail(String tel, String sex, String id);

    @Query(value = "update member set grade=?1 where id=?2", nativeQuery = true)
    @Modifying
    @Transactional
    public int updateMemberGrade(int grade, String id);


    @Query(value = "update member set inviter_id=?1 where id=?2", nativeQuery = true)
    @Modifying
    @Transactional
    public int updateMemberInviterId(String inviterId, String id);
}
