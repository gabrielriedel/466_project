# CSC 466 Final Project - Random Forest 

## Data

We are using pitch level data from college baseball. Our goal will be to predict what type of pitch is thrown based off of metrics like pitch velocity, spin, vertical movement, horizontal movement, tilt, and others.

## Random Forest Process

1. Train n decision trees on random subset of training data
2. Each tree votes for a classification, majority classification wins
3. Use validation set to tune hyperparameters (# of trees, features per split, tree depth, etc.)
4. Use test set and compute accuracy stats of the model

## Design Considerations/Steps (Actionable chunks of work to split up)

1. Be able to read in data.
2. Adjust decision tree implementation from lab 7 to work with our data or generic data. 
3. Create main random forest logic: training n trees on subsets of training data
4. Create hyperparamter tuning logic: use validation data and run random forest with different hyperparams to find optimal hyper params.
5. Create model accuracy metric calculations: given test data, find accuracy, precision, recall, f1 score, loss. 