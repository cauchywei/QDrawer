/**
 * Created by cauchywei on 15/11/9.
 */
public  class  Main<T> {


    public <E> E trans(T t) {
        return (E)t;
    }

    class Node{

    }

    class A extends Node {

    }

    class B extends Node{

    }

    public static void main(String[] args) {
        Node node = new A();

        A a = trans(node);
        B b = trans(node);


    }

}
