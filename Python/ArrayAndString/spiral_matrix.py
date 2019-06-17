# -*- coding: utf-8 -*-
# =============================================================================
# given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
# 
# example 1:
# 
# input:
# [
#  [ 1, 2, 3 ],
#  [ 4, 5, 6 ],
#  [ 7, 8, 9 ]
# ]
# output: [1,2,3,6,9,8,7,4,5]
# example 2:
# 
# input:
# [
#   [1, 2, 3, 4],
#   [5, 6, 7, 8],
#   [9,10,11,12]
# ]
# output: [1,2,3,4,8,12,11,10,9,5,6,7]
# =============================================================================

# Time: O(m * n)
# Space: O(1)
def spiralOrder(matrix) -> list:
    """
    :type matrix:List[List[int]]
    :rtype List[int]
    """

# =============================================================================
#     if not matrix or not matrix[0]:
#         return []
#     
#     col_len, row_len = len(matrix[0]), len(matrix), 
#     col_start, row_start = 0, 0
#     col_end, row_end = col_len - 1, row_len - 1
#     result = []
#     while row_start <= row_end and col_start <= col_end:
#         # move right
#         for i in range(col_start, col_end + 1):
#             result.append(matrix[row_start][i])
#         row_start += 1
#         # move down
#         for i in range(row_start, row_end + 1):
#             result.append(matrix[i][col_end])
#         col_end -= 1
#         # move left
#         if row_start < row_end:
#             for i in range(col_end, col_start - 1, -1):
#                 result.append(matrix[row_end][i])
#             row_end -= 1
#         # move up
#         if col_start < col_end:
#             for i in range(row_end, row_start - 1, -1):
#                 result.append(matrix[i][col_start])
#             col_start += 1
#         row_start, col_end, row_end, col_start
#             = row_start + 1, col_end - 1, row_end - 1, col_start + 1
#             
#     return result
# =============================================================================
    return list(matrix[0]) + spiralOrder(list(zip(*matrix[1:]))[::-1]) if matrix else []
    # transposition : zip(*matrix)

if __name__ == '__main__':
    matrix = [
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
    x = list(zip(*matrix[1:]))[::-1]
    for i in x:
        print(i)
# =============================================================================
#     result = spiralOrder(matrix)
#     for i in result:
#         print(i)
# =============================================================================
