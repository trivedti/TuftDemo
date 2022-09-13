Feature: Client Registration

Background:
Given Validate the browser

@RegressionTest
Scenario Outline:Successful Login with valid credentials and register client
Given User is on login page
When User enters Email as <Email>
And User enters Password as <Password>
And Click on Login
And User click on Add client menu
And User click on Add new client button
Then Add or edit client pop up is displayed as <Popup>
And User enters Firs Name as <FirstName> in to personal details
And User enters Last Name as <LastName> in to personal details
And User enters Email Address as <EmailAddress> in to contact details
And User enters Mobile Number as <MobileNumber> in to contact details
And User enters address as <Address> in to contact details
And User enters Emergency FullName as <EFullName> in to Emergency contact details
And User enters Emergency Telephone as <ETelephone> in to Emergency contact details
#And User enters Practice Name as <VPractice> in to Veterinary Practice details
#And User enters Notes as <Notes> in to bottom of the page
And Click on Save Change button
And close browser

Examples:
|Email|Password|Popup|FirstName|LastName|EmailAddress|MobileNumber|Address|EFullName|ETelephone|VPractice|Notes|
|test22.2multi@yopmail.com|12345678|Add or Edit Client|Salim|Roseph|rosuph@synoverge.com|7890062909|Washington, DC, USA|John Rovek|7890062909|Test|Test|