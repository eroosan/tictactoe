Feature: TicTacToe

A player succeeds in placing three of their marks in row wins the game

  Scenario: First player wins 
    Given both players are connected to the browsers
      When the first player marks cell-0
      	And the second player marks cell-3
      	And the first player marks cell-1
	  	And the second player marks cell-4
	  	And the first player marks cell-2
      Then the first player wins
      	But the second player loses
     
  Scenario: First player loses 
    Given both players are connected to the browsers
      When the first player marks cell-0
      	And the second player marks cell-3
      	And the first player marks cell-1
	  	And the second player marks cell-4
    	And the first player marks cell-6
	  	And the second player marks cell-5
      Then the first player loses
      But the second player wins
  
  Scenario: Both players draw
  	Given both players are connected to the browsers
      When the first player marks cell-0
      	And the second player marks cell-1
      	And the first player marks cell-2
	  	And the second player marks cell-3
    	And the first player marks cell-4
	  	And the second player marks cell-5
    	And the first player marks cell-7
	  	And the second player marks cell-6
    	And the first player marks cell-8
        Then the game ends with a draw
      