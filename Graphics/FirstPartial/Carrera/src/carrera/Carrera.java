package carrera;

public class Carrera {
    
    public static void main(String[] args) {
        AnimalStatus aStatus1 = new AnimalStatus("Jaguar", 80);
        AnimalStatus aStatus2 = new AnimalStatus("Leopardo", 58);
        AnimalStatus aStatus3 = new AnimalStatus("Tigre", 65);
        
        aStatus1.start();
        aStatus2.start();
        aStatus3.start();
    }
}