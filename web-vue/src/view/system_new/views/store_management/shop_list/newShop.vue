<template>
  <div class="new-shop infoForm">
    <div class="select-menu top infoWrapper">
      <el-form
        ref="form"
        :model="Data"
        label-width="100px"
        @submit.prevent="onSubmit"
        style="margin:0px 0px 0px 50px;width:25%;min-width:300px;"
        label-position="left"
      >
        <el-form-item label="所属账号">
          <!-- <el-input
            v-model="Data.sysId"
            key="1"
            size="small"
            v-if="flag"
          ></el-input> -->
          <span
            v-if="!flag"
            style="color:#333"
          >{{this.$route.params.name}}</span>
        </el-form-item>
        <el-form-item label="数据库">
          <el-select
            v-model="Data.dbConfigId"
            placeholder="请选择数据库"
            size="small"
          >
            <el-option
              label="vpu_user(172.21.0.3)"
              value="1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="手机号"
          :required=true
        >
          <el-input
            v-model="Data.mobile"
            type="text"
            key="2"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="店铺类型"
          :required=true
        >
          <el-select
            v-model="Data.shopType"
            placeholder="请选择店铺类型"
            size="small"
          >
            <el-option
              label="体验版"
              value="v1"
            ></el-option>
            <el-option
              label="基础版"
              value="v2"
            ></el-option>
            <el-option
              label="高级版"
              value="v3"
            ></el-option>
            <el-option
              label="旗舰版"
              value="v4"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="到期时间">
          <div class="block">
            <el-date-picker
              v-model="Data.endTime"
              size="small"
              type="datetime"
              placeholder="选择日期时间"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="接收短信手机号">
          <el-input
            v-model="Data.receiveMobile"
            label='1000'
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="店铺名称"
          :required=true
        >
          <el-input
            v-model="Data.shopName"
            label='100'
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="店铺客服电话">
          <el-input
            v-model="Data.shopPhone"
            label="100"
            size="small"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="店铺公告">
          <el-input
            type="textarea"
            v-model="Data.shopNotice"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="店铺微信">
          <el-input
            v-model="Data.shopWx"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="店铺邮箱">
          <el-input
            v-model="Data.shopEmail"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="店铺禁用">
          <el-checkbox-group v-model="Data.isEnabled">
            <el-checkbox
              label="禁用"
              name="type"
            ></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="店铺标记">
          <el-select
            v-model="Data.shopFlag"
            placeholder="请选择店铺标记"
            size="small"
          >
            <el-option
              label="欧派"
              value="1"
            ></el-option>
            <el-option
              label="寺库"
              value="2"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="隐藏底部">
          <el-checkbox-group v-model="Data.hidBottom">
            <el-checkbox
              label="隐藏"
              name="type"
            ></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="欧派店铺标识">
          <el-input
            v-model="Data.memberKey"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="欧派大屏租户名称">
          <el-input
            v-model="Data.tenancyName"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="欧派大屏用户名">
          <el-input
            v-model="Data.userName"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="欧派大屏密码">
          <el-input
            v-model="Data.password"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="店铺客服QQ">
          <el-input
            v-model="Data.shopQq"
            size="small"
          ></el-input>
        </el-form-item>
      </el-form>
      <div class="btn">
        <el-button
          size="small"
          type="primary"
          @click="save()"
        >
          添加
        </el-button>
        <span class="text">添加新店铺，会创建此店铺的数据库。添加店铺只可以禁用，不能删除！，谨慎添加。</span>
      </div>
    </div>
  </div>
</template>

<script>
import { newShopRequest } from '@/api/system/shopList.js'
export default {
  name: 'newShop',
  data () {
    return {
      Data: {
        sysId: '',
        mobile: '',
        shopType: '',
        receiveMobile: '',
        shopName: '',
        shopPhone: '',
        shopNotice: '',
        shopWx: '',
        shopEmail: '',
        isEnabled: '',
        shopQq: '',
        shopFlag: '',
        memberKey: '',
        tenancyName: '',
        userName: '',
        password: '',
        hidBottom: '',
        dbConfigId: '',
        endTime: ''
      },
      name: '',
      flag: '',
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      }
    }
  },
  // mounted () {
  //   console.log(this.$route.params)
  //   if (this.$route.params.flag === true) {
  //     let name = this.$route.params.name
  //     console.log(name)
  //   }
  // },
  methods: {
    // 添加商家账户
    save () {
      let params = {
        'sysId': '',
        'mobile': '18722222233',
        'shopType': 'v4',
        'shopName': 'json测试3'
      }
      newShopRequest(params).then(res => console.log(res + '1111')).catch(err => console.log(err + '8888'))
      //   let obj = {
      //     'sysId': '2',
      //     'mobile': '18722222233',
      //     'shopType': 'v4',
      //     'receiveMobile': '18722222234',
      //     'shopName': 'json测试3',
      //     'shopPhone': '18722222235',
      //     'shopNotice': '店铺公告',
      //     'shopWx': 'weixin',
      //     'shopEmail': 'lalala@163.com',
      //     'isEnabled': '0',
      //     'shopFlag': '2',
      //     'hidBottom': '0',
      //     'shopQq': '99887766',
      //     'memberKey': '欧派店铺标识',
      //     'tenancyName': '欧派大屏租户名称',
      //     'userName': '欧派大屏用户名',
      //     'password': '123456'
      //   }
      //   let params = Object.assign(obj, this.Data)
      //   console.log(params)
      //   newShopRequest(params).then(res => {
      //     console.log(res)
      //     if (res.error === 0) {
      //       this.$message({
      //         message: '保存成功',
      //         type: 'success'
      //       })
      //     } else {
      //       this.$message({
      //         message: res.message,
      //         type: 'warning'
      //       })
      //     }
      //   }).catch(() => {
      //     this.$message.error('保存失败')
      //   })
    }
  }
}
</script>

<style lang="scss" scoped>
.new-shop {
  background: #fff;
  .infoWrapper {
    padding-bottom: 50px;
    .el-form-item:first-child {
      padding-top: 20px;
    }
    .el-form-item {
      margin-bottom: 10px;
      /deep/ .el-textarea__inner {
        width: 150px;
        height: 80px;
        margin-left: 30px;
      }
      /deep/ .el-form-item__label {
        width: 130px !important;
      }
    }
    .el-input {
      width: 150px;
    }
    .el-select {
      width: 150px;
    }
    .btn {
      margin-left: 180px;
      .text {
        color: #b94a48;
        font-size: 14px;
        margin-left: 15px;
      }
    }
  }
}
</style>
