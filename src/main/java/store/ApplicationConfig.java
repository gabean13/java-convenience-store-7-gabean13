package store;

import store.controller.PurchaseController;
import store.service.FileService;
import store.service.InitialSettingService;
import store.service.PurchaseService;
import store.service.ReceiptService;
import store.view.InputValidator;
import store.view.InputView;
import store.view.OutputView;

public class ApplicationConfig {
    public ConvenienceStore createConvenienceStore() {
        FileService fileService = new FileService();
        OutputView outputView = new OutputView();
        InputValidator inputValidator = new InputValidator();
        InputView inputView = new InputView(inputValidator);
        ReceiptService receiptService = new ReceiptService();
        PurchaseService purchaseService = new PurchaseService(inputView, receiptService);
        PurchaseController purchaseController = new PurchaseController(outputView, inputView, purchaseService);
        InitialSettingService initialSettingService = new InitialSettingService(fileService);

        return new ConvenienceStore(purchaseController, initialSettingService);
    }
}
