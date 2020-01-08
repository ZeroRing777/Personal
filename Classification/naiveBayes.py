# naiveBayes.py
# -------------
# Licensing Information: Please do not distribute or publish solutions to this
# project. You are free to use and extend these projects for educational
# purposes. The Pacman AI projects were developed at UC Berkeley, primarily by
# John DeNero (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# For more info, see http://inst.eecs.berkeley.edu/~cs188/sp09/pacman.html

import util
import classificationMethod
import math
import random

class NaiveBayesClassifier(classificationMethod.ClassificationMethod):
  """
  See the project description for the specifications of the Naive Bayes classifier.
  
  Note that the variable 'datum' in this code refers to a counter of features
  (not to a raw samples.Datum).
  """
  def __init__(self, legalLabels):
    self.legalLabels = legalLabels
    self.type = "naivebayes"
    self.k = 1 # this is the smoothing parameter, ** use it in your train method **
    self.automaticTuning = False # Look at this flag to decide whether to choose k automatically ** use this in your train method **
    
  def setSmoothing(self, k):
    """
    This is used by the main method to change the smoothing parameter before training.
    Do not modify this method.
    """
    self.k = k

  def train(self, trainingData, trainingLabels, validationData, validationLabels):
    """
    Outside shell to call your method. Do not modify this method.
    """  
      
    # might be useful in your code later...
    # this is a list of all features in the training set.
    self.features = list(set([ f for datum in trainingData for f in datum.keys() ]));
    
    if (self.automaticTuning):
        kgrid = [0.001, 0.01, 0.05, 0.1, 0.5, 1, 5, 10, 20, 50]
    else:
        kgrid = [self.k]
        
    self.trainAndTune(trainingData, trainingLabels, validationData, validationLabels, kgrid)
      

  
  def trainAndTune(self, trainingData, trainingLabels, validationData, validationLabels, kgrid):
    """
    Trains the classifier by collecting counts over the training data, and
    stores the Laplace smoothed estimates so that they can be used to classify.
    Evaluate each value of k in kgrid to choose the smoothing parameter 
    that gives the best accuracy on the held-out validationData.
    
    trainingData and validationData are lists of feature Counters.  The corresponding
    label lists contain the correct label for each datum.
    
    To get the list of all possible features or labels, use self.features and 
    self.legalLabels.
    """
    self.P_FY={}
    C_0={}
    C_1={}
    for y in self.legalLabels:
        for f in self.features:
            C_0[(f,y)]=0.0
            C_1[(f,y)]=0.0
    
    
    index=0 
    
    for i in trainingData:
          C0=0
          C1=0
          "for l in trainingLabels[0:9]:"
          "for y in range(10):"
          y=trainingLabels[index]
          for f in self.features: 
                  if i[f]==0:
                     C_0[(f,y)]=C_0[(f,y)]+1.0
                  if i[f]==1:
                     C_1[(f,y)]=C_1[(f,y)]+1.0
                  "C_0[(f,y)]=C_0[(f,y)]+float(C0)"
                  "C_1[(f,y)]=C_1[(f,y)]+float(C1)"
                  
          index=index+1
          
    "print(P_FY)"
    "print(C_0)"
    "print(C_1)"
    for f in self.features:
        for y in self.legalLabels:
            self.P_FY[(f,0,y)]=(C_0[(f,y)]+0.05)/(C_0[(f,y)]+C_1[(f,y)]+0.1)
            self.P_FY[(f,1,y)]=(C_1[(f,y)]+0.05)/(C_0[(f,y)]+C_1[(f,y)]+0.1)
   
    

    
    
    self.P_Y={}
    n=len(trainingLabels)
    for Y in self.legalLabels:
      count=0
      for l in trainingLabels:   
          if l==Y: 
            count=count+1
      P=float(count)/float(n)
      self.P_Y[Y]=P
          
    "P_Y=float(count)/float(n)"
    "print(P_Y)"
    "util.raiseNotDefined()"
        
  def classify(self, testData):
    """
    Classify the data based on the posterior distribution over labels.
    
    You shouldn't modify this method.
    """
    guesses = []
    self.posteriors = [] # Log posteriors are stored for later data analysis (autograder).
    for datum in testData:
      posterior = self.calculateLogJointProbabilities(datum)
      guesses.append(posterior.argMax())
      self.posteriors.append(posterior)
    return guesses
      
  def calculateLogJointProbabilities(self, datum):
    """
    Returns the log-joint distribution over legal labels and the datum.
    Each log-probability should be stored in the log-joint counter, e.g.    
    logJoint[3] = <Estimate of log( P(Label = 3, datum) )>
    
    To get the list of all possible features or labels, use self.features and 
    self.legalLabels.
    """
    logJoint = util.Counter()
    
    "*** YOUR CODE HERE ***"
    
    "print(self.P_FY[(23,20),0,1])"
    " print(self.P_Y[1])"
    "util.raiseNotDefined()"
    for y in self.legalLabels:
      count=0
      for f in self.features:
           if datum[f]==0:
              count=count+math.log(self.P_FY[f,0,y])
           if datum[f]==1:
              count=count+math.log(self.P_FY[f,1,y])
      logJoint[y]=math.log(self.P_Y[y])+count
    "print(self.P_FY)"
    " print(self.P_Y)"
    "print(logJoint)"
    "util.raiseNotDefined()"
    return logJoint
  
  def findHighOddsFeatures(self, label1, label2):
    """
    Returns the 100 best features for the odds ratio:
            P(feature=1 | label1)/P(feature=1 | label2) 
    
    Note: you may find 'self.features' a useful way to loop through all possible features
    """
    featuresOdds = []
       
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()

    return featuresOdds
    

    
      
