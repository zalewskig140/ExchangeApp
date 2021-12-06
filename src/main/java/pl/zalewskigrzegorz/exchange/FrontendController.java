package pl.zalewskigrzegorz.exchange;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log
@Controller
public class FrontendController {

    @Autowired
    private ExchangeRatesService exchangeRatesService;
    @Autowired
    private ExchangeWorkService exchangeWorkService;
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("exchangeOptions", new ExchangeOptions());
        model.addAttribute("currencies", exchangeRatesService.getRates());
        return "index";
    }

    @PostMapping("/")
    public String postForm(Model model, @ModelAttribute ExchangeOptions exchangeOptions) {
        log.info("Form submitted with data:" + exchangeOptions);
        exchangeWorkService.convert(exchangeOptions);
        Transaction transaction = new Transaction(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), exchangeOptions.getMyCurrency(), exchangeOptions.getTargetCurrency(), exchangeOptions.getQuantity(), LocalDateTime.now());
        transactionRepository.save(transaction);
        exchangeWorkService.deleteOldTransaction();
        return "redirect:result";
    }

    @GetMapping("/result")
    public String result(Model model, @ModelAttribute ExchangeOptions exchangeOptions) {
        model.addAttribute("mainRate", exchangeWorkService.getMainRate());
        model.addAttribute("otherRates", exchangeWorkService.getOtherRates());
        model.addAttribute("myCurrency", exchangeWorkService.getExchangeOptions().getMyCurrency());
        model.addAttribute("targetCurrency", exchangeWorkService.getExchangeOptions().getTargetCurrency());
        model.addAttribute("myCurrencyName", exchangeWorkService.getMyCurrencyName());
        model.addAttribute("targetCurrencyName", exchangeWorkService.getTargetCurrencyName());
        model.addAttribute("amount", exchangeWorkService.getAmount());
        model.addAttribute("quantity", exchangeWorkService.getMyCurrencyQuantity());
        return "result";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("history", exchangeWorkService.getTransactionHistory());
        exchangeWorkService.deleteOldTransaction();
        return "history";
    }

}