<template>
  <div class="new-shop infoForm">
    <div class="select-menu top infoWrapper">
      <el-form
        ref="form"
        :model="Data"
        label-width="100px"
        @submit.prevent="onSubmit"
        style="margin:0px 0px 0px 50px;min-width:300px;"
        label-position="left"
      >
        <el-form-item :label="$t('shopList.addInfo.sysId')">
          <!-- <el-input
            v-model="this.$route.params.name"
            key="1"
            size="small"
            v-if="!flag"
            style="border: none"
          ></el-input> -->
          <!-- <span
            v-if="!flag"
            style="color:#333"
          >{{this.$route.params.name}}</span> -->
          <span
            v-if="!flag"
            style="color:#333"
          >{{this.$route.params.title}}</span>
        </el-form-item>
        <el-form-item :label="$t('shopList.addInfo.dbConfigId')">
          <el-select
            v-model="Data.dbConfigId"
            :placeholder="$t('shopList.selectDb')"
            size="small"
          >
            <el-option
              v-for="item in dbVersion"
              :key="item.value"
              :label="item.label"
              :value="item.value"
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
            ref="mobile"
          ></el-input>
          <span v-if="this.phoneHave">手机号已注册</span>
        </el-form-item>

        <el-form-item
          :label="$t('shopList.addInfo.shopLanguage')"
          :required=true
        >
          <el-select
            v-model="Data.shopLanguage"
            placeholder="请选择语言"
            size="small"
          >
            <el-option
              v-for="item in languageType"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              ref="shopLanguage"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item
          :label="$t('shopList.addInfo.currency')"
          :required=true
        >
          <el-select
            v-model="Data.currency"
            placeholder="请选择币种"
            size="small"
            ref="currency"
          >
            <el-option
              v-for="item in currencyType"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
              <span style="float: left">{{ item.label }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.sign }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          :label="$t('shopList.addInfo.shopType')"
          :required=true
        >
          <el-select
            v-model="Data.shopType"
            :placeholder="$t('shopList.selectType')"
            size="small"
            ref="shopType"
          >
            <el-option
              v-for="item in shopVersion"
              :key="item.value"
              :label="item.label"
              :value="item.value"
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
            ref="shopName"
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
            :placeholder="$t('shopList.selectFlag')"
            size="small"
            clearable
          >
            <el-option
              v-for="item in flagType"
              :key="item.value"
              :label="item.label"
              :value="item.value"
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
          @click="beforeSave()"
        >
          {{$t('shopList.save')}}
        </el-button>
        <span class="text">{{$t('shopList.prompt')}}</span>
      </div>
    </div>
    <el-dialog
      title="提示"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <span>已提交</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          @click="centerDialogVisible = false"
          type="primary"
        >确认</el-button>
      </span>

    </el-dialog>
  </div>
</template>

<script>
import { newShopRequest, shopSearchRequest, checkMobileRequest } from '@/api/system/shopList.js'
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
        isEnabled: 0,
        shopQq: '',
        shopFlag: '',
        memberKey: '',
        tenancyName: '',
        userName: '',
        password: '',
        hidBottom: 0,
        dbConfigId: '',
        endTime: '',
        currency: '',
        shopLanguage: ''
      },
      name: '',
      flag: '',
      phoneHave: false,
      centerDialogVisible: false,
      languageType: this.$t('shopList.language_type'),
      currencyType: this.$t('shopList.currency_type'),
      shopVersion: this.$t('shopList.shop_version'),
      dbVersion: this.$t('shopList.db_version'),
      flagType: this.$t('shopList.flag_type'),
      phonereg: /^1[3|7|8]\d{9}$|^19[8-9]\d{8}$|^166\d{8}|^15[0-3|5-9]\d{8}|^14[5|7]\d{8}$/
    }
  },
  mounted () {

  },
  methods: {
    getList () {
      shopSearchRequest({
        'currentPage': 1,
        'pageRows': 10
      }).then(res => console.log(res)).catch(err => console.log(err))
    },
    // 添加商家账户
    save () {
      newShopRequest(this.Data).then(res => {
        console.log(res)
        this.centerDialogVisible = false
        if (res.error === 0) {
          this.$message.success('保存成功')
        } else {
          this.$message.error(res.message)
        }
      }).catch(() => {
        this.$message.error('保存失败')
      })
    },
    beforeSave () {
      if (this.checkData()) {
        this.checkMobile()
      }
    },
    checkData () {
      if (this.isEmpty(this.Data.sysId)) {
        this.$message.error('sysId不能为空')
        return false
      } if (this.isEmpty(this.Data.mobile)) {
        this.$message.error('手机号不能为空')
        this.$refs.mobile.$el.querySelector('input').focus()
        return false
      } if (this.isEmpty(this.Data.shopType)) {
        this.$message.error('店铺类型不能为空')
        this.$refs.shopType.$el.querySelector('input').focus()
        return false
      } if (this.isEmpty(this.Data.currency)) {
        this.$message.error('币种不能为空')
        this.$refs.currency.$el.querySelector('input').focus()
        return false
      } if (this.isEmpty(this.Data.shopLanguage)) {
        this.$message.error('默认语言不能为空')
        this.$refs.shopLanguage.$el.querySelector('input').focus()
        return false
      } if (this.isEmpty(this.Data.shopName)) {
        this.$message.error('店铺名称不能为空')
        this.$refs.shopName.$el.querySelector('input').focus()
        return false
      } else {
        if (!(this.phonereg.test(this.Data.mobile))) {
          this.$message.error('手机号格式错误')
          this.$refs.mobile.$el.querySelector('input').focus()
          return false
        }
        return true
      }
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    checkMobile () {
      let params = {
        'mobile': this.Data.mobile
      }
      checkMobileRequest(params).then((res) => {
        if (res.error === 100002) {
          this.phoneHave = true
          this.$confirm('手机号已注册，确定继续创建此店铺吗？', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$message.success('开始提交')
            this.loadingUp()
          }).catch(() => {
            this.$message.success('已取消提交')
          })
        } if (res.error === 0) {
          this.loadingUp()
        } if ((res.error !== 0) && (res.error !== 100002)) {
          this.$message.error(res.message)
        }
      })
    },
    loadingUp () {
      this.save()
      this.centerDialogVisible = true
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
        width: 150px !important;
        height: 80px;
        margin: -30px 0 10px 30px;
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
