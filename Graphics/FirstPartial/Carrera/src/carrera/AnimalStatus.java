package carrera;

import java.util.Random;

public class AnimalStatus extends Thread{
    String name;
    int velocity;
    int meters;
    
    public AnimalStatus(String name, int velocity){
        this.name = name;
        this.velocity = velocity;
        this.meters = 0;
    }
    
    @Override
    public void run(){
        System.out.println("Inicia el " + name + " con una velocidad de: " + velocity + "mts/s");
        
        while(meters <= RaceStatus.goal){
            try{
                sleep(500 + new Random().nextInt(500));
                System.out.println(name + " => " + meters);
            }catch(Exception ex){ System.out.println(ex); }
            
            meters += velocity;
        }
        
        System.out.println("[" + name + " en " + RaceStatus.animalPosition + " lugar, recorriendo: " + meters + "mts]");
        RaceStatus.animalPosition++;
    }
}
