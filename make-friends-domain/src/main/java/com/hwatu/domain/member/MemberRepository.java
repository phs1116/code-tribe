package com.hwatu.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by hwatu on 2017. 2. 21..
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, QueryDslPredicateExecutor<Member> {
	Member findByUserId(String userId);

	Member findByUserIdAndPassword(String userId, String password);

}
