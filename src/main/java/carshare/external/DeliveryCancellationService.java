
package carshare.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="delivery", url="${api.delivery.url}")
public interface DeliveryCancellationService {

    @RequestMapping(method= RequestMethod.POST, path="/deliveryCancellations")
    public void deliveryCancel(@RequestBody DeliveryCancellation deliveryCancellation);

}