Feature: Run a step analysis

Background:

  * def analyses = call('../analyses.feature')
  * def analysisId = analyses.response[0].analysisId
  * def endpoint = '#(analyses.endpoint)/#(analysisId)'

Scenario: Success

  Given url baseUrl
    And path endpoint
  When method post
  Then status 200

