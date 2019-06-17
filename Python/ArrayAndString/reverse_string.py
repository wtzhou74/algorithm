# -*- coding: utf-8 -*-
# =============================================================================
# Write a function that reverses a string. The input string is given as an array of characters char[].
# 
# Do not allocate extra space for another array, you must do this by modifying the input array IN-PLACE with O(1) extra memory.
# 
# You may assume all the characters consist of printable ascii characters.
# 
#  
# 
# Example 1:
# 
# Input: ["h","e","l","l","o"]
# Output: ["o","l","l","e","h"]
# Example 2:
# 
# Input: ["H","a","n","n","a","h"]
# Output: ["h","a","n","n","a","H"]
# =============================================================================

# Time O(n)
# Space 1
def reverse_string(s) -> None:
    """
    Do not return anything, modify s in-place instead.
    """
    """
    :type s:List[str]
    :rtype: None
    """
    i, j = 0, len(s) - 1
    while i < j:
#        temp = s[i]
#        s[i] = s[j]
#        s[j] = temp
        s[i], s[j] = s[j], s[i]
        i += 1
        j -= 1

def reverse_string0(s) -> str:
    """
    :type s: str
    :rtype str
    """
    string = list(s)
    i, j = 0, len(s) -1
    while i < j:
        string[i], string[j] = string[j], string[i]
        i += 1
        j -= 1
    return "".join(string) # string is a list of char

def reverse_string1(s) -> str:
    """
    :type s:List[str]
    :rtype: str
    """
    # s[:] = s[::-1]
    # s = reversed(s) # returned a new list without affecting original list
    s.reverse() # reverse original list in-place
    return s

if __name__ == '__main__':
    for i in reverse_string1(["h","e","l","l","o"]):
        print(i)
    