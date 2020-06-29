package com.vpu.jmd.service.schedule.bo.rabbit;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RabbitInfoData {
    private String name;

    private Double memory;

    private Long messages;

    private String stateName;

}
