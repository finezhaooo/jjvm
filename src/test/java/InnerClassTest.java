/**
 * @ClassName: Test2
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/23 15:37
 */
public class InnerClassTest{
    class InnerClass{
        public void test(){
            System.out.println("InnerClassTest.InnerClass.test");
        }
    }


    public static void main(String[] args) {
        InnerClassTest innerClassTest = new InnerClassTest();
        InnerClass innerClass = innerClassTest.new InnerClass();
        innerClass.test();
    }
}
