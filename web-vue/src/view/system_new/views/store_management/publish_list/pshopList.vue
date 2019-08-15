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
        <el-form-item :label="$t('shopList.addInfo.sysId')">
          <el-input
            v-model="this.$route.params.name"
            key="1"
            size="small"
            v-if="!flag"
            style="border: none"
          ></el-input>
          <!-- <span
            v-if="!flag"
            style="color:#333"
          >{{this.$route.params.name}}</span> -->
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.dbConfigId')">
          <el-select
            v-model="Data.dbConfigId"
            :placeholder="$t('shopList.selectDb')"
            size="small"
          >
            <el-option
              label="vpu_user(172.21.0.3)"
              value="1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          :label="$t('shopList.addInfo.mobile')"
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
          :label="$t('shopList.addInfo.shopType')"
          :required=true
        >
          <el-select
            v-model="Data.shopType"
            :placeholder="$t('shopList.selectType')"
            size="small"
          >
            <el-option
              :label="$t('shopList.versionName.exp')"
              value="v1"
            ></el-option>
            <el-option
              :label="$t('shopList.versionName.base')"
              value="v2"
            ></el-option>
            <el-option
              :label="$t('shopList.versionName.high')"
              value="v3"
            ></el-option>
            <el-option
              :label="$t('shopList.versionName.unique')"
              value="v4"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.endTime')">
          <div class="block">
            <el-date-picker
              v-model="Data.endTime"
              size="small"
              type="datetime"
              :placeholder="$t('shopList.selectData')"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.receiveMobile')">
          <el-input
            v-model="Data.receiveMobile"
            label='1000'
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('shopList.addInfo.shopName')"
          :required=true
        >
          <el-input
            v-model="Data.shopName"
            label='100'
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.shopPhone')">
          <el-input
            v-model="Data.shopPhone"
            label="100"
            size="small"
          >
          </el-input>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.shopNotice')">
          <el-input
            type="textarea"
            v-model="Data.shopNotice"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.shopWx')">
          <el-input
            v-model="Data.shopWx"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.shopEmail')">
          <el-input
            v-model="Data.shopEmail"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.isDisabled')">
          <el-checkbox-group v-model="Data.isEnabled">
            <el-checkbox
              :label="$t('shopList.shopDisabled')"
              name="type"
              true-label=1
              false-label=0
            ></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.shopFlag')">
          <el-select
            v-model="Data.shopFlag"
            :placeholder="$('shopList.selectFlag')"
            size="small"
            clearable
          >
            <el-option
              :label="$t('shopList.flag_type.type1')"
              value="1"
            ></el-option>
            <el-option
              :label="$t('shopList.flag_type.type2')"
              value="2"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.hidBottom')">
          <el-checkbox-group v-model="Data.hidBottom">
            <el-checkbox
              :label="$t('shopList.hideFooter')"
              name="type"
              true-label=1
              false-label=0
            ></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.memberKey')">
          <el-input
            v-model="Data.memberKey"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.tenancyName')">
          <el-input
            v-model="Data.tenancyName"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.userName')">
          <el-input
            v-model="Data.userName"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.password')">
          <el-input
            v-model="Data.password"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.shopQq')">
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
import { newShopRequest, shopSearchRequest } from '@/api/system/shopList.js'
export default {
  name: 'newShop',
  data () {
    return {
      Data: {
        sysId: this.$route.params.name,
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
      flag: ''
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
    getList () {
      shopSearchRequest({
        'currentPage': 1,
        'pageRows': 10
      }).then(res => console.log(res)).catch(err => console.log(err))
    },
    addOne () {
      newShopRequest({
        'sysId': 85,
        'mobile': '18237093404',
        'shopType': 'v4',
        'shopName': '旺店'
      }).then(res => console.log(res)).catch(err => console.log(err))
    },
    // 添加商家账户
    save () {
      let obj = {
        'sysId': '85',
        'mobile': '18722222233',
        'shopType': 'v4',
        'receiveMobile': '18722222234',
        'shopName': 'json测试4443',
        'shopPhone': '18722222235',
        'shopNotice': '店铺公告',
        'shopWx': 'weixin',
        'shopEmail': 'lalala@163.com',
        'isEnabled': '0',
        'shopFlag': '2',
        'hidBottom': '0',
        'shopQq': '99887766',
        'memberKey': '欧派店铺标识',
        'tenancyName': '欧派大屏租户名称',
        'userName': '欧派大屏用户名',
        'password': '123456'
      }
      console.log(this.Data)
      let params = Object.assign(obj, this.Data)
      console.log(params)
      newShopRequest(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message({
            message: '保存成功',
            type: 'success'
          })
        } else {
          this.$message({
            message: res.message,
            type: 'warning'
          })
        }
      }).catch(() => {
        this.$message.error('保存失败')
      })
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
