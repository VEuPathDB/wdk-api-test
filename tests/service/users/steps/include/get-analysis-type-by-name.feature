# getAnalysisTypeByName(a, cookie)
#   a:      response from analysis-types endpoint
#   cookie: login cookie
@ignore
Feature: Get an analysis type by provided name

Scenario:

  Given url baseUrl
    And path 'service/users/current/steps/110719150/analysis-types', __arg['name']
    And cookie wdk_check_auth = __arg['auth']
  When method get
  Then status 200