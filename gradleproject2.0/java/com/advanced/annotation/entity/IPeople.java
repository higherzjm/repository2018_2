package com.advanced.annotation.entity;

import com.advanced.annotation.myannotation.PeopleAnnotion;

public abstract class IPeople {
    @PeopleAnnotion(say="NoColor")
	public abstract void say();
	
	public void walk(){
		System.out.println("I can Walk");
	}
 
}
