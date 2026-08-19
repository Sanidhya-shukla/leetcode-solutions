class Solution {
    public int mySqrt(int x) {
        int left = 1;
        int answer = 0;
        int right = x/2;

        if (x == 0 || x == 1) {
            return x;
        }

        while (left <= right) {
            int mid = (left + right)/2;
            if ((long)mid * mid <= x) {
                answer = mid;
                left = mid+1;
            }
            else {
                right = mid-1;
            }
        }
        return answer;
    }
}