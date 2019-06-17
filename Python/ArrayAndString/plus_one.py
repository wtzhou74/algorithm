# -*- coding: utf-8 -*-
# =============================================================================
# Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
# 
# The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.
# 
# You may assume the integer does not contain any leading zero, except the number 0 itself.
# 
# Example 1:
# 
# Input: [1,2,3]
# Output: [1,2,4]
# Explanation: The array represents the integer 123.
# Example 2:
# 
# Input: [4,3,2,1]
# Output: [4,3,2,2]
# Explanation: The array represents the integer 4321.
# =============================================================================

# time: O(n)
# space: O(1)

def plusOne(digits: list) -> list:
    
    if not digits: return [] # empty list
    for i in reversed(range(len(digits))):
        if digits[i] < 9:
            digits[i] = digits[i] + 1
            return digits
        else:
            digits[i] = 0
    
    digits[0] = 1
    digits.append(0)
    
    return digits


# time O(n)
# space O(n)
    
def plusOne2(digits: list) -> list:
    """
    :type: list
    :rtype: list
    """
    carry = 1;
    result = digits[::-1] # reverse list
    for i in range(len(result)):
# =============================================================================
#         digit = result[i] + carry
#         result[i] = digit % 10 # set digits[i] value
#         # reset carry
#         if digit > 9 :
#            carry = 1
#         else: carry = 0
# =============================================================================
        result[i] += carry
        carry, result[i] = divmod(result[i], 10)
       
        
    if carry > 0:
        result.append(carry)
    
    return result[::-1]
        
if __name__ == '__main__':
    result = plusOne2([9, 9, 9])
    for i in result:
        print(i)
    
    