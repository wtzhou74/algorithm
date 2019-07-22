# -*- coding: utf-8 -*-
# =============================================================================
# Given two arrays, write a function to compute their intersection.
# 
# Example 1:
# 
# Input: nums1 = [1,2,2,1], nums2 = [2,2]
# Output: [2]
# Example 2:
# 
# Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
# Output: [9,4]
# Note:
# 
# Each element in the result must be unique.
# The result can be in any order.
# =============================================================================

def intersection(nums1:list, nums2:list) -> list:
    set1 = set(nums1)
    set2 = set(nums2)
    print(type(set1 & set2)) # set
    return list(set1 & set2) # set1.intersection(set2)

def sol2 (nums1: list, nums2: list) -> list:
    if len(nums1) > len(nums2):
        sol2(nums2, nums1)
    lookup = set()
    for i in nums1:
        lookup.add(i)
        
    res = []   
    for i in nums2:
        if i in lookup:
            res += i,
            lookup.discard(i) #discard will NOT raise an error if the item to remove does not exist.
            # while remove() will raise an error
            
    return res

# TWO POINTERS SOLUTION
# Time: O(max(m, n) * log(max(m,n)))
# Space: O(1)
def sol3 (nums1: list, nums2: list) -> list:
    nums1.sort()
    nums2.sort()
    res = []
    it1, it2 =0, 0
    while it1 < len(nums1) and it2 < len(nums2):
        if nums1[it1] > nums2[it2]:
            it2 += 1
        elif nums1[it1] < nums2[it2]:
            it1 += 1
        else:
            if not res or res[-1] != nums1[it1]:
                # res.append( nums1[it1])
                res += nums1[it1], # HAVE TO add ',', otherwise, you will get "TypeError: 'int' object is not iterable"
            it1 += 1
            it2 += 1
    return res


# FOR REFERENCE
# BINARY SEARCH
# Time: O(max(m, n) * log(max(m, n)))
# Space: O(1)
def sol4(nums1: list, nums2: list) -> list:
    if len(nums1) > len(nums2):
        return intersection(nums2, nums1)
    
    def binary_search(compare, nums, left, right, target):
        while left < right:
            mid = left + (right - left) / 2
            if compare(nums[mid], target):
                right = mid
            else:
                left = mid + 1
        return left
    nums1.sort(), nums2.sort()
    res = []
    left = 0
    for i in nums1:
        left =binary_search(lambda x, y: x >= y, nums2, left, len(nums2), i)
        if left != len(nums2) and nums2[left] == i:
            res += i,
            left = binary_search(lambda x, y: x > y, nums2, left, len(nums2), i)
    return res

if __name__ == '__main__':
    result = sol4([9,4,9,8,4], [4,9,5])
    for i in result:
        print(i)