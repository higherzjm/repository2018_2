package com.advanced.annotation.entity;

import com.advanced.annotation.myannotation.PeopleAnnotion;

public class WhitePeople extends IPeople {
 
	@Override
    @PeopleAnnotion(say = "White")
	public void say() {
		System.out.println("I am White");
	}
 
}
