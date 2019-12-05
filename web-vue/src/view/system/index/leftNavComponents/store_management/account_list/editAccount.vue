<template>
  <div class="experience-version infoForm">
    <div class="select-menu top infoWrapper">
      <el-form
        ref="formData"
        :model="formData"
        label-width="110px"
        style="margin:0px 0px 0px 50px;"
        label-position="left"
        status-icon
      >
        <el-form-item :label="$t('shopAccountList.accountAdd.userName')">
          <el-input
            type="text"
            ref="userName"
            v-model="formData.userName"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.password')">
          <el-input
            v-model="formData.password"
            show-password
            ref="password"
            size="small"
            autocomplete="off"
          ></el-input>
          <span
            v-show="formData.sysId!==null"
            style="color: #c09853;"
          >密码为空，则不修改原密码</span>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.accountName')">
          <el-input
            v-model="formData.accountName"
            ref="accountName"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.state')">
          <el-select
            v-model="formData.state"
            ref="state"
            :placeholder="$t('shopAccountList.selectState')"
            size="small"
          >
            <el-option
              v-for="item in authStates"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.maxSkuNum')">
          <el-input
            v-model="formData.maxSkuNum"
            ref="maxSkuNum"
            size="small"
            type="number"
            min=1
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.maxShopNum')">
          <el-input
            v-model="formData.maxShopNum"
            ref="maxShopNum"
            size="small"
            type="number"
            min=1
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.buyTime')">
          <div class="block">
            <el-date-picker
              size="small"
              v-model="formData.buyTime"
              ref="buyTime"
              type="datetime"
              :placeholder="$t('shopAccountList.timeSelect')"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.endTime')">
          <div class="block">
            <el-date-picker
              size="small"
              v-model="formData.endTime"
              ref="endTime"
              type="datetime"
              :placeholder="$t('shopAccountList.timeSelect')"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.mobile')">
          <el-input
            v-model="formData.mobile"
            ref="mobile"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.company')">
          <el-input
            v-model="formData.company"
            ref="company"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.salesperson')">
          <el-input
            v-model="formData.salesperson"
            ref="salesperson"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.geoLocation')">
          <div style="display: flex; width: 800px">
            <v-distpicker
              v-if="showDis"
              :province="select.province"
              :city="select.city"
              :area="select.area"
              @selected="onSelected"
              @province="getProvinceCode"
              @city="getCityCode"
              @area="getDistrictCode"
            ></v-distpicker>
          </div>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.address')">
          <el-input
            v-model="formData.address"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.baseSale')">
          <el-switch
            v-model="baseSaleTran"
            active-color="#f7931e"
            inactive-color="#ddd"
          >
          </el-switch>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.addCommentSwitch')">
          <el-switch
            v-model="addCommentSwitchTran"
            active-color="#f7931e"
            inactive-color="#ddd"
          >
          </el-switch>
        </el-form-item>
      </el-form>
      <el-button
        size="small"
        type="primary"
        style="margin-left: 158px; padding: 0!important"
        @click="saveBefore"
      >
        {{$t('shopAccountList.accountAdd.save')}}
      </el-button>
    </div>
  </div>
</template>

<script>
import { editAccountRequest, searchOneAccountRequest } from '@/api/system/accountList.js'
import VDistpicker from 'v-distpicker'
export default {
  name: 'editAccount',
  components: { VDistpicker },
  computed: {
  },
  props: ['sendData'],
  watch: {
  },
  data () {
    return {
      formData: {
        userName: '',
        password: '',
        accountName: '',
        maxSkuNum: '',
        maxShopNum: '',
        buyTime: '',
        endTime: '',
        mobile: '',
        company: '',
        salesperson: '',
        area: '',
        addCommentSwitch: 0,
        baseSale: 0
      },
      sendDatas: null,
      baseSaleTran: false,
      addCommentSwitchTran: false,
      authStates: this.$t('shopAccountList.auth_state'),
      phonereg: /^1[3|7|8]\d{9}$|^19[8-9]\d{8}$|^166\d{8}|^15[0-3|5-9]\d{8}|^14[5|7]\d{8}$/,
      select: { province: null, city: null, area: null },
      showDis: false
    }
  },
  mounted () {
    this.search()
  },
  methods: {
    search () {
      if (!this.isEmpty(this.sendData)) {
        this.sendDatas = this.sendData
        console.log(this.sendDatas)
      }
      searchOneAccountRequest(this.sendDatas).then((res) => {
        if (res.error === 0) {
          this.formData = res.content
          this.exchangeSwitch()
          // distpicker插件的数据初始化在页面加载时候，我们需要动态赋值之后再让其初始化，用v-if控制，
          // 此插件的值为number，需要格式化
          this.select.province = Number(this.formData.provinceCode)
          this.select.city = Number(this.formData.cityCode)
          this.select.area = Number(this.formData.districtCode)
          this.showDis = true
          console.log('结果')
          console.log(this.formData)
        } else {
          this.showDis = true
          this.$message.error(res.message)
        }
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },
    // 省市区数据更新
    onSelected (data) {
      this.formData.provinceCode = data.province.code
      this.formData.cityCode = data.city.code
      this.formData.districtCode = data.area.code
    },
    getProvinceCode (data) {
      this.formData.provinceCode = data.code
    },
    getCityCode (data) {
      this.formData.cityCode = data.code
    },
    getDistrictCode (data) {
      this.formData.districtCode = data.code
    },
    saveBefore () {
      if (this.checkData()) {
        this.save()
      }
    },
    // 添加商家账户
    save () {
      this.switchChange()
      editAccountRequest(this.formData).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success(res.message)
          let params = {
            'name': 'thirdOver'
          }
          this.$emit('send', params)
        } else {
          this.$message.error(res.message)
        }
      }).catch(() => {
        this.$message.error('保存失败')
      })
    },
    checkData () {
      if (this.isEmpty(this.formData.userName)) {
        this.$message.error('用户名不能为空')
        this.$refs.userName.$el.querySelector('input').focus()
        return false
      } if (this.isEmpty(this.formData.state)) {
        this.$message.error('审核状态不能为空')
        this.$refs.state.$el.querySelector('input').focus()
        return false
      } if (this.isGreaterZero(this.formData.maxSkuNum)) {
        this.$message.error('最大SKU数量不能为空或0')
        this.$refs.maxSkuNum.$el.querySelector('input').focus()
        return false
      } if (this.isGreaterZero(this.formData.maxShopNum)) {
        this.$message.error('最大店铺数量不能为空或0')
        this.$refs.maxShopNum.$el.querySelector('input').focus()
        return false
      } if (this.isEmpty(this.formData.buyTime)) {
        this.$message.error('首次续费时间不能为空')
        this.$refs.buyTime.$el.querySelector('input').focus()
        return false
      } if (this.isEmpty(this.formData.endTime)) {
        this.$message.error('到期时间不能为空')
        this.$refs.endTime.$el.querySelector('input').focus()
        return false
      } else {
        if (!this.isEmpty(this.formData.mobile)) {
          if (!(this.phonereg.test(this.formData.mobile))) {
            this.$message.error('手机号格式错误')
            this.$refs.mobile.$el.querySelector('input').focus()
            return false
          }
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
    isGreaterZero (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        if (obj > 0) {
          return false
        }
        return true
      }
    },
    exchangeSwitch () {
      if (!this.isEmpty(this.formData.baseSale)) {
        if (this.formData.baseSale === 1) {
          this.baseSaleTran = true
        } if (this.formData.baseSale === 0) {
          this.baseSaleTran = false
        }
      }
      if (!this.isEmpty(this.formData.addCommentSwitch)) {
        if (this.formData.addCommentSwitch === 1) {
          this.addCommentSwitchTran = true
        } if (this.formData.addCommentSwitch === 0) {
          this.addCommentSwitchTran = false
        }
      }
      this.provinceCode = this.formData.provinceCode
      this.cityCode = this.formData.cityCode
      this.districtCode = this.formData.districtCode
    },
    switchChange () {
      if (this.baseSaleTran === true) {
        this.formData.baseSale = 1
      } if (this.baseSaleTran === false) {
        this.formData.baseSale = 0
      } if (this.addCommentSwitchTran === true) {
        this.formData.addCommentSwitch = 1
      } if (this.addCommentSwitchTran === false) {
        this.formData.addCommentSwitch = 0
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.experience-version {
  background: #fff;
  .infoWrapper {
    padding-bottom: 30px;
    .el-form-item:first-child {
      padding-top: 20px;
    }
    .el-form-item {
      margin-bottom: 10px;
    }
    .el-input {
      width: 150px;
    }
    .el-select {
      width: 150px;
    }
    .el-button--small {
      width: 52px;
      height: 26px;
      padding: 7px 14px;
    }
    /deep/ .distpicker-address-wrapper select {
      padding: 0;
      font-size: 14px;
      width: 150px;
      height: 30px;
    }
  }
}
</style>
