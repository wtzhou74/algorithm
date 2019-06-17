# -*- coding: utf-8 -*-
# =============================================================================
# Given a matrix of M x N elements (M rows, N columns), return all elements of the matrix in diagonal order as shown in the below image.
# 
# Example:
# 
# Input:
# [
#  [ 1, 2, 3 ],
#  [ 4, 5, 6 ],
#  [ 7, 8, 9 ]
# ]
# 
# Output:  [1,2,4,7,5,3,6,8,9]
# =============================================================================

def findDiagonalOrder(matrix) -> list:
    if not matrix or not matrix[0]:
        return []
    
    col_len = len(matrix[0])
    row_len = len(matrix)
    
    result = []
    col, row = 0, 0
    for i in range(col_len * row_len):
        result.append(matrix[row][col]) # matrix[row, col] in numpy
        if (row + col) % 2 == 0:
            # result.append(matrix[row][col])
            if col == col_len - 1: # rightmost board
                row += 1
            elif row == 0: # uppermost board
                col += 1
            else:
                col += 1
                row -= 1
        else:
            # result.append(matrix[row][col])
            if row == row_len - 1: # downmost
                col += 1
            elif col == 0: # leftmost
                row += 1
            else:
                row += 1
                col -= 1
    return result
        
if __name__ == '__main__':
    matrix = [
              [ 1, 2, 3 ],
              [ 4, 5, 6 ],
              [ 7, 8, 9 ]
            ]
    result = findDiagonalOrder(matrix)
    print(type(result))
    for i in result:
        print(i)