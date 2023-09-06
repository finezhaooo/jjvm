/**
 * @ClassName: test
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/09/02 17:02
 */
public class test {
    public static void main(String[] args) {
        C c = new C();
        c.n();
    }
}

class A {
    public void m() {
        System.out.println("A.m");
    }
}

class B extends A {
    public void m() {
        System.out.println("B.m");
    }
}

class C extends B {
    public void m() {
        System.out.println("C.m");
    }

    public void n() {
        super.m();
    }
}
