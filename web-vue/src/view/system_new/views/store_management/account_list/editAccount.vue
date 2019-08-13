<template>
  <div class="experience-version infoForm">
    <div class="select-menu top infoWrapper">
      <el-form
        ref="form"
        :model="formData"
        label-width="100px"
        @submit.prevent="onSubmit"
        style="margin:0px 0px 0px 50px;width:20%;min-width:200px;"
        label-position="left"
        :rules="rules"
      >
        <el-form-item label="用户名">
          <el-input
            v-model="formData.userName"
            key="1"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input
            v-model="formData.password"
            type="password"
            key="2"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input
            v-model="formData.accountName"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="审核状态">
          <el-select
            v-model="formData.state"
            placeholder="请选择状态"
            size="small"
          >
            <el-option
              label="申请中"
              value="1"
            ></el-option>
            <el-option
              label="审核通过"
              value="2"
            ></el-option>
            <el-option
              label="审核不通过"
              value="3"
            ></el-option>
            <el-option
              label="已禁用"
              value="4"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="最大SKU数量">
          <el-input
            v-model="formData.maxSkuNum"
            label='1000'
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="最大店铺数量">
          <el-input
            v-model="formData.maxShopNum"
            label='100'
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="首次续费时间">
          <div class="block">
            <el-date-picker
              size="small"
              v-model="formData.buyTime"
              type="datetime"
              placeholder="选择日期时间"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="到期时间">
          <div class="block">
            <el-date-picker
              size="small"
              v-model="formData.endTime"
              type="datetime"
              placeholder="选择日期时间"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input
            v-model="formData.mobile"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="公司名称">
          <el-input
            v-model="formData.company"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="销售员">
          <el-input
            v-model="formData.salesperson"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="地理位置">
          <div style="display: flex; width: 800px">
            <v-distpicker
              @selected="onSelected"
              :province="formData.provinceCode"
              :city="formData.cityCode"
              :area="formData.districtCode"
              @province="getProvinceCode"
              @city="getCityCode"
              @area="getDistrictCode"
            ></v-distpicker>
          </div>
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input
            v-model="formData.address"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="初始销量">
          <el-switch
            active-value="1"
            inactive-value="0"
            active-color="#f7931e"
            inactive-color="#ddd"
            v-model="formData.baseSale"
          >
          </el-switch>
        </el-form-item>
        <el-form-item label="添加评价">
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
        class="ml-6"
        type="primary"
        style="margin-left: 148px"
        @click="save()"
      >
        搜索
      </el-button>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { addCoountRequest } from '@/api/system/accountList.js'
import VDistpicker from 'v-distpicker'
export default {
  name: 'experienceVersion',
  component: { VDistpicker },
  computed: {
    ...mapGetters(['proAndUrData']),
    proAndUrData_ () {
      return this.proAndUrData
    }
  },
  watch: {
    proAndUrData_ (newData, oldData) {
      console.log(newData)
      this.query()
    }
  },
  data () {
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
        provinc: '',
        city: '',
        area: '',
        addCommentSwitch: false,
        baseSale: false
      },
      rules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ],
        state: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        date2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ],
        provinceCode: [
          { required: true, message: '请选择省份', trigger: 'change' }
        ],
        cityCode: [
          { required: true, message: '请选择市区', trigger: 'change' }
        ],
        address: [
          { required: true, message: '请选择片区', trigger: 'change' }
        ]
      }
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
    save () {
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
      let params = Object.assign(obj, this.formData)
      console.log(params)
      addCoountRequest(params).then(res => {
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
