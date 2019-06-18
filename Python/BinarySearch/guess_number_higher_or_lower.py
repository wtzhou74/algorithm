# -*- coding: utf-8 -*-
# =============================================================================
# We are playing the Guess Game. The game is as follows:
# 
# I pick a number from 1 to n. You have to guess which number I picked.
# 
# Every time you guess wrong, I'll tell you whether the number is higher or lower.
# 
# You call a pre-defined API guess(int num) which returns 3 possible results (-1, 1, or 0):
# 
# -1 : My number is lower
#  1 : My number is higher
#  0 : Congrats! You got it!
# Example :
# 
# Input: n = 10, pick = 6
# Output: 6
# =============================================================================

def guessNumber(n) -> int:
    """
    :type n:int
    :rtype int
    """
    start, end = 1, n
    while start <= end:
        mid = start + (end - start) // 2
        if guess(mid) == -1: 
            # MY (API's) NUMBER is lower, NOT the guess number is lower, so move left side
            end = mid - 1
        elif guess(mid) == 1:
            # MY(API's) NUMBER is higher
            start = mid + 1
        else:
            return mid
            