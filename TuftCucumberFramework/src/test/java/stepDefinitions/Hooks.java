package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
	@Before("@MobileTest")
	public void beforevalidation()
	{
		System.out.print("Mobile before hook or open browser");
	}
	
	@After("@MobileTest")
	public void aftervalidation()
	{
		System.out.print("Mobile after hook or close browser");
	}

}
