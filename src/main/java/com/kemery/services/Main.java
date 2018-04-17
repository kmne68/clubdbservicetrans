package com.kemery.services;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClubDBServiceConfiguration.class);

		MemberService memberService = applicationContext.getBean(MemberService.class);
		
		Scanner sc = new Scanner(System.in);
		String success;
		
		System.out.print("Update password for member: ");
		String memid = sc.nextLine();
		if(!memid.isEmpty() && memberService.showMember(memid)) {
			System.out.print("New Password: ");
			long pwd = Long.parseLong(sc.nextLine());
			
			memberService.updatePassword(memid, pwd);
		}
		
		System.out.print("Renew member (id): ");
		if(!memid.isEmpty() && memberService.showMember(memid)) {
			
			System.out.println("Succeed? (Y/N): ");
			success = sc.nextLine();
			
			// call renewMember			
			memberService.renewMember(memid, success);		

		}
		
		sc.close();
		applicationContext.close();
		
	}

}
