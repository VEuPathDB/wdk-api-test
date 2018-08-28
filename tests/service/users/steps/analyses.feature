Feature: Analysis List Endpoint

Background: User must be logged in

  * def login = call read('../../login.feature')

Scenario:

  Given url baseUrl
    And path 'service/users/current/steps/110719150/analyses'
    And cookie wdk_check_auth = login.cookies.wdk_check_auth
  When method get
  Then status 200