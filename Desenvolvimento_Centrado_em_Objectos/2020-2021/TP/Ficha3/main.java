public static void main(String[] args) {
Counter c1 = new Counter();
SpecialCounter sc1 = new SpecialCounter();
Counter c2 = new SpecialCounter();
SpecialCounter sc2 = new Counter ();
c1.add(2);
sc1.dec();
sc1.add(4);
c2.inc();
c2.add(3);
c2 = sc1;
c2.add(3);
sc1 = c2;
int x = sc1.value();
x = c1.value();
x = c2.value();
sc1.add(-3);
}