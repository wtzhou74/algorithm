# -*- coding: utf-8 -*-
# =============================================================================
# Given a string, you need to reverse the order of characters in each word within
# a sentence while still preserving whitespace and initial word order.
# 
# Example 1:
# Input: "Let's take LeetCode contest"
# Output: "s'teL ekat edoCteeL tsetnoc"
# Note: In the string, each word is separated by single space and there will not be any extra space in the string.
# =============================================================================

def reverseWords(s) -> str:
    if not s:
        return ""
    words = s.split()
    return " ".join([word[::-1] for word in words])
    
    # reversed(word) returns a reverse ITERATOR
    # return " ".join([reversed(word) for word in words]) # TypeError: sequence item 0: expected str instance, reversed found
    
def reverseWords0(s) -> str:
    """
    :type s:str
    :rtype str
    """
    def reverse(s, begin, end):
        for i in range((end-begin) // 2):
            s[begin + i], s[end - 1- i] = s[end - 1 - i], s[begin + i]
            
    s, i = list(s), 0
    for j in range(len(s) + 1):
        if j == len(s) or s[j] == ' ': # check the word 
            reverse(s, i, j)
            i = j + 1
    return "".join(s)

if __name__ == '__main__':
    print(reverseWords("Let's take LeetCode contest"))