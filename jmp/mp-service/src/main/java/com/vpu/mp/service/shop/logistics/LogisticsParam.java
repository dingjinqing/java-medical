package com.vpu.mp.service.shop.logistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liufei
 * @date 12/2/19
 */
@Data
public class LogisticsParam {
    @JsonProperty(value = "delivery_id")
    private String deliveryId;
    @JsonProperty(value = "delivery_name")
    private String deliverName;
    @JsonProperty(value = "can_use_cash")
    private byte canUseCash;
    @JsonProperty(value = "can_get_quota")
    private byte canGetQuota;
    @JsonProperty(value = "service_type")
    private List<ServiceType> serviceType;

    public class ServiceType {
        @JsonProperty(value = "service_type")
        private int serviceType;
        @JsonProperty(value = "service_name")
        private String serviceName;

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }
    }
}
