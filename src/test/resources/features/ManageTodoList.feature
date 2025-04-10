#Feature file represents the Acceptance Criteria for the given User Story
Feature: Manage ToDo List
  As a user, I should be able to manage my todos effectively.

  @test-1
  Scenario: Add new items to the todo list
    Given I am on an empty todo list page
    When I add the following todo items
      | Write gherkin scenarios |
      | Write step definitions  |
    Then the todo list should display the added items in the same order
    And the todo count should display "2 items left!"

  @test-2
  Scenario: Mark a todo item as completed
    Given I have the following items in the todo list
      | Write gherkin scenarios |
      | Write step definitions  |
    When I mark the item "Write gherkin scenarios" as completed
    Then the todo list should display "Write gherkin scenarios" as completed
    And the todo count should display "1 item left!"

  @test-3
  Scenario: Edit an existing item
    Given I have the following items in the todo list
      | Write gherkin scenarios |
      | Write step definitions  |
    When I edit the item "Write step definitions" to "Write step def with Clean Code"
    Then the todo list should display the edited item "Write step def with Clean Code"

  @test-4
  Scenario: Remove an active item from the todo list using destroy button
    Given I have the following items in the todo list
      | Write gherkin scenarios |
      | Write step definitions  |
    When I remove an active item "Write step definitions"
    Then the todo list should not display "Write step definitions"
    And the todo count should display "1 item left!"

  @test-5
  Scenario: Remove a completed item from the todo list using destroy button
    Given I have the following items in the todo list
      | Write gherkin scenarios |
      | Write step definitions  |
    And I mark the item "Write step definitions" as completed
    When I remove a completed item "Write step definitions"
    Then the todo list should not display "Write step definitions"
    And the todo count should display "1 item left!"

  @test-6
  Scenario: View all items when "all" filter is selected
    Given I have the following items in the todo list
      | Write gherkin scenarios |
      | Write step definitions  |
    And I mark the item "Write gherkin scenarios" as completed
    When I select "All" filter
    Then the todo list should display all 2 items

  @test-7
  Scenario: View only active items when "active" filter is selected
    Given I have the following items in the todo list
      | Write gherkin scenarios |
      | Write step definitions  |
    And I mark the item "Write gherkin scenarios" as completed
    When I select "Active" filter
    Then the todo list should display only 1 active item

  @test-8
  Scenario: View only completed items when "completed" filter is selected
    Given I have the following items in the todo list
      | Write gherkin scenarios |
      | Write step definitions  |
    And I mark the item "Write gherkin scenarios" as completed
    When I select "Completed" filter
    Then the todo list should display only 1 completed item

  @test-9
  Scenario: Clear all completed items using "Clear completed" button
    Given I have the following items in the todo list
      | Write test scenarios   |
      | Write step definitions |
    And I mark the item "Write test scenarios" as completed
    When I clear all completed items using clear completed button
    Then the todo list should display only 1 item

  @test-10
  Scenario: Change a item from completed to active
    Given I have the following items in the todo list
      | Write test scenarios   |
      | Write step definitions |
    And I mark the item "Write test scenarios" as completed
    When I change the item "Write test scenarios" from completed to active
    Then the todo count should display "2 items left!"

  @test-11
  Scenario: Change all active items to completed using "toggle-all" button
    Given I have the following items in the todo list
      | Write test scenarios   |
      | Write step definitions |
      | Execute scenarios      |
    And the todo count should display "3 items left!"
    When I change all active items to completed using toggle-all button
    Then the todo count should display "0 items left!"

  @test-12
  Scenario: Change all completed items to active using "toggle-all" button
    Given I have the following completed items in the todo list
      | Write test scenarios   |
      | Write step definitions |
      | Execute scenarios      |
    And the todo count should display "0 items left!"
    When I change all Completed items to active using toggle-all button
    Then the todo count should display "3 items left!"