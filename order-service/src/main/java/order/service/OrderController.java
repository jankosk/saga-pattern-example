package order.service;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/order")
public class OrderController {

    @Get(processes = MediaType.TEXT_PLAIN)
    public String processOrder() {
        return "Hello world";
    }
}
