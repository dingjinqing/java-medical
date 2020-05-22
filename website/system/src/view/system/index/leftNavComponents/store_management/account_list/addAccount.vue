<template>
  <div class="experience-version infoForm">
    <div class="select-menu top infoWrapper">
      <el-form
        ref="formData"
        :model="formData"
        label-width="110px"
        @submit.prevent="onSubmit"
        style="margin:0px 0px 0px 50px;"
        label-position="left"
        status-icon
        :rules="rules"
      >
        <el-form-item
          :label="$t('shopAccountList.accountAdd.userName')"
          prop="userName"
        >
          <el-input
            type="text"
            v-model="formData.userName"
            key="1"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('shopAccountList.accountAdd.password')"
          prop="password"
        >
          <el-input
            v-model="formData.password"
            autocomplete="new-password"
            onfocus="this.removeAttribute('readonly')"
            onblur="this.setAttribute('readonly',true)"
            type="password"
            readonly
            key="2"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.accountName')">
          <el-input
            v-model="formData.accountName"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.state')">
          <el-select
            v-model="formData.state"
            :placeholder="$t('shopAccountList.selectState')"
            size="small"
            clearable
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
            label='1000'
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.maxShopNum')">
          <el-input
            v-model="formData.maxShopNum"
            label='100'
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.buyTime')">
          <div class="block">
            <el-date-picker
              size="small"
              v-model="formData.buyTime"
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
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.company')">
          <el-input
            v-model="formData.company"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.salesperson')">
          <el-input
            v-model="formData.salesperson"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.geoLocation')">
          <div style="display: flex; width: 800px">
            <v-distpicker
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
            active-value="1"
            inactive-value="0"
            active-color="#f7931e"
            inactive-color="#ddd"
            v-model="formData.baseSale"
          >
          </el-switch>
        </el-form-item>
        <el-form-item :label="$t('shopAccountList.accountAdd.addCommentSwitch')">
          <el-switch
            active-value="1"
            inactive-value="0"
            active-color="#f7931e"
            inactive-color="#ddd"
            v-model="formData.addCommentSwitch"
          >
          </el-switch>
        </el-form-item>
      </el-form>
      <el-button
        size="small"
        type="primary"
        style="margin-left: 158px; padding: 0!important"
        @click="save('formData')"
      >
        {{$t('shopAccountList.accountAdd.save')}}
      </el-button>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { addCoountRequest } from '@/api/system/accountList.js'
import VDistpicker from 'v-distpicker'
export default {
  name: 'addAccount',
  components: { VDistpicker },
  computed: {
    ...mapGetters(['proAndUrData']),
    proAndUrData_ () {
      return this.proAndUrData
    }
  },
  watch: {
    proAndUrData_ (newData, oldData) {
      this.query()
    }
  },
  data () {
    var checkUserName = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('请输入用户名'))
      }
    }
    var checkPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.formData.password !== '') {
          this.$refs.formData.validateField('checkPassword')
        }
        callback()
      }
    }
    return {
      formData: {
        userName: '',
        password: '',
        accountName: '',
        maxSkuNum: '10000',
        maxShopNum: '100',
        buyTime: '',
        endTime: '',
        mobile: '',
        company: '',
        salesperson: '',
        address: '',
        addCommentSwitch: false,
        baseSale: false
      },
      rules: {
        userName: [
          { validator: checkUserName, trigger: 'blur' }
        ],
        password: [
          { validator: checkPassword, trigger: 'change' }
        ]
      },
      authStates: this.$t('shopAccountList.auth_state')
    }
  },
  methods: {
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
    // 添加商家账户
    save (formData) {
      this.$refs[formData].validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          console.log('error submit!')
          return false
        }
      })
      let obj = {
        'userName': '',
        'password': '',
        'state': '1',
        'shopGrade': '1',
        'maxSkuNum': '10000',
        'maxShopNum': '100',
        'provinceCode': '110000',
        'cityCode': '110100',
        'districtCode': '110101'
      }
      console.log(this.formData)
      let params = Object.assign(obj, this.formData)
      console.log(params)
      addCoountRequest(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success('保存成功')
        } else {
          this.$message.error(res.message)
        }
      }).catch(() => {
        this.$message.error('保存失败')
      })
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
