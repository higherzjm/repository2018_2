package com.advanced.annotation.entity;

import com.advanced.annotation.myannotation.PeopleAnnotion;

public class YellowPeople extends IPeople{
 
	@Override
    @PeopleAnnotion(say="Yellow")
	public void say() {
		System.out.println("I am Yellow");			
	}
 
}
