# -*- coding: utf-8 -*-
# =============================================================================
# 
# # In a given integer array nums, there is always exactly one largest element.
# #
# # Find whether the largest element in the array is at least twice as much as every other number in the array.
# #
# # If it is, return the index of the largest element, otherwise return -1.
# #
# # Input: nums = [3, 6, 1, 0]
# # Output: 1
# # Explanation: 6 is the largest integer, and for every other number in the array x,
# # 6 is more than twice as big as x.  The index of value 6 is 1, so we return 1.
# #
# # Input: nums = [1, 2, 3, 4]
# # Output: -1
# # Explanation: 4 isn't at least as big as twice the value of 3, so we return -1.
# =============================================================================

def dominantIndex(nums : list) -> int:
    """
    :param nums
    rtype: int
    """
    if not nums: return -1 # empty list
    max_num = max(nums)
    if all(max_num >= x * 2 for x in nums if x != max_num):
        return nums.index(max_num) # get the index of specifized item
    return -1
 

def dominantIndex2(nums: list) -> int:
    if not nums: return -1
    if len(nums) == 1: return 0
    
    max_num = max(nums)
    max_index = nums.index(max_num)
    # remove max number
    nums.remove(max_num)
    
# =============================================================================
#     second_max = max(nums)
#     if max_num >= second_max * 2:
#         return max_index
#     else: pass
#     
#     return -1
# =============================================================================
    for num in nums:
        if 2 * num > max_num:
            return -1
        else: pass
    
    return max_index

if __name__ == '__main__':
    nums = [3, 6, 1, 0]
    # result = dominantIndex(nums)
    result = dominantIndex2(nums)
    print(result)   