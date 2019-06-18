# -*- coding: utf-8 -*-
# =============================================================================
# Implement int sqrt(int x).
# 
# Compute and return the square root of x, where x is guaranteed to be a non-negative integer.
# 
# Since the return type is an integer, the decimal digits are truncated and only the integer part of the result is returned.
# 
# Example 1:
# 
# Input: 4
# Output: 2
# Example 2:
# 
# Input: 8
# Output: 2
# Explanation: The square root of 8 is 2.82842..., and since 
#              the decimal part is truncated, 2 is returned.
# =============================================================================

# Time: O(logn) = => n * 1/2 * 1/2 *... = 1 ==> binary search can be used for doing SQRT()
# Space: O(1)

def mySqrt(x) -> int:
    if x < 2:
        return 0
    left, right = 1, x // 2
    while left <= right:
        mid = left + (right - left) // 2
        if mid > x / mid:
            right = mid - 1
        else:
            left = mid + 1
        
    return left - 1

# Time: O(√ n)
def mySqrt0(x) -> int:
    if x < 2:
        return x
    result, i = 1, 1
    # starting from 1, try all numbers untill i*i is greater than or equal to x
    while result < x:
        i += 1
        result = i * i
        
    return i - 1

# Time: O(logn)
def mySqrt1(x) -> int:
    if x < 2:
        return x
    start, end = 1, x
    result = 0
    while start <= end:
        mid = start + (end -start) // 2
        # x is perfect square
        if mid * mid == x:
            return mid
        # since we need FLOOR, we update answer when mid * mid is smaller than x
        # and move closer to sqrt(x)
        elif mid * mid < x:
            start = mid + 1
            result = mid
        else:
            end = mid - 1
    return result

# all can be done by int(math.sqrt(x))
###    int(x ** (1/2))
if __name__ == '__main__':
    print(mySqrt1(8))