# login(): (result, cookies)
#
#   Returns:
#     result: http response body
#     cookies: http response cookies
@ignore
Feature: Perform login

Scenario:
  Given url baseUrl
    And path "service/login"
    And request { email: '#(username)', password: '#(password)', redirectUrl: '#(baseUrl)' }
  When method post
  Then status 200
    And def result = response
    And def cookies = responseCookies
