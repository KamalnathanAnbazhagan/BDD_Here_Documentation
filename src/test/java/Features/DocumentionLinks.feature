Feature: Documention page links
@SmokeTest
  Scenario Outline: Checking the links in documention links
    Given Launch browser and navigate to documention page
    When Getting number of links in Documention page 
    Then Validate the all the links are working and angular is loading
		And Close the browser