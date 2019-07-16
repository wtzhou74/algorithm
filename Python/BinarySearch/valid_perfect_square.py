# -*- coding: utf-8 -*-
# =============================================================================
# Given a positive integer num, write a function which returns True if num is a perfect square else False.
# 
# Note: Do not use any built-in library function such as sqrt.
# 
# Example 1:
# 
# Input: 16
# Output: true
# Example 2:
# 
# Input: 14
# Output: false
# 
# =============================================================================

def isPerfectSquare(num: int) -> bool:
    start, end = 1, num
    while start <= end: # need to compare start == end
        mid = start + (end - start) // 2
        if mid * mid > num:
            end = mid - 1
        elif mid * mid < num:
            start = mid + 1
        else:
            return True
    return False
    

if __name__ == '__main__':
    print(isPerfectSquare(15))
    