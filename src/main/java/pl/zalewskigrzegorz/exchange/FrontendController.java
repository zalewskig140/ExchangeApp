package pl.zalewskigrzegorz.exchange;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log
@Controller
public class FrontendController {

    @Autowired
    private ExchangeRatesService exchangeRatesService;
    @Autowired
    private ExchangeWorkService exchangeWorkService;

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("exchangeOptions", new ExchangeOptions());
        model.addAttribute("currencies", exchangeRatesService.getRates());
        return "index";
    }

    @PostMapping("/")
    public String postForm(Model model, @ModelAttribute ExchangeOptions exchangeOptions) {
        log.info("Form submitted with data:" + exchangeOptions);
        exchangeWorkService.convert(exchangeOptions);
        return "redirect:result";
    }

    @GetMapping("/result")
    public String result(Model model, @ModelAttribute ExchangeOptions exchangeOptions) {
        model.addAttribute("mainRate", exchangeWorkService.getMainRate());
        model.addAttribute("otherRates", exchangeWorkService.getOtherRates());
        model.addAttribute("myCurrency", exchangeWorkService.getExchangeOptions().getMyCurrency());
        model.addAttribute("targetCurrency", exchangeWorkService.getExchangeOptions().getTargetCurrency());
        model.addAttribute("myCurrencyName",exchangeWorkService.getMyCurrencyName());
        model.addAttribute("targetCurrencyName", exchangeWorkService.getTargetCurrencyName());
        model.addAttribute("amount", exchangeWorkService.getAmount());
        return "result";
    }

}