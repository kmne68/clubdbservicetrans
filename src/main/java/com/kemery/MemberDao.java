package com.kemery;

import java.util.List;

public interface MemberDao {

	public Member find(String memid);
	public Member findByQuery(String memid);
	public List<Member> findByStatus(String status);
	public void insert(Member member);
	public void delete(String memid);
	public void update(Member member);
	public void updateMember(Member member);
	public void addDuesPurchase(String memid, String purchasedt, String success);
}
