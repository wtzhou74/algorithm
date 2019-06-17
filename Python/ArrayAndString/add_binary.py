# -*- coding: utf-8 -*-
# =============================================================================
# Given two binary strings, return their sum (also a binary string).
# 
# The input strings are both non-empty and contains only characters 1 or 0.
# 
# Example 1:
# 
# Input: a = "11", b = "1"
# Output: "100"
# Example 2:
# 
# Input: a = "1010", b = "1011"
# Output: "10101"
# =============================================================================

from itertools import zip_longest

def addBinary0(a, b) -> str:
    result = ""
    carry = 0
    for i in range(max(len(a), len(b))):
        val = 0
        if i < len(a):
            val += int(a[-(i+1)])
        if i < len(b):
            val += int(b[-(i+1)])
        carry, remainder = divmod(val + carry, 2)
        result += str(remainder)
    if carry:
        result += str(carry)
    
    return result[::-1]

def addBinary(a, b) -> str:
    """
    :type a: str
    :type b: str
    :rtype str
    """
    zip_res = zip(reversed(a), reversed(b))
    result = []
    carry = 0
    for item in zip_res:
        if int(item[0]) + int(item[1]) + carry > 1:
            carry = 1
            # result.append(0) # WRONG e.g. 1 + 1 +1 , should be a + b + carry % 2
            result.append((int(item[0]) + int(item[1]) + carry) % 2)
        else:
            result.append(int(item[0]) + int(item[1]) + carry)
            carry = 0
    if len(a) == len(b):
        if carry > 0:
            result.append(1)
            return str(result[::-1])
        else: return str(result[::-1])
    else:
        if len(a) > len(b):
            r = addOne(a[len(b):], carry)
        else:
            r = addOne(b[len(a), carry])
        r.extend(result) # adding each element to the list and extending the list
        return r
            

def addOne(s, carry) -> list:
    result = []
    for i in s[::-1]:
        if int(i) + carry > 1:
            result.append(0)
            carry = 1
        else:
            result.append(int(i) + carry)
            carry = 0
    if carry > 0:
        result.append(1)
    return result[::-1]
            
def addBinary2(a, b) -> str:
    """
    Applying itertools.zip_longest()
    """
    result = ""
    carry = 0
    for a, b in zip_longest(reversed(a), reversed(b), fillvalue="0"):
        if int(a) + int(b) + carry > 1:
            result += str((int(a) + int(b) + carry) % 2)
            carry = 1
        else:
            result += int(a) + int(b) + carry
            carry = 0
    if carry:
        result += str(carry)
    
    return result[::-1]

# Time: O(n)
# Space: O(1)
def addBinary3(a, b) -> str:
    result = ""
    carry = 0
    for a, b in zip_longest(reversed(a), reversed(b), fillvalue='0'):
        # quotient == carry for BINARY add
        carry, remainder = divmod(int(a) + int(b) + carry, 2)
        result += str(remainder)
    if carry:
        result += str(carry)
        
    return result[::-1]
if __name__ == '__main__':
    a = '11'
    b = '1'
    print(addBinary0(a, b))