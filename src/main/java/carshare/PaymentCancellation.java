package carshare;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="PaymentCancellation_table")
public class PaymentCancellation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String productId;
    private Integer qty;
    private String status;

    @PostPersist
    public void onPostPersist(){
        PayCancelled payCancelled = new PayCancelled();
        BeanUtils.copyProperties(this, payCancelled);
        payCancelled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        carshare.external.DeliveryCancellation deliveryCancellation = new carshare.external.DeliveryCancellation();
        // mappings goes here
        deliveryCancellation.setOrderId(this.getOrderId());
        deliveryCancellation.setPaymentId(this.getId());
        deliveryCancellation.setProductId(this.getProductId());
        deliveryCancellation.setQty(this.getQty());
        deliveryCancellation.setStatus("deliveryCancelled");

        PaymentApplication.applicationContext.getBean(carshare.external.DeliveryCancellationService.class)
            .deliveryCancel(deliveryCancellation);

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
