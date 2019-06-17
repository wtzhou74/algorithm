# -*- coding: utf-8 -*-
# =============================================================================
# Given a binary array, find the maximum number of consecutive 1s in this array.
# 
# Example 1:
# Input: [1,1,0,1,1,1]
# Output: 3
# Explanation: The first two digits or the last three digits are consecutive 1s.
#     The maximum number of consecutive 1s is 3.
# Note:
# 
# The input array will only contain 0 and 1.
# The length of input array is a positive integer and will not exceed 10,000
# =============================================================================

# Time: O(n * m)
# Space: O(1)
def findMaxConsecutiveOnes(nums) -> int:
    """
    :type nums: List[int]
    :rtype: int
    """
    # TIME LIMIT EXCEED
    if not nums: return 0
    count = 0
    j = 0
    for i in range(0, len(nums)):
        j = i
        temp = 0
        while j < len(nums) and nums[j] == 1:
            temp += 1
            j += 1
        count = max(count, temp)
    return count

def findMaxConsecutiveOnes0(nums) -> int:
    """
    :type nums: Listp[int]
    :rtype int
    """
    if not nums: return []
    result, local_count = 0, 0
    for num in nums:
        local_count = (local_count + 1 if num else 0) # add 0 won't affect previous sum
        result = max(result, local_count) # record max (pre-max, current)
    return result # consecutive 1s, binary list
    
if __name__ == '__main__':
    print(findMaxConsecutiveOnes([1,1,0,1,1,1]))
            
