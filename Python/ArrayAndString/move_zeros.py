# -*- coding: utf-8 -*-
# =============================================================================
# Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
# 
# Example:
# 
# Input: [0,1,0,3,12]
# Output: [1,3,12,0,0]
# Note:
# 
# You must do this IN-PLACE without making a copy of the array.
# Minimize the total number of operations.
# =============================================================================

def moveZeroes(nums) -> None:
        """
        Do not return anything, modify nums in-place instead.
        """
        if not nums:
            return ""
        start = -1
        for i in range(len(nums)):
            if nums[i] != 0: # if nums[i]
                start += 1
                nums[start] = nums[i]
        # fill zeros
        for i in range(start + 1, len(nums)):
            nums[i] = 0
        return None

def moveZeroes0(nums) -> None:
    if not nums:
        return None
    start = 0
    for i in range(len(nums)):
        if nums[i]:
            nums[start], nums[i] = nums[i], nums[start] # start points zero
            start += 1
    return None

if __name__ == '__main__':
    moveZeroes0([1,1,0,3,12])