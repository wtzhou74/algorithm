# -*- coding: utf-8 -*-
# =============================================================================
# There are two sorted arrays nums1 and nums2 of size m and n respectively.
# 
# Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
# 
# You may assume nums1 and nums2 cannot be both empty.
# 
# Example 1:
# 
# nums1 = [1, 3]
# nums2 = [2]
# 
# The median is 2.0
# Example 2:
# 
# nums1 = [1, 2]
# nums2 = [3, 4]
# 
# The median is (2 + 3)/2 = 2.5
# =============================================================================

# Time: O(log(min(m, n)))
# Space: O(1)
def findMedianSortedArrays(nums1: list, nums2: list) -> float:
    # found the break point of each list
    # BOTH arrays are SORTED
    if len(nums1) > len(nums2):
        return findMedianSortedArrays(nums2, nums1)
    low, high = 0, len(nums1)
    while low <= high:
        # keep partitionX + partitionY equals half of (m + n)
        partitionX = (low + high) // 2
        partitionY = (len(nums1) + len(nums2) + 1) // 2 - partitionX
            
        # minLeftX - maxLeftX | minRightX - maxRightX
        # minLeftY - maxLeftY | minRightY - maxRightY
        # => maxLeftX, minRightX, maxLeftY, minRightY
        # => WHOLE left side < WHOLE RIGHT side AND WHOLE left = WHOLE RIGHT = HALF of (m + n)
# =============================================================================
#         if partitionX == 0:
#             maxLeftX = float('-inf')
#             minRightX = nums1[0]
#         elif partitionX == len(nums1):
#             minRightX = float('inf')
#             maxLeftX = nums1[partitionX - 1]
#         else:
#             maxLeftX = nums1[partitionX - 1]
#             minRightX = nums1[partitionX]
# =============================================================================
        if partitionX == 0:
            maxLeftX = float('-inf')
        else:
            maxLeftX = nums1[partitionX - 1]
        if partitionX == len(nums1):
            minRightX = float('inf')
        else:
            minRightX = nums1[partitionX]
            
# =============================================================================
#         if partitionY == 0:
#             maxLeftY = float('-INF')
#             minRightY = nums2[0]
#         elif partitionY == len(nums2):
#             minRightY = float('inf')
#             maxLeftY = nums2[partitionX - 1]
#         else:
#             maxLeftY = nums2[partitionY - 1]
#             minRightY = nums2[partitionY]
# =============================================================================
        if partitionY == 0:
            maxLeftY = float('-inf')
        else:
            maxLeftY = nums2[partitionY - 1]
        if partitionY == len(nums2):
            minRightY = float('inf')
        else:
            minRightY = nums2[partitionY]
        
        # final goal
        # WHOLE left <= WHOLE right
        if maxLeftX <= minRightY and maxLeftY <= minRightX:
            if (len(nums1) + len(nums2)) % 2 == 0:
                return (max(maxLeftX, maxLeftY) + min(minRightX, minRightY)) / 2
            else:
                return max(maxLeftX, maxLeftY) # reference to partitionY = (len(nums1) + len(nums2) + 1) // 2 - partitionX
        elif maxLeftX > minRightY:
            # move left
            high = partitionX - 1
        else:
            low = partitionX + 1

# Time: O(m + n)
# Space: O(m + n)
def sol2(nums1 : list, nums2 : list) -> float:
    len1 = len(nums1)
    len2 = len(nums2)
    lenC = len1 + len2
    index1, index2, index3 = 0, 0, 0
    C = [False for i in range(lenC)]
    print(C)
    # add elements to lenC in order (both nums1 and nums2 are sorted)
    while index1 < len1 and index2 < len2:
        if nums1[index1] < nums2[index2]:
            C[index3] = nums1[index1]
            index3 += 1
            index1 += 1
        else:
            C[index3] = nums2[index2]
            index3 += 1
            index2 += 1
    # add remaining elements to C
    while index1 < len1:
        C[index3] = nums1[index1]
        index3 += 1
        index1 += 1
    while index2 < len2:
        C[index3] = nums2[index2]
        index3 += 1
        index2 += 1
        
    # C includes all elements from nums1 and nums2 in order
    indexM1 = (lenC - 1) // 2 # // the results of // operation are rounded down
    indexM2 = lenC // 2
    if lenC % 2 == 0:
        return (C[indexM1] + C[indexM2]) / 2.0
    else:
        return C[indexM2] / 1.0
    
if __name__ == '__main__':
    print(sol2([1, 2], [3, 4]))