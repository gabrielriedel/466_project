# CSC 466 Final Project - Random Forest 

## Data

We are using pitch level data from college baseball. Our goal will be to predict what type of pitch is thrown based off of metrics like pitch velocity, spin, vertical movement, horizontal movement, tilt, and others.

## Random Forest Process

1. Train n decision trees on random subset of features and bootstrapped sample of training data
2. Each tree votes for a classification, majority classification wins
3. Use validation set to tune hyperparameters (# of trees, features per split, tree depth, etc.)
4. Use test set and compute accuracy stats of the model
5. Obtain Variable Importance Scores 

## Design Considerations/Steps (Actionable chunks of work to split up)

1. Be able to read in data.
2. Adjust decision tree implementation from lab 7 to work with our data or generic data. 
3. Create main random forest logic: training n trees on subsets of training data
4. Create hyperparamter tuning logic: use validation data and run random forest with different hyperparams to find optimal hyper params.
5. Create model accuracy metric calculations: given test data, find accuracy, precision, recall, f1 score, loss.

## Results:
```
Trees=10,  Depth=5,  MinSplit=2 -> Val Acc=0.9079
Trees=10,  Depth=5,  MinSplit=5 -> Val Acc=0.9126
Trees=10,  Depth=10, MinSplit=2 -> Val Acc=0.9265
Trees=10,  Depth=10, MinSplit=5 -> Val Acc=0.9321
Trees=10,  Depth=15, MinSplit=2 -> Val Acc=0.9284
Trees=10,  Depth=15, MinSplit=5 -> Val Acc=0.9330
Trees=50,  Depth=5,  MinSplit=2 -> Val Acc=0.9172
Trees=50,  Depth=5,  MinSplit=5 -> Val Acc=0.9079
Trees=50,  Depth=10, MinSplit=2 -> Val Acc=0.9349
Trees=50,  Depth=10, MinSplit=5 -> Val Acc=0.9321
Trees=50,  Depth=15, MinSplit=2 -> Val Acc=0.9312
Trees=50,  Depth=15, MinSplit=5 -> Val Acc=0.9312
Trees=100, Depth=5,  MinSplit=2 -> Val Acc=0.9079
Trees=100, Depth=5,  MinSplit=5 -> Val Acc=0.9153
Trees=100, Depth=10, MinSplit=2 -> Val Acc=0.9321
Trees=100, Depth=10, MinSplit=5 -> Val Acc=0.9312
Trees=100, Depth=15, MinSplit=2 -> Val Acc=0.9349 <- best one 
Trees=100, Depth=15, MinSplit=5 -> Val Acc=0.9321

Best Validation Accuracy: 0.9348837209302325
Test Accuracy with best model: 0.9415584415584416

Confusion Matrix:
Actual\Pred     0       1       2       3       4
0               598     5       3       0       0
1               5       101     1       0       17
2               5       1       157     0       0
3               0       0       0       74      3
4               2       20      0       1       85

Feature Importances:
RelSpeed: 61272.2100
InducedVertBreak: 60527.7550
SpinRate: 43421.8663
HorzBreak: 25038.5387
Tilt: 13272.3305
```
[Presentation Link](https://docs.google.com/presentation/d/1fxwKDUhdskaGK_Ps5NRBn4vG36XBWrHJeXcMbonDqP8/edit?usp=sharing)
