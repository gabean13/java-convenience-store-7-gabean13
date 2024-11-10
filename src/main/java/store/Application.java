package store;

public class Application {
    private final ConvenienceStore convenienceStore;

    public Application() {
        this.convenienceStore = new ApplicationConfig().createConvenienceStore();
    }

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        convenienceStore.run();
    }
}
