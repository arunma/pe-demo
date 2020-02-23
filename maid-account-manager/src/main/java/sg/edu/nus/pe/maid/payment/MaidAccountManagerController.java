package sg.edu.nus.pe.maid.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sg.edu.nus.pe.maid.payment.dto.MaidUsageBill;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(MaidAccountManagerController.BASE_URL)
public class MaidAccountManagerController {

    public static final String BASE_URL = "/api/bill";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(path = "/get")
    @ResponseStatus(HttpStatus.OK)
    public MaidUsageBill getBill() {
        String randomMonth = Month.of(ThreadLocalRandom.current().nextInt(1, 12)).getDisplayName(TextStyle.FULL, Locale.US);
        String randomYear = String.valueOf(ThreadLocalRandom.current().nextInt(2000, 2020));
        return new MaidUsageBill(
                Math.abs(ThreadLocalRandom.current().nextInt()),
                Math.ceil(ThreadLocalRandom.current().nextDouble(500, 1200)),
                String.format("Version 2 of the API - Payment for %s %s", randomMonth, randomYear)
        );
    }

    @GetMapping(path = "/pay")
    @ResponseStatus(HttpStatus.OK)
    public String makePayment() {
        String confirmation;
        try {
            System.err.println("Entering Payment service through Get /pay");
            confirmation = this.restTemplate.getForObject("http://payment-manager-service:8080/api/pay", String.class);
            System.out.println("Confirmation = " + confirmation);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception " + e.getMessage());
            confirmation = "exception here";
        }
        return "Version 2 of Maid Account & " + confirmation;
    }

    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }
}