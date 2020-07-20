import numpy as np
from scipy.integrate import dblquad
import math

#定义被积函数
func=lambda y,x:3*(x**2)-8*x+8*y-2*x*y-y**2
#定义三角形三点
points=[(1,5),(3,5),(0,8)]

#二重积分计算
#找出中间点
left_point=min(points)
points.remove(left_point)
right_point=max(points)
points.remove(right_point)
mid_point=points[0]
#判断正三角or倒三角
if mid_point[1]>=left_point[1] or mid_point[1]>=right_point[1]:
    flag=True   #正三角
else:
    flag=False  #倒三角
#分两部分求直线解析式
result=0
err=[]
#对角侧
k0=(left_point[1]-right_point[1])/(left_point[0]-right_point[0])
b0=left_point[1]-left_point[0]*k0
line0=lambda x:k0*x+b0
#左侧
if not mid_point[0]-left_point[0]==0:
    k1=(mid_point[1]-left_point[1])/(mid_point[0]-left_point[0])
    b1=mid_point[1]-mid_point[0]*k1
    line1=lambda x:k1*x+b1
    #求积分
    if k0>k1:
        result1,err1=dblquad(func,left_point[0],mid_point[0],line1,line0)
    else:
        result1,err1=dblquad(func,left_point[0],mid_point[0],line0,line1)
    if not flag:    #倒三角情况
        result1=-result1
    result+=result1
    err.append(err1)
#右侧
if not mid_point[0]-right_point[0]==0:
    k2=(mid_point[1]-right_point[1])/(mid_point[0]-right_point[0])
    b2=mid_point[1]-mid_point[0]*k2
    line2=lambda x:k2*x+b2
    #求积分
    if k0>k2:
        result2,err2=dblquad(func,mid_point[0],right_point[0],line2,line0)
    else:
        result2,err2=dblquad(func,mid_point[0],right_point[0],line0,line2)
    if flag:    #正三角情况
        result2=-result2
    result+=result2
    err.append(err2)
#输出结果
print('result:',result,'\nerr:',err)
