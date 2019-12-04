<template>
  <div class="applyMobile">
    <div
      class="top"
      :style="'background:url('+$imageHost+'/image/admin/apply_fortrial_bg2.jpg) no-repeat;background-size: 100% 100%'"
    >
    </div>
    <div class="formContainer">
      <div class="main">
        <div class="title">我要开店-申请免费试用</div>
        <el-form
          :model="ruleForm"
          status-icon
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
          class="demo-ruleForm"
        >
          <el-form-item
            label="姓名"
            prop="username"
          >
            <el-input
              :placeholder="$t('apply.placeholder_name')"
              v-model="ruleForm.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="电话"
            prop="phonenum"
          >
            <el-input
              :placeholder="$t('apply.placeholder_name')"
              v-model.number="ruleForm.phonenum"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <div class="companyName">
            <el-form-item label="公司名称">
              <el-input
                placeholder="请填写公司名称"
                v-model="ruleForm.companyName"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </div>
        </el-form>
        <div
          class="p_apply_submit"
          @click="handlesubmit()"
        >{{$t('apply.submit')}}</div>
      </div>
    </div>
    <div class="middleImg">
      <img :src="$imageHost+'/image/admin/new_apply_mobile/function_great.png'">
    </div>
    <div class="coreMain">
      <div
        class="coreList"
        v-for="(item,index) in coreData"
        :key="index"
      >
        <div class="img">
          <img :src="item.imgUrl">
        </div>
        <p>{{item.name}}</p>
        <h4>{{item.contentOne}}</h4>
        <h4>{{item.contentTow}}</h4>
      </div>

    </div>
    <div class="caseShow">
      <img :src="$imageHost+'/image/admin/new_apply_mobile/khal.png'">
    </div>
    <div
      class="customer"
      v-for="(item,index) in customerData"
      :key="index"
    >
      <div class="customerImg">
        <img :src="item.imgUrl">
      </div>
      <p>{{item.contentOne}}</p>
      <h4>{{item.contentTwo}}</h4>
    </div>
    <div
      class="footer"
      :style="'background:url('+$imageHost+'/image/admin/new_apply_mobile/oval.png) no-repeat;background-size: 100% 100%'"
    >
      <div class="content">
        <p
          :class="item.class"
          v-for="(item,index) in footerPData"
          :key="index"
        >
          {{item.text}}
        </p>
      </div>
    </div>
  </div>
</template>
<script>
import { applyrequest } from '@/api/index/apply.js'
export default {
  data () {
    return {
      ruleForm: {
        username: '',
        phonenum: '',
        companyName: ''
      },
      rules: {
        username: [{ required: true, message: '请填写您的姓名', trigger: 'blur' }],
        phonenum: [
          { type: 'number', message: '请填写您的手机号' },
          { required: true, message: '手机号不能为空' }
        ],
        companyName: [{ message: '请填写您的公司名称', trigger: 'blur' }]
      },
      coreData: [
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/apply.png',
          name: '预约服务',
          contentOne: '线上引流、多项预约模式',
          contentTow: '提高顾客体验'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/store_management.png',
          name: '门店管理',
          contentOne: '多门店连锁经营,获客细分',
          contentTow: '增加曝光、降低管理成本'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/yxhd.png',
          name: '营销活动',
          contentOne: '拼团、砍价、分销、秒杀、',
          contentTow: '优惠券、抽奖、加价购等'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/dpzj.png',
          name: '店铺装修',
          contentOne: '行业模板按需试用',
          contentTow: '模块拖拽、无需代码编辑'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/data_count.png',
          name: '数据统计',
          contentOne: '销量、流量来源、精准统计',
          contentTow: '用户画像精准分析'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/vip.png',
          name: '会员管理',
          contentOne: '线上线下会员打通',
          contentTow: '会员卡、积分、限次卡'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/wlgl.png',
          name: '物流管理',
          contentOne: '多种运费模板一键选择',
          contentTow: '区域性运费模板轻松设置'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/zxjy.png',
          name: '在线交易',
          contentOne: '微信、余额、积分、买单',
          contentTow: '多种支付方式轻松开启'
        }
      ],
      customerData: [
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/ajjc.png',
          contentOne: '安吉九茶',
          contentTwo: '让更多人喝到安心正味好茶'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/lsr.png',
          contentOne: '雷瑟尔行货商城',
          contentTwo: '行货商品更有性价比'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/bjhz.png',
          contentOne: '北京寒竹梅鲜花',
          contentTwo: '良心品质、用心服务'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_apply_mobile/zwlj.png',
          contentOne: '中外旅居',
          contentTwo: '品质为本，精益求精'
        }
      ],
      footerPData: [
        {
          class: 'p1',
          text: '北京掌上先机网络科技有限公司'
        },
        {
          class: 'p1',
          text: '电话:  400-010-1039   7*24小时'
        },
        {
          class: 'p2',
          text: '北京总部：北京市海淀区西直门外大街伊泰大厦5层'
        },
        {
          class: 'p2',
          text: '上海分公司：上海市闵行区七莘路1188号A座509'
        }
      ]
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 提交表单
    handlesubmit () {
      localStorage.setItem('contentType', 'application/json;charset=UTF-8')
      let obj = {
        contact: this.ruleForm.username,
        mobile: this.ruleForm.phonenum,
        company: this.ruleForm.companyName
      }
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          applyrequest(obj).then((res) => {
            console.log(res)
            if (res.error === 0) {
              this.$message.success({
                showClose: true,
                message: '提交申请成功，请等待业务员联系',
                type: 'success'
              })
              this.$refs['ruleForm'].resetFields()
              this.ruleForm.companyName = ''
            } else {
              this.$message.error({
                showClose: true,
                message: res.message,
                type: 'error'
              })
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.applyMobile {
  position: absolute;
  right: 0;
  left: 0;
  top: 0;
  bottom: 0;
  overflow-y: auto;
  background-color: #e5e9f2;
  .top {
    height: 7.05rem;
  }
  .formContainer {
    display: flex;
    justify-content: center;
    background: #dee8ff;
    padding-bottom: 5%;
    .main {
      background: #fff;
      width: 90%;
      border-radius: 8px;
      display: flex;
      justify-content: center;
      flex-direction: column;
      position: relative;
      align-items: center;
      margin-bottom: 30px;
      .title {
        color: #333;
        font-size: 16px;
        height: 60px;
        line-height: 60px;
        text-align: center;
      }
      .demo-ruleForm {
        width: 300px;
        padding-bottom: 20px;
        /deep/ .el-form-item {
          display: flex;
          /deep/ .el-form-item__label {
            width: 60px !important;
            white-space: nowrap;
          }
          /deep/ .el-form-item__content {
            margin-left: 0 !important;
            width: 100%;
          }
        }
        .companyName {
          margin-left: -8px;
        }
      }
      .p_apply_submit {
        width: 80%;
        height: 40px;
        line-height: 40px;
        color: #fff;
        background: -webkit-linear-gradient(left, #5a8bff, #28a6ff);
        border-radius: 20px;
        margin: 0 auto;
        text-align: center;
        cursor: pointer;
        font-size: 14px;
        position: absolute;
        bottom: -20px;
      }
    }
  }
  .middleImg {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: -15px;
    img {
      width: 50%;
    }
  }
  .coreMain {
    background: #fff;
    width: 90%;
    margin: 0 auto;
    border-radius: 8px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    position: relative;
    margin-top: 20px;
    padding: 10px 0px 30px 0px;
    margin-bottom: 10px;
    .coreList {
      width: 49%;
      display: flex;
      justify-content: center;
      flex-direction: column;
      margin-top: 10px;
      .img {
        display: flex;
        justify-content: center;
        img {
          width: 50%;
        }
      }
      p {
        font-size: 0.3rem;
        color: #333;
        text-align: center;
        margin-top: 5px;
      }
      h4 {
        color: #666;
        font-size: 0.25rem;
        text-align: center;
        margin-top: 5px;
      }
    }
  }
  .caseShow {
    margin-top: 30px;
    display: flex;
    justify-content: center;
    img {
      width: 50%;
    }
  }
  .customer {
    width: 90%;
    margin: 0 auto;
    margin-top: 20px;
    background: #fff;
    padding: 10px;
    margin-bottom: 10px;
    border-radius: 8px;
    position: relative;
    z-index: 3;
    .customerImg {
      display: flex;
      justify-content: center;
      align-items: center;
      img {
        width: 50%;
      }
    }
    p {
      font-size: 0.3rem;
      color: #333;
      text-align: center;
      margin-top: 5px;
      font-weight: 600;
    }
    h4 {
      color: #666;
      font-size: 0.25rem;
      text-align: center;
      margin-top: 5px;
      margin-bottom: 5px;
    }
  }
  .footer {
    height: 4.5rem;
    margin-top: -80px;
    display: flex;
    justify-content: flex-end;
    flex-direction: column;
    align-items: center;
    padding-bottom: 30px;
    .content {
      p {
        text-align: center;
        width: 100%;
      }
      .p1 {
        color: #fff;
        font-size: 0.3rem;
        text-align: center;
        margin-top: 10px;
        margin-bottom: 10px;
        font-weight: 600;
      }
      .p2 {
        color: #fff;
        font-size: 0.25rem;
        text-align: center;
        margin-top: 5px;
        margin-bottom: 10px;
      }
    }
  }
}
</style>
