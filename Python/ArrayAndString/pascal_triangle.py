# -*- coding: utf-8 -*-
# =============================================================================
# Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
# Example:
# 
# Input: 5
# Output:
# [
#      [1],
#     [1,1],
#    [1,2,1],
#   [1,3,3,1],
#  [1,4,6,4,1]
# ]
# =============================================================================

def generate(numRows):
    """
    :type numRows:int
    :rtype List[List[int]]
    """
 
     if numRows == 0:
         return []
     result = []
     for i in range(numRows):
#         if i == 0:
#             result.append([1]) ## append 1
#         elif i == 1:
#             result.append([1, 1]) ## append 1, AND append 1 at the end
#         else:
             level = []
             level.append(1) # first 1  ## append 1
             for j in range(1, i):
                 level.append(result[i - 1][j -1] + result[i - 1][j])
#              level.append(1) # last 1  ## append 1 at tge end
             if i > 0:
                 level.append(1) # add last 1
             result.append(level)
     return result

 

if __name__ == '__main__':
    result = generate(5)
    for i in result:
        for j in result[i]:
            print(j)
        