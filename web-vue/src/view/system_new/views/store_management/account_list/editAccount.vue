<template>
  <div class="experience-version infoForm">
    <div class="select-menu top infoWrapper">
      <el-form
        ref="form"
        :model="formData"
        label-width="100px"
        @submit.prevent="onSubmit"
        style="margin:20px 0px 0px 50px;width:20%;min-width:200px;"
        label-position="left"
        :rules="rules"
      >
        <el-form-item label="用户名">
          <el-input
            v-model="formData.userName"
            class="inputHeight"
            key="1"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input
            v-model="formData.password"
            type="password"
            key="2"
            class="inputHeight"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input
            v-model="formData.accountName"
            class="inputHeight"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="审核状态">
          <el-select
            v-model="formData.state"
            placeholder="请选择状态"
            class="inputHeight"
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
            class="inputHeight"
            label='1000'
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="最大店铺数量">
          <el-input
            v-model="formData.maxShopNum"
            class="inputHeight"
            label='100'
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="首次续费时间">
          <div class="block">
            <el-date-picker
              size="small"
              class="inputHeight"
              v-model="formData.buyTime"
              type="datetime"
              placeholder="选择日期时间"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="到期时间">
          <div class="block">
            <el-date-picker
              size="small"
              class="inputHeight"
              v-model="formData.endTime"
              type="datetime"
              placeholder="选择日期时间"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input
            v-model="formData.mobile"
            class="inputHeight"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="公司名称">
          <el-input
            v-model="formData.company"
            class="inputHeight"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="销售员">
          <el-input
            v-model="formData.salesperson"
            class="inputHeight"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="地理位置">
          <div style="display: flex; width: 800px;">
            <v-distpicker
              style="height:30px"
              class="inputHeight"
              :province="formData.provinceCode"
              :city="formData.cityCode"
              :area="formData.districtCode"
            ></v-distpicker>
          </div>
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input
            v-model="formData.address"
            class="inputHeight"
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
import { addCoountRequest } from '@/api/system/accountList.js'
import VDistpicker from 'v-distpicker'
export default {
  name: 'experienceVersion',
  component: { VDistpicker },
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
      },
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
  methods: {
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
.form {
  margin: 20px 0px 0px 50px;
}
</style>
