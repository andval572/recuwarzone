import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger llegada = new AtomicInteger(0);
    static Semaphore sema = new Semaphore(0);
    static AtomicInteger llegada2 = new AtomicInteger(0);
    static final int jug=10;
    static int encola=0;

    //hilos jugadores

    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            //iniciar el hilo en 0
            Hilos hilo = new Hilos(0);
            //nombre hilo
            hilo.setName(" JUGADOR "+ i );
            hilo.start();
        }
    }
}

class Hilos extends Thread {

    int probabilidad;

    //constructor

    public Hilos(int probabilidad) {

        this.probabilidad = probabilidad;

    }

    //inicia hilos

    @Override
    public void run() {
        Pruebas.prueba1(this);
    }
}

    class Pruebas{

        public static void prueba1(Hilos hilo){

        //dormir

        try {

            Thread.sleep((new Random().nextInt(3) +1)  * 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        //el 10% probabilidad

        hilo.probabilidad = (new Random().nextInt(10) );

        if (hilo.probabilidad == 0){

            System.out.println("EL " +hilo.getName()+ " HA SIDO ELIMINADO DE LA PRUEBA 1 \n");
            return;

        }

        System.out.println("EL " +hilo.getName()+ " HA COMPLETADO LA PRUEBA 1 \n");
        prueba2(hilo);

    }

    public static void prueba2(Hilos hilo){

        //llamada del atomico entero controla cuantos hilos van llegando

        Main.llegada.getAndIncrement();

        if (Main.llegada.get() <= 10){

            System.out.println("EL " +hilo.getName()+ " BIEN HECHO PASAS A LA PRUEBA 2 \n");
            semifinales(hilo);

        }

        else{

            System.out.println("EL " +hilo.getName()+ " NO COMPLETO LA PRUEBA 1 Y SERA ELIMINADO \n");

        }
    }

    public static void semifinales(Hilos hilo){

        System.out.println("EL " +hilo.getName() + " ESTA A LA ESPERA DE EMPEZAR LAS SEMIFINALES \n");
        Main.encola++;

        try {

            // espera hasta que hallan 10

            if (Main.encola == Main.jug)
                Main.sema.release(Main.jug);
                Main.sema.acquire();

                //has la misma prueba
                Thread.sleep((new Random().nextInt(3) +1)  * 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        hilo.probabilidad = (new Random().nextInt(10) );

            if (hilo.probabilidad ==0){
            System.out.println("EL " +hilo.getName()+ " HA SIDO ELIMINADO PRUEBA 2\n");
            return;
        }

        System.out.println("El " +hilo.getName()+ " HA COMPLETADO LA PRUEBA 2 \n");
            ganador(hilo);
    }

    public static void ganador(Hilos hilo){

        Main.llegada2.getAndIncrement();

        if (Main.llegada2.get() == 1){
            System.out.println(" !!!!!MUY BIEN " +hilo.getName()+ " HAS GANADO LA PARTIDA!!!!! \n");
            return;
        }

        else{

                if (Main.llegada2.get() <= 5){
                System.out.println("EL " +hilo.getName()+ " HA TERMINADO EN  "+Main.llegada2.get()+ "ª POSICION \n");
                return;

            }
        }

        System.out.println("EL " +hilo.getName()+ " HA SIDO ELIMINADO DE LA FINAL \n");
    }

}
