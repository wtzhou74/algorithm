# -*- coding: utf-8 -*-
# =============================================================================
# Given a sorted array, two integers k and x, find the k closest elements to x in the array. 
# The result should also be sorted in ascending order. If there is a tie, the smaller elements are always preferred.
# 
# Example 1:
# Input: [1,2,3,4,5], k=4, x=3
# Output: [1,2,3,4]
# Example 2:
# Input: [1,2,3,4,5], k=4, x=-1
# Output: [1,2,3,4]
# Note:
# The value k is positive and will always be smaller than the length of the sorted array.
# Length of the given array is positive and will not exceed 10**4
# Absolute value of elements in the array and x will not exceed 10**4
# UPDATE (2017/9/19):
# The arr parameter had been changed to an array of integers (instead of a list of integers). 
# Please reload the code definition to get the latest changes.
# =============================================================================

# NOTICE:
# the problem is how to find the k elements by "removing" n-k elements
# the distance here is arr[i] - arr[j], rather than i - j
# HEAD or TAIL might be the OUTMOST elements to x
# x can be not in array, e.g. x = -1

import bisect

def findClosestElements(arr:list, k:int, x:int) -> list:
    """
    :type arr: List[int]
    :type k: int
    :type x: int
    :rtype: List[int]
    """
    if not arr:
        return []
    # length = len(arr)
    while len(arr) > k:
        if x - arr[0] > arr[-1] - x:
            arr.pop(0) # return the fist element and remove it from original list
        else:
            arr.pop() # the last one
    return arr  

def sol2(arr:list, k:int, x:int) -> list:
    if not arr:
        return []
    # removing n-k elements => set right to len-k
    left, right = 0, len(arr) - k
    # binary search
    while left < right:
        mid = left + (right - left) // 2
        # do not need to calculate the position of x
        # calculate the DISTANCE
        # find the LEFT index
        if x - arr[mid] > arr[mid + k] - x: # keep the distance K
            left = mid + 1
        else:
            right = mid # left can equal right = mid => k elements
    return arr[left:left + k]

# Time: O(logn + k)
# Space: O(1)
def sol3(arr:list, k:int, x:int) -> list:
    if not arr:
        return []
    i = bisect.bisect_left(arr, x) # return the index of the leftmost x in list if it appears in the list
    left, right = i - 1, i # set to i - 1 for minus
    while k:
        if right >= len(arr) or \
            (left >= 0 and abs(arr[left] -x) >= abs(arr[right] - x)):
                left -= 1
        else:
            right += 1
        k -= 1
    return arr[left+1 : right] # add 1 back
    
if __name__ == '__main__':
    result = sol3([1,2,3,4,5], 5, 3)
    print([x for x in result])