package com.chavetasfechadas;

public class UrlCheckerExample {
	public static void main(String[] args) {
		UrlChecker c = new UrlChecker();
		c.setUrl("https://ciencias.ulisboa.pt/");
		System.out.println(c.validate() == true);
		
		c.setUrl("https://sem.ciencias.ulisboa.pt/");
		System.out.println(c.validate() == false);
		
	}
}
