import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName : Q
 * @Description :
 * @Author : wangjie
 * @Date: 2021/4/28-18:28
 */
public class Q {
    /*数组M=[1,2,3,4,5,6,15,20,30,40,50]
取两个数，使加和最接近与27*/
    public static void main(String[] args) {
        Integer[] M = new Integer[]{1,2,3,4,5,6,15,21,30,40,50};
        Q q = new Q();
        Integer[] res = q.reduce(M, 27);
        System.out.println(res[0]);
        System.out.println(res[1]);
        System.out.println(res[2]);
    }


    public Integer[] reduce(Integer[] bs, int c){
        Integer[] reds = new Integer[]{100000, 0, 1};
        for (int i = 0; i < bs.length; i++) {
            for(int j = i+1 ; j<bs.length; j++){
                Integer[] tmps = reduce1(bs[i], bs[j], c);
                if(tmps[0] < reds[0]) {
                    reds = tmps;
                }
            }
        }
        return reds;
    }

    private Integer[] reduce1(int a, int b, int c){
        System.out.println("计算：" + a + "---" + b + "---" + c);
        int res = c - (a + b);
        return new Integer[]{Math.abs(res), a, b};
    }
}
