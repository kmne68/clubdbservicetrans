package com.kemery.services;

public interface MemberService {

	public void updatePassword(String memid, long password);
	public boolean showMember(String memid);
//	public void renewMember(String memid, String success);
}
