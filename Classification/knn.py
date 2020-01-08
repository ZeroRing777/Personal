# knn.py
# ---------------

import util
import classificationMethod
import math
import operator

class knnClassifier(classificationMethod.ClassificationMethod):
 
  def __init__(self, legalLabels):
    self.legalLabels=legalLabels
    self.type = "knn"
  
  def train(self, data, labels, validationData, validationLabels):
    self.data=data
    self.labels=labels
  
  
  def classify(self, testData):
    k=1
    guesses=[]
    freq={}
    
    for datum in testData:
        index=0
        distance=[]
        for i in self.data:
          s=0
          for key in datum.keys():
            s+=math.pow(datum[key]-i[key],2)
          d=math.sqrt(s)
          distance.append((d,self.labels[index]))
          index=index+1 
          distance=sorted(distance)[:k]
          "print(distance)"
          for y in self.legalLabels:
             freq[y]=0
          for d in distance:
              for l in self.legalLabels:
                  if d[1]==l:
                      freq[l]+=1
                  "print(freq)"
        "util.raiseNotDefined()"
        guesses.append(max(freq.iteritems(), key=operator.itemgetter(1))[0])
  
    return guesses 
        
        
        
        
        
        
        
        
