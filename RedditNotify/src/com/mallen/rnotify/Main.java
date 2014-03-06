package com.mallen.rnotify;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.mallen.notify.DeskNotify;

import im.goel.jreddit.message.Message;
import im.goel.jreddit.message.Messages;
import im.goel.jreddit.submissions.Submission;
import im.goel.jreddit.submissions.Submissions;
import im.goel.jreddit.submissions.Submissions.Page;
import im.goel.jreddit.submissions.Submissions.Popularity;
import im.goel.jreddit.user.User;


public class Main {
	public static String contextURL;
	private static DeskNotify dn = new DeskNotify();
	static String User;
	static String Pass;
	static User u;
	public static void main(String[] args) throws Exception{
		
		InputStream ips=new FileInputStream("redditnotify.ini"); 
	     InputStreamReader ipsr=new InputStreamReader(ips);
	     BufferedReader br=new BufferedReader(ipsr);
	     
	     
	     for(int i = 0; i < 2; i++){
	     String[] LineSplit = br.readLine().split("=");
	     String Line = LineSplit[1].trim();
	     
	     if(i == 0){
	    	 User = Line;
	    	 System.out.println("## SET ACC_NAME TO " + User);
	     }
	     if(i == 1){
	    	 Pass = Line;
	    	 System.out.println("## SET ACC_AUTH TO " + Pass);
	     }
	    }
	     
	 	u = new User(User, Pass);
		u.connect();
		
		
		Main m = new Main();
		
		while(true){
			try{
		List<Message> orgInbox = new Messages().inbox(u, 1);
		System.out.println(orgInbox.get(0).getId());
		Thread.sleep(2000);
		m.checkInbox(orgInbox);
			}catch(Exception e){}
		}
	}
	public void checkInbox(List<Message> snapBox){
		List<Message> inbox = new Messages().inbox(u, 1);
		
		
		if(inbox.get(0).getId().equals(snapBox.get(0).getId())){ System.out.println(snapBox.get(0).getId()); } else {		
			dn.setUrl("www.reddit.com" + snapBox.get(0).getContext());
			 dn.notify(inbox.get(0).getAuthor() + " - " + inbox.get(0).getBody());
			 System.out.println(snapBox.get(0).getContext());
		}
	}
}
