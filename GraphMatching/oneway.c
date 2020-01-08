/*
 **********************************************
 *  CS314 Principles of Programming Languages *
 *  Fall 2018                                 *
 *  File: oneway.c                            *
 *  Date: 11/25/2018                          *
 **********************************************
 */
#include "oneway.h"
#include "utils.h"

void extend_one_hand(int threadId, int threadNum, GraphData graph,
                     int nodeNum, int *nodeToProcess,
                     int *res, int *strongNeighbor)
{
    /* Your Code Goes Here */
  //  printf("In extend_one_hand\n");
    //printf("ID %d Num %d nodeNum %d\n", threadId,threadNum,nodeNum);
   // printf("nodeToProcess\n");
   // int i=0; 
   // for (i = 0 ; i < nodeNum; i++) 
   // {
   // 	printf("%d ",nodeToProcess[i]);
//	}
//	printf("res:\n");
   //  for (i = 0 ; i < graph.nNodes; i++) 
   // {
   // 	printf("%d ",res[i]);
//	}
	
	int i=threadId;
	int workchunk=(nodeNum+threadNum-1)/ threadNum;
	int begin=i*workchunk;
	int end=0;
	if((begin+workchunk)<nodeNum) end=begin+workchunk;
	else end=nodeNum;
	int j=0;
//	printf("end:%d begin:%d\n",end,begin);
	int k=0;
	int V[end-begin];
	for(j=begin;j<end;j++)
	{
		V[k]=nodeToProcess[j];
		k++;
	}

	for(k=0;k<(end-begin);k++)//remember to revise it
	{
		int v=V[k];
	//	printf("vertex %d\n",vertex);
		int degree=graph.degree[v];
	//	printf("degree %d\n",degree);
		int offset=graph.offset[v];
	//	printf("offset %d\n",offset);
		int NL[degree];
		for(j=0;j<degree;j++)
		{
			NL[j]=graph.index[offset];
	//		printf("NL %d\n",NL[j]);
			offset++;
		}
		
		int count=0;
    	for(j=0;j<degree;j++)
		{
			int u=NL[j];
			if((res[u]!=UNMATCHED)&&(res[v]==UNMATCHED)&&(res[v]!=-2))
			{
				count++;
			}
		}
//		printf("vertex:%d count:%d degree:%d\n",v,count,degree);
		if(count==degree)
		{
			res[v]=-2;
		}
		
		
		
		for(j=0;j<degree;j++)
		{
			int u=NL[j];
			if(u<graph.nNodes)
			{
			if(res[u]==UNMATCHED)
			{
				strongNeighbor[v]=u;
				break;
			}}
		}
		
		
		
		
	
	}
  	//	printf("StrongNeighbor\n");
	//	for(j=0;j<graph.nNodes;j++)
	//	{
	//		printf("%d ",strongNeighbor[j]);
	//	}
}

void check_handshaking(int threadId, int threadNum,
                       int nodeNum, int *nodeToProcess,
                       int *strongNeighbor, int *res)
{
    /* Your Code Goes Here */
   // printf("In check_handshaking\n");
   // printf("ID %d Num %d nodeNum %d\n", threadId,threadNum,nodeNum);
  //  printf("nodeToprocess\n");
   // int i=0; 
    
   // for (i = 0 ; i < nodeNum; i++) 
  //  {
   // 	printf("%d ",nodeToProcess[i]);
//	}

	int i=threadId;
	int workchunk=(nodeNum+threadNum-1)/ threadNum;
	int begin=i*workchunk;
	int end=0;
	if((begin+workchunk)<nodeNum) end=begin+workchunk;
	else end=nodeNum;

	int j=0;
//	printf("end:%d begin:%d\n",end,begin);
	int k=0;
	int V[end-begin];
	for(j=begin;j<end;j++)
	{
		V[k]=nodeToProcess[j];
		k++;
	}
	for(k=0;k<(end-begin);k++)
	{
	    int v=V[k];
		int s=strongNeighbor[v];
		if(v==strongNeighbor[s])
		{
			res[v]=s;
		}
		
	
		
	}
		
	
  // printf("res:\n"); 
    
    
//	for(j=0;j<9;j++)
//	{
//		printf("%d ",res[j]);
//	}
	

}


