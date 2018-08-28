Feature: Analysis Type List Endpoint

Background: User must be logged in

  * def login = call read('../../login.feature')

  # TODO: refactor these
  * def step = 110719150
  * def endpoint = 'service/users/current/steps/'

Scenario: Retrieve available analysis types

  Given url baseUrl
    And path 'service/users/current/steps/110719150/analysis-types'
    And cookie wdk_check_auth = login.cookies.wdk_check_auth
  When method get
  Then status 200
