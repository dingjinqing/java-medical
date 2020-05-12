package com.vpu.mp.service.foundation.util.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 锁实体
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LockEntity {
    private String value;
    private List<String> keys;
}
