# -*- coding: utf-8 -*-

# =============================================================================
# Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
# 
# Example 1:
# 
# Input: haystack = "hello", needle = "ll"
# Output: 2
# Example 2:
# 
# Input: haystack = "aaaaa", needle = "bba"
# Output: -1
# Clarification:
# 
# What should we return when needle is an empty string? This is a great question to ask during an interview.
# 
# For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
# =============================================================================

# Time: O(n + k)
# space: O(k)
def strStr(haystack, needle) -> str:
    """
    :type haystack:str
    :type needle:str
    :rtype int
    """
    if not needle: return 0
    if len(haystack) < len(needle): return -1
    result = -1
    for i in range(len(haystack)):
        for j in range(len(needle)):
            if haystack[i] == needle[j]:
                # result = i # WRONG
                if len(haystack[i+1:]) < len(needle[j+1:]): return -1
                if isSubStr(haystack[i+1:], needle[j+1:]):
                    return i
            break
                
    return result

def isSubStr(subStr, subNeedle):
    # if len(subStr) < len(subNeedle): return False
    for i in range(len(subNeedle)):
        if subStr[i] == subNeedle[i]:
            continue
        else: return False
    return True

# Time: O(n*k)
# Space: O(k)
def strStr2(haystack, needle):
    if not needle: return 0
    for i in range(len(haystack) - len(needle) + 1):
        if haystack[i:i+len(needle)] == needle:
            return i
    return -1
    
if __name__ == '__main__':
    result = strStr2("mississippi", "sipi")
    print(result)