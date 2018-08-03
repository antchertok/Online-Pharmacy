package test.java.service;

public class Test {
    protected int x;
    Test(int x1) {x=x1;}
}

abstract class Tester{
    Tester(int a){};
    protected abstract boolean hello();
    @Override
    public boolean equals(Object o){
        return true;
    }
}

class RealTester {
//    @Override
//    public void hello(){
//        System.out.println(1);
//    }
//
//    @Override
//    public boolean hello() {
//        return false;
//    }
    Tester tester = new Tester(14) {
    @Override
        protected boolean hello() {
            return false;
        }
    };

    void p(Object... o){
        System.out.println("Obj");
    }

    void p(Integer... i){
        System.out.println("Int[]");
    }

//    void p(int... i){
//        System.out.println("int");
//    }

    public static void main(String[] args) {
        for(int i=0, j=10; i<=j; i++,--j) {
            if(i<j-2) {
                System.out.println(2);
            }}
            Owner.Inner inner = new Owner.Inner();
        RealTester tester = new RealTester();
        Integer[] i = {2, 4};
        tester.p( 5, 7);
    }
}

class Owner {
    static int b = 3;
    public static class Inner {
        int c = 1;
    }}
