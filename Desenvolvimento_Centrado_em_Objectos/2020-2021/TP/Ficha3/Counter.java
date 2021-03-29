public class Counter{
    private int v;

    public int value(){
        return v;
    }

    /**
    * Incrementar uma unidade.
    * @ensures value() == \old(value()) + 1;
    */
    public void inc() {
        v++;
    }

    /**
    * Decrementar uma unidade.
    * @ensures value() == \old(value()) – 1;
    */
    public void dec() {
        v--;
    }   
}