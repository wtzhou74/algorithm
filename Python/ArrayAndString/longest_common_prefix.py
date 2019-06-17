# -*- coding: utf-8 -*-
# =============================================================================
# Write a function to find the longest common prefix string amongst an array of strings.
# 
# If there is no common prefix, return an empty string "".
# 
# Example 1:
# 
# Input: ["flower","flow","flight"]
# Output: "fl"
# Example 2:
# 
# Input: ["dog","racecar","car"]
# Output: ""
# Explanation: There is no common prefix among the input strings.
# Note:
# 
# All given inputs are in lowercase letters a-z.
# =============================================================================

# Time: O(n * k)
# Space: O(1)
def longestCommonPrefix(strs: list) ->str:
    if not strs: return ""
    shortest_str = strs[0]
    for s in strs:
        if len(shortest_str) > len(s):
            shortest_str = s
    result = ""
    for i in range(len(shortest_str)):
        for sl in strs:
            if shortest_str[i] == sl[i]:
                continue;
            else: return result
        result += shortest_str[i]
    return result


# Time: O(n * k) k is the length of the common prefix
# space: O(k)
def longestCommonPrefix1(strs: list) -> str:
    if not strs: return ""
    prefix = ""
    zip_res = zip(*strs)
    for element in zip_res:
        if all(element[0] == x for x in element):
            prefix += element[0]
        else: break
    return prefix

# Time: O(n * k)
# Space: O(1)
def longestCommonPrefix2(strs: list) -> str:
    if not strs: return ""
    prefix = ""
    for i in range(len(strs[0])):
        for string in strs[1:]:
            # REVERSE THINKING OF ALL
            if i >= len(string) or string[i] != strs[0][i]:
                return prefix # return strs[0][:i]
        prefix += strs[0][i]
    return prefix # return strs[0]

if __name__ == '__main__':
     result = longestCommonPrefix2(["flower","flow","flight"])
     for i in result:
         print(i)
            
        
        
        