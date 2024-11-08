package store;

import store.service.FileService;
import store.service.InitialSettingService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    private final ConvenienceStore convenienceStore;
    private final InitialSettingService initialSettingService;
    private final FileService fileService;
    private final OutputView outputView;
    private final InputView inputView;

    public Application() {
        this.fileService = new FileService();
        this.outputView = new OutputView();
        this.inputView = new InputView();
        this.initialSettingService = new InitialSettingService(fileService);
        this.convenienceStore = new ConvenienceStore(initialSettingService, outputView, inputView);
    }

    public void run() {
        convenienceStore.run();
    }

    public static void main(String[] args) {
        new Application().run();
    }
}
