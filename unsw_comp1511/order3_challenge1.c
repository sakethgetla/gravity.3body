#include <stdio.h>
int main(){
	int x,y,z,n;
	double a,b,c;
	int max_xy, min_xy, max, mid, min;
	printf("Enter integer: ");
	scanf("%d", &x);
	printf("Enter integer: ");
	scanf("%d", &y);
	printf("Enter integer: ");
	scanf("%d", &z);
	max_xy = (x+y+((x-y)*((2*(x-y)+1)%2)))/2;
	min_xy = (x+y-((x-y)*((2*(x-y)+1)%2)))/2;
	printf("min_xy %d \n",min_xy);
	printf("min %d \n",(min_xy-z));
	printf("min %d \n",(2*(min_xy-z)+1)%2);
	printf("min %d \n",(min_xy-z)*((2*(min_xy-z)+1)%2));
	printf("-2*-1 = %d \n",(-2)*(-1));
	printf("min %d \n",(min_xy+z-((min_xy-z)*(2*(min_xy-z)+1)%2))/2);
	min = (min_xy+z-((min_xy-z)*((2*(min_xy-z)+1)%2)))/2;
	mid = (min_xy+z+((min_xy-z)*((2*(min_xy-z)+1)%2)))/2;
	max = (max_xy+z+((max_xy-z)*((2*(max_xy-z)+1)%2)))/2;
	//printf("afdawfwf %d \n",((x-y)^2)^(1/2) );
	printf("afdawfwf %d \n", z*((2*z+1)%2) );
	//printf("afdawfwf %lf \n",(4)^(1/2) );
	//n*((2*n+1)%2)
	printf("afdawfwf %d \n",max_xy);
	printf("afdawfwf %d \n",min_xy);
	printf("afdawfwf %d \n",min);
	printf("afdawfwf %d \n",((min !=x)&&(max !=x))*x + ((min !=y)&&(max !=y))*y + ((min !=z)&&(max !=z))*z  +(x ==y && x!=z)*x + (y==z && y!=x)*y + (z==x && z!=y)*z + (y==x && x==z)*z);
	printf("mid %d \n",mid);
	printf("max %d \n",max);
	printf("<> %d ",min);
	printf("<> %d ",((min !=x)&&(max !=x))*x + ((min !=y)&&(max !=y))*y + ((min !=z)&&(max !=z))*z  +(x ==y && x!=z)*x + (y==z && y!=x)*y + (z==x && z!=y)*z + (y==x && x==z)*z);
	printf("<> %d \n",max);
	return 0;
}
