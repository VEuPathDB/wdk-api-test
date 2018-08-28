Feature: Retrieve analysis type details by name

Background:

  # Perform analysis type lookup
  * def types = call read('./analysis-types.feature')

  # Define function to build input for analysis-type get feature
  * def fun = function(k) { return { name: k.name, auth: types.login.cookies.wdk_check_auth }; }

  # Build analysis-type get feature input
  * def table = karate.map(types.response, fun)

Scenario: Retrieved analysis types can all be looked up

  # Perform the call per array entry in table
  * def result = call read('./include/get-analysis-type-by-name.feature') table

Scenario: Invalid url param results in 404

  # Invalid analysis type
  Given url baseUrl
    And path 'service/users/current/steps/110719150/analysis-types/asdfasdfasdf'
  When method get
  Then status 404

  # Invalid user id
  Given path 'service/users/-1/steps/110719150/analysis-types/', table[0]['name']
  When method get
  Then status 404

  # Invalid step id
  Given path 'service/users/current/steps/-1/analysis-types/', table[0]['name']
  When method get
  Then status 404