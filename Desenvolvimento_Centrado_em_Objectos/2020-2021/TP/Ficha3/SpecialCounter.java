public class SpecialCounter extends Counter {
    /**
    * Adicionar um valor.
    * @param i valor a adicionar.
    * @requires i >= 0;
    * @ensures value() == \old(value()) + i;
    */
    public void add(int i) {
        while(i > 0) {
            inc(); i--;
        }
    }

    public void remove(int i) {
        while(i>0){
            dec();
            i--;
        }
    }

    public void reset(){
        if(value()>0){
            remove(value());
        }else{
            add(value());
        }
        
    }
}