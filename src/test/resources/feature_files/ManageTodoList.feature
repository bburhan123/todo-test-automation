@manage_todo_list
Feature: Manage ToDo List
  In order to remember the things I want to do, as a ToDo MVC user,
  I want to manage my todo list

  @id_001
  Scenario: id_001 - User adds new items in todo list
    Given User is on empty todo list page
    When User adds the following todo item
      | <NewItem>                  |
      | Write gherkin scenarios    |
      | Implement step definitions |
    Then User should see added item in todo list
      | <AddedItem>                |
      | Write gherkin scenarios    |
      | Implement step definitions |
    And Todo count should show "2 items left"

  @id_002
  Scenario: id_002 - User marks the items as completed
    Given User has 3 "active" items in todo list
    When User marks the following item as completed
      | <Item>                     |
      | Write gherkin scenarios    |
      | Implement step definitions |
    Then User should see strikeout on completed item text
      | <CompletedItem>            |
      | Write gherkin scenarios    |
      | Implement step definitions |
    And Todo count should show "1 item left"

  @id_003
  Scenario: id_003 - User marks all items as completed using toggle-all
    Given User has 3 "active" items in todo list
    When User marks all items as completed using toggle-all
    Then User should see strikeout on completed item text
      | <CompletedItem>            |
      | Write gherkin scenarios    |
      | Implement step definitions |
      | Execute scenarios          |
    And Todo count should show "0 items left"

  @id_004
  Scenario: id_004 - User can view all items when all filter is selected
    Given User has 3 "active" items in todo list
    And  User marks the following item as completed
      | <Item>                  |
      | Write gherkin scenarios |
    Then User should see 3 all items in the list

  @id_005
  Scenario: id_005 - User can view only active items when active filter is selected
    Given User has 3 "active" items in todo list
    And  User marks the following item as completed
      | <Item>                  |
      | Write gherkin scenarios |
    When User selects "Active" filter
    Then User should see 2 active items in the list

  @id_006
  Scenario: id_006 - User can view only completed items when completed filter is selected
    Given User has 3 "active" items in todo list
    And  User marks the following item as completed
      | <Item>                  |
      | Write gherkin scenarios |
    When User selects "Completed" filter
    Then User should see 1 completed items in the list

  @id_007
  Scenario: id_007 - User clears an active item from the todo list using destroy button
    Given User has 3 "active" items in todo list
    When User clears the following item using destroy button
      | <Item>                  |
      | Write gherkin scenarios |
    Then User should see 2 all items in the list

  @id_008
  Scenario: id_008 - User clears a completed item from the todo list using destroy button
    Given User has 3 "active" items in todo list
    And  User marks the following item as completed
      | <Item>                     |
      | Write gherkin scenarios    |
      | Implement step definitions |
    When User clears the following item using destroy button
      | <Item>                  |
      | Write gherkin scenarios |
    Then User should see 2 all items in the list

  @id_009
  Scenario: id_009 - User clears all completed items using clear completed button
    Given User has 3 "active" items in todo list
    And  User marks the following item as completed
      | <Item>                     |
      | Write gherkin scenarios    |
      | Implement step definitions |
    When User clears all completed items using clear completed button
    Then User should see 1 all items in the list

  @id_010
  Scenario: id_010 - User can change a item from completed to active
    Given User has 3 "active" items in todo list
    And  User marks the following item as completed
      | <Item>                  |
      | Write gherkin scenarios |
    And Todo count should show "2 items left"
    When User changes the following item from completed to active
      | <Item>                  |
      | Write gherkin scenarios |
    Then Todo count should show "3 items left"

  @id_011
  Scenario: id_011 - User can change all items from completed to active
    Given User has 3 "completed" items in todo list
    And Todo count should show "0 items left"
    When User changes all items from completed to active
    Then Todo count should show "3 items left"

  @id_012
  Scenario: id_012 - User can edit the text of an item
    Given User has 3 "active" items in todo list
    When User edit the text of the following item
      | <OriginalText>    | <EditedText>     |
      | Execute scenarios | Refactor scripts |
    Then User should see edited item in todo list
      | <EditedItem>     |
      | Refactor scripts |