package com.vpu.mp.service.pojo.shop.config.message;

import lombok.Data;

import java.util.List;

/**
 * message template config param
 * @author 卢光耀
 * @date 2019-09-16 11:15
 *
*/
@Data
public class MessageConfigParam {
    private List<Config> configs;


    public class Config{
        private Integer id;

        private Byte openMa;

        private Byte openMp;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Byte getOpenMa() {
            return openMa;
        }

        public void setOpenMa(Byte openMa) {
            this.openMa = openMa;
        }

        public Byte getOpenMp() {
            return openMp;
        }

        public void setOpenMp(Byte openMp) {
            this.openMp = openMp;
        }
    }

}
