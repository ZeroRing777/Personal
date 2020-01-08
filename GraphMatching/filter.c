/*
 **********************************************
 *  CS314 Principles of Programming Languages *
 *  Fall 2018                                 *
 *  File: filter.c                            *
 *  Date: 11/25/2018                          *
 **********************************************
 */

#include "filter.h"

void count_unmatched_vertices(int threadId, int threadNum, int nodeNum,
                              int *nodeToProcess, int *res,
                              int *nodeCount)
{
    /* Your Code Goes Here */
   // printf("In count_unmatched_vertices\n");
 //   printf("original nodeCount\n");
    int j=0;

	    int i=threadId;
		nodeCount[i]=0;

	int workchunk=(nodeNum+threadNum-1)/ threadNum;
	int begin=i*workchunk;
	int end=0;
	if((begin+workchunk)<nodeNum) end=begin+workchunk;
	else end=nodeNum;
	
//	printf("end:%d begin:%d\n",end,begin);
	int k=0;
	int V[end-begin];
	for(j=begin;j<end;j++)
	{
		V[k]=nodeToProcess[j];
		k++;
	}
//	printf("res for %d\n",i);
//	for(j=0;j<nodeNum;j++)
//	{
//		printf("%d ",res[j]);
		
//	 } 
//	 printf("\n");
	
  
	
   for(k=0;k<(end-begin);k++)
    {
    	int v=V[k];
    	
    
    	if(res[v]==UNMATCHED)
    	{
    		//if(v<nodeNum)
    		nodeCount[threadId]=nodeCount[threadId]+1;
    	
		}
    	
	}
//	printf("nodeCount\n");

//	for(j=0;j<threadNum;j++)
//	{
//		printf("%d ",nodeCount[j]);
//	}

}

void update_remain_nodes_index(int threadId, int threadNum,
                               int *nodeToProcess, int *startLocations,
                               int *res, 
                               int nodeNum, int *newNodeToProcess)
{
    /* Your Code Goes Here */
   // printf("In update_remain_nodes_index\n");
   int i=threadId;
	int workchunk=(nodeNum+threadNum-1)/ threadNum;
	int begin=i*workchunk;
	int end=0;
	if((begin+workchunk)<nodeNum) end=begin+workchunk;
	else end=nodeNum;
	
	int j=0;
	//printf("end:%d begin:%d\n",end,begin);
	int k=0;
	int V[end-begin];
	for(j=begin;j<end;j++)
	{
		V[k]=nodeToProcess[j];
		k++;
	}
    for(k=0;k<end-begin;k++)
    {
    	int v=V[k];
    	if(res[v]==UNMATCHED)
    	{
    		int offset=startLocations[i]++;
    		newNodeToProcess[offset]=v; 
		}
    	
	}
//	printf("newNode\n");
//	for(j=0;j<nodeNum;j++)
//	{
//		printf("%d ",newNodeToProcess[j]);
//	}
      
}
