package com.advanced.annotation.entity;

import com.advanced.annotation.myannotation.PeopleAnnotion;

public class BlackPeople extends IPeople{
 
	@Override
    @PeopleAnnotion(say="Black")
	public void say() {
		System.out.println("I am Black");
	}
 
}