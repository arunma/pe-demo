package sg.edu.nus.pe.maid.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.pe.maid.payment.dto.PaymentConfirmation;

import java.util.Date;

@RestController
@RequestMapping(PaymentManagerController.BASE_URL)
public class PaymentManagerController {

    public static final String BASE_URL = "/api";
    private static final Logger log = LoggerFactory.getLogger(PaymentManagerController.class);

    @PostMapping(path = "/payobj")
    @ResponseStatus(HttpStatus.OK)
    public PaymentConfirmation makePayment(Integer id, Double amount) {
        return new PaymentConfirmation(
                id,
                amount,
                String.format("Version 1 - Payment complete by calling Payment service. Id %s Amount %s", id, amount)
        );
    }

    @GetMapping(path = "/pay")
    @ResponseStatus(HttpStatus.OK)
    public String makePayment() throws InterruptedException {
        String currentTime = new Date().toString();
        log.info("Returning payment confirmation for Version 1 @ "+ currentTime);
        Thread.sleep(1000);
        return "Version 1 - Payment complete by calling Payment service @ "+ currentTime;
    }
}