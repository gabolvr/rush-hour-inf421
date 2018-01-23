# rush-hour-inf421
Programming project for the algorithms course (INF421), developing an efficient solver for rush hour puzzles.

# How to use it

## Start a game

RushHour game = new RushHour("file-name.txt");

## Show initial state

game.initial_state.printState();

## Find the number of steps to solve the game in the brute force way

int steps = game.solveSteps();

or

int steps = game.solve(false);

## Find the number of steps to solve the game with the first heuristic

int steps = game.solveStepsHeuristic();

or

int steps = game.solveHeuristic(false);

## Find the number of steps to solve the game with the second heuristic

int steps = game.solveStepsHeuristicAlt();

or

int steps = game.solveHeuristicAlt(false);

## Show the steps to solve the game in the brute force way

int steps = game.solve();

or

int steps = game.solve(true);

## Show the steps to solve the game with the first heuristic

int steps = game.solveHeuristic();

or

int steps = game.solveHeuristic(true);

## Show the steps to solve the game with the second heuristic

int steps = game.solveHeuristicAlt();

or

int steps = game.solveHeuristicAlt(true);

