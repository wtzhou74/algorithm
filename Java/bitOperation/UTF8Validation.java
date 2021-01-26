package bitOperation;

//A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:
//
//For 1-byte character, the first bit is a 0, followed by its unicode code.
//For n-bytes character, the first n-bits are all one's, the n+1 bit is 0, followed by n-1 bytes with most significant 2 bits being 10.
//This is how the UTF-8 encoding would work:
//
//   Char. number range  |        UTF-8 octet sequence
//      (hexadecimal)    |              (binary)
//   --------------------+---------------------------------------------
//   0000 0000-0000 007F | 0xxxxxxx
//   0000 0080-0000 07FF | 110xxxxx 10xxxxxx
//   0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
//   0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
//Given an array of integers representing the data, return whether it is a valid utf-8 encoding.
//
//Note:
//The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data. This means each integer represents only 1 byte of data.
//
//Example 1:
//
//data = [197, 130, 1], which represents the octet sequence: 11000101 10000010 00000001.
//
//Return true.
//It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character.
//Example 2:
//
//data = [235, 140, 4], which represented the octet sequence: 11101011 10001100 00000100.
//
//Return false.
//The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character.
//The next byte is a continuation byte which starts with 10 and that's correct.
//But the second continuation byte does not start with 10, so it is invalid.

public class UTF8Validation {

	public boolean validUtf8(int[] data) {
        if (data == null || data.length == 0)
            return false;
        int bytes = 0;
        for (int i = 0; i < data.length; i++) {
            String bits = Integer.toBinaryString(data[i]); // 不是总是8位的，000011，就只展示11
            bits = bits.length() >= 8 ?
                bits.substring(bits.length() - 8) :  // 只取最低位的8位，即 least significant 8 bits
                "00000000".substring(bits.length() % 8) + bits; // 补上0， 比如 00000+1
            
            System.out.println(bits);
            if (i == 0 || bytes == 0) {
                if (bits.charAt(0) == '0') {
                    bytes = 0;
                } else {
                    int idx = bits.indexOf('0');
                    bytes = idx - 1;
                    if (bytes == 0 || bytes >= 4) // UTF-8 是 [1-4]个bytes，不是 data[]长度限定在 （1，4）
                    							// 一个 UTF-8可以是多个 data elements组合成的
                    	// 【145】，10010001, 不对的，如果是1byte的UTF-8，那就得是0开始，所以这里对 bytes==0的要false
                        return false;
                }
            } else {
                if (bits.charAt(0) == '1' && bits.charAt(1) == '0') {
                    bytes--;
                } else
                    return false;
            }
        }
        return bytes == 0;
    }
	
	//位运算
	//移位后与实现 count 位数
	public boolean validUtf8BitManipulation(int[] data) {
        if (data == null || data.length == 0)
            return false;
        int bytes = 0;
        int mark1 = 1 << 7; // 取最高位 （总8位）
        int mark2 = 1 << 6; // 取次高位
        for (int i = 0; i < data.length; i++) {
            if (bytes == 0) {
                int mask = 1 << 7; // 计算几个1
                while ((mask & data[i]) != 0) {
                    bytes++;
                    mask = mask >> 1;
                }

                if (bytes == 0)
                    continue;
                if (bytes > 4 || bytes == 1) // ！！！！
                    return false;
            } else {
                if (!((data[i] & mark1) != 0 && (data[i] & mark2) == 0)) {
                    return false;
                } 
            }
            bytes--; // 在这里减，自己本身也算1个byte，不是只有 10**** 才算一个 byte的
        }   
            
        return bytes == 0;
    }
}
