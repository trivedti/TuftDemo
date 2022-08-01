Feature: Login the Product

Background:
Given Validate the browser

@RegressionTest
Scenario Outline:Successful Login with valid credentials
Given User is on login page
When User enters Email as <Email>
And User enters Password as <Password>
And Click on Login
Then User moves to Dashboard page and verify present text as <Dashboard> in to present element


Examples:
| Email|Password|Dashboard|
| test22.2multi@yopmail.com|^FsnSYE!xOFX|Dashboard|