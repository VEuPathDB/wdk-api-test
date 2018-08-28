Feature: Analysis List Endpoint

Background: User must be logged in

  * def login = call read('../../login.feature')
  * def stepId = '110719150'
  * def userId = login.userId
  * def endpoint = 'service/users/#(userId)/steps/#(stepId)/analyses'

Scenario:

  Given url baseUrl
    And path endpoint
    And cookie wdk_check_auth = login.cookies.wdk_check_auth
  When method get
  Then status 200