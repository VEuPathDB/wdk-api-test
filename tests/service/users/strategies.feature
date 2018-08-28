Feature: Strategies

Background:

  * def login = call read('../../login.feature')
  * def endpoint = 'service/users/#(login.userId)/strategies'

Scenario:
  
  Given url baseUrl
    And path endpoint
    And cookie 
  When 