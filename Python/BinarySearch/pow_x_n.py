# -*- coding: utf-8 -*-
# =============================================================================
# Implement pow(x, n), which calculates x raised to the power n (xn).
# 
# Example 1:
# 
# Input: 2.00000, 10
# Output: 1024.00000
# Example 2:
# 
# Input: 2.10000, 3
# Output: 9.26100
# Example 3:
# 
# Input: 2.00000, -2
# Output: 0.25000
# Explanation: 2-2 = 1/22 = 1/4 = 0.25
# Note:
# 
# -100.0 < x < 100.0
# n is a 32-bit signed integer, within the range [−231, 231 − 1]
# =============================================================================

def myPow(x: float, n:int) -> float:
    counter = 1
    result = 1
    temp = abs(n)
    while counter <= temp:
        result *= x
        counter += 1
    return 1/result if n < 0 else result


# TIME: O(logn)
# Space: O(logn)
def sol2(x:float, n:int) -> float:
    if n < 0:
        return 1.0 / sol2(x, -n)
    if n == 0:
        return 1
    result = sol2(x, n // 2) # split n into two equal parts
    # even numbers
    if n % 2 == 0:
        return result * result # first half * second half
    # odds numbers
    else:
        return result * result * x
    
def sol3(x:float, n:int) -> float:
    return x **n

def sol4(x:float, n:int) -> float:
    """
    BIT operation
    """
    result = 1
    abs_n = abs(n)
    while abs_n:
        if abs_n & 1: # check odds?
            result *= x
        abs_n >>= 1 # halve the number
        x *= x
    return 1.1/result if n < 0 else result
    
if __name__ == '__main__':
    result = sol4(2.0000, -2)
    print(result)