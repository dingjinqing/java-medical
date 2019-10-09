<template>
    <div class="personalCenterContent">
        <div class="personalCenterContent_main">
            <div class="prompt">
                <img src="@/assets/image/admin/notice_img.png" alt="">
                <span>为避免产生不必要的运营问题，建议关闭不使用的展示配置项</span>
            </div>
            <div class="cententLeft">
                <div class="cententLeft_title"></div>
                <div class="cententLeft_bottom">

                </div>
            </div>
            <div class="cententRight">
                <div class="cententRight_title">
                    页面布局样式：
                    <el-radio-group v-model="styleChoose">
                        <el-radio :label="1">平铺式</el-radio>
                        <el-radio :label="2">卡片式</el-radio>
                    </el-radio-group>
                </div>
                <div class="cententRight_container">
                    <el-collapse v-model="activeNames" @change="changeHandler">
                        <el-collapse-item title="个人信息背景色配置" name="1">
                            
                        </el-collapse-item>
                        <el-collapse-item title="账户资产信息展示项配置" name="2">
                            <el-form ref="propertyForm" :model="propertyFormInfo" label-width="100px">
                                <el-form-item label="资产：">
                                    <el-switch v-model="propertyForm.switch" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
                                    <span>开关开启，则展示账户资产信息</span>
                                </el-form-item>
                                <el-form-item label="标题：" prop="title">
                                    <el-input style="width: 120px;" v-model="propertyForm.title"></el-input>
                                </el-form-item>
                                <el-form-item label="我的余额：" prop="balance">
                                    <el-radio-group v-model="propertyForm.balance">
                                        <el-radio label="1">展示</el-radio>
                                        <el-radio label="0">不展示</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="我的积分：" prop="points">
                                    <el-radio-group v-model="propertyForm.points">
                                        <el-radio label="1">展示</el-radio>
                                        <el-radio label="0">不展示</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="优惠券：" prop="preferenceTicket">
                                    <el-radio-group v-model="propertyForm.preferenceTicket">
                                        <el-radio label="1">展示</el-radio>
                                        <el-radio label="0">不展示</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="优惠卡：" prop="preferenceCard">
                                    <el-radio-group v-model="propertyForm.preferenceCard">
                                        <el-radio label="1">展示</el-radio>
                                        <el-radio label="0">不展示</el-radio>
                                    </el-radio-group>
                                </el-form-item>
        
                            </el-form>
                        </el-collapse-item>
                        <el-collapse-item title="订单信息展示项配置" name="3">
                            
                        </el-collapse-item>
                        <el-collapse-item title="预约信息展示项配置" name="4">
                            
                        </el-collapse-item>
                        <el-collapse-item title="店铺使用相关数据展示项配置" name="5">
                        
                        </el-collapse-item>
                        <el-collapse-item title="服务展示项配置" name="6">
                            
                        </el-collapse-item>
                    </el-collapse>
                </div>




                



                
            </div>
            <div class="footer">
                <el-button type="primary" size="small" @click="saveClickHandler">保存</el-button>
            </div>
        </div>
    </div>
</template>
<script>
import { mapGetters } from 'vuex'
import ImageDalog from '@/components/admin/imageDalog'
import SelectLinks from '@/components/admin/selectLinks'
// import { bottomGetRequest, bottomUpdateRequest } from '@/api/admin/bottomNavigation'
export default {
  components: { SelectLinks, ImageDalog },
  data () {
    return {
        styleChoose: '',
        activeNames: ['2'],

        propertyForm: {
            switch: true,
            title: '',
            balance: '1',
            points: '1',
            preferenceTicket: '1',
            preferenceCard:'1'
        },
    }
  },
  computed: {
    // ...mapGetters(['afferentPath']),
    // afferentPath_ () {
    //   return this.afferentPath
    // }
  },
  watch: {
    
  },
  mounted () {
    // 初始化语言
    this.langDefault();
    // 初始化查询
    // this.queryBottom();
  },
  methods: {
    // 保存
    saveClickHandler() {

    },
    changeHandler(val) {
        console.log(val);
    },
  }
}
</script>
<style scoped>
.personalCenterContent {
    padding: 10px;
    min-width: 100%;
    font-size: 14px;
    height: 100%;
    position: relative;
}

.personalCenterContent_main {
    background-color: #fff;
    height: 100%;
    overflow: hidden;
    overflow-y: auto;
    padding-bottom: 96px;
}

.prompt {
    width: 820px;
    height: 40px;
    line-height: 40px;
    border: 1px solid #F2E1C8;
    background: #FFF7EC;
    color: #666;
    padding-left: 12px;
    /* margin: 0 auto; */
    margin-left: 224px;
    margin-top: 80px;
}

.prompt img {
    margin: -3px 5px 0 0;
}

.cententLeft {
    width: 323px;
    height: 510px;
    border: 1px solid #ccc;
    background: #eee;
    position: relative;
    float: left;
    margin: 20px 0 0 224px;
}

.cententLeft_title {
  height: 55px;
  background: url(../../../../../../assets/adminImg/phone_tops.png) no-repeat;
  line-height: 55px;
  text-align: center;
  padding-top: 9px;
  color: #fff;
}

.cententRight {
    width: 480px;
    float: left;
    margin: 20px 0 0 15px;
    /* border: 1px solid #e5e5e5; */
    /* background: #f8f8f8; */
    padding: 15px 12px 22px;
    border-radius: 3px;
}

.cententRight_title {
    margin-bottom: 20px;
}

.el-collapse {
    border: none;
}

.el-collapse-item {
    margin-bottom: 20px;
    background: #ccc;
}

.el-collapse-item .el-collapse-item__header {
    font-size: 14px;
    background: #ccc !important;
}

.footer {
   background: #f8f8fa;
    text-align: center;
    box-sizing: border-box;
    height: 50px;
    padding-top: 10px;
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 2;
}
</style>
