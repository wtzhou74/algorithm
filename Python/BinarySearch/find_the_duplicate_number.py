# -*- coding: utf-8 -*-
# =============================================================================
# Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.
# 
# Example 1:
# 
# Input: [1,3,4,2,2]
# Output: 2
# Example 2:
# 
# Input: [3,1,3,4,2]
# Output: 3
# Note:
# 
# You must not modify the array (assume the array is read only).
# You must use only constant, O(1) extra space.
# Your runtime complexity should be less than O(n2).
# There is only one duplicate number in the array, but it could be repeated more than once.
# =============================================================================

# Time: O(n)
# Space: O(1)
def findDuplicate(nums: list) -> int:
    # Treat each (key, value) pair of the array as the (pointer, next) node of the linked list,
    # thus the duplicated number will be the begin of the cycle in the linked list.
    # Besides, there is always a cycle in the linked list which
    # starts from the first element of the array.
    slow = nums[0]
    fast = nums[nums[0]]
    while slow != fast:
        slow = nums[slow]
        fast = nums[nums[fast]]
        
    # when fast == slow, the slow is not the begining of cycle
    # reset the fast, when fast reach slow, then the point is the begining of cycle
    fast = 0
    while slow != fast:
        slow = nums[slow]
        fast = nums[fast]

    return slow

# Time: O(n)
# Space: O(n)
def sol2 (nums: list) -> int:
    numbers = [0] * (len(nums) - 1)
    for i in nums:
        if numbers[i - 1] != 0:
            return i
        else:
            numbers[i - 1] += 1
            

def sol3 (nums: list) -> int:
    result = set(nums)
    print(type(nums))
# =============================================================================
#    result = set()
#    for i in nums:
#         if i in result:
#             return i
#         else:
#             result.add(i)
# =============================================================================
    # return sum(nums) - sum(result) # WRONG IF THERE ARE DUPLICATE REPEATS, e.g. [2, 2, 2, 2]
    return (sum(nums) - sum(result)) // (len(nums) - len(result))

# Time: O(nlogn)
# Space: O(1)
def sol4(nums: list) -> int:
    left, right = 1, len(nums) - 1
    while left <= right:
        mid = left + (right - left) // 2
        # get count of num <= mid
        count = 0
        for num in nums:
            if num <= mid:
                count += 1
        if count > mid:
            right = mid - 1
        else:
            left = mid + 1
    return left

if __name__ == '__main__':
    print(sol3([1,3,4,2,2]))      