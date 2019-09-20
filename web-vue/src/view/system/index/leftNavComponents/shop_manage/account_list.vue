<template>
  <div class="outside">
    <div class="content">
      <div class="name">
        <span>系统管理后台</span>
        <span>/</span>
        <span>商家账号添加</span>
      </div>

      <div class="nav">
        <div
          class="nav_left"
          :class="{'title-active':!isSubLogin}"
          @click="switchTab(false)"
        >
          商家账号列表
        </div>
        <div
          class="nav_right"
          :class="{'title-active':isSubLogin}"
          @click="switchTab(true)"
        >
          商家账号添加
        </div>
      </div>

      <div class="nav_content">
        <div
          class="accountList"
          data-type="0"
          v-show="!isSubLogin"
        >
          <div class="addInfo">
            <div class="info_wrapper">
              <select v-model="mainData.state">
                <option
                  disabled
                  value=""
                >选择审核状态</option>
                <option value="1">审核中</option>
                <option value="2">审核通过</option>
                <option value="3">审核不通过</option>
                <option value="4">已禁用</option>
              </select>
              <input
                type="text"
                placeholder="请输入用户名、昵称"
                v-model="mainData.keywords"
              >
              <input
                type="text"
                placeholder="请输入公司名称"
                v-model="mainData.company"
              >
              <button @click="searchAccount()">搜索</button>
            </div>
          </div>

          <div class="formtable">
            <el-table
              :data="formTable"
              style="width: 100%"
              height="400"
              border
            >
              <el-table-column
                prop="userName"
                label="用户名"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="keywords"
                label="昵称"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="company"
                label="公司名称"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="state"
                label="审核状态"
                :formatter="ifendcase"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="shopGrade"
                label="店铺等级"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="shopNumber"
                label="店铺总数"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="addTime"
                label="添加时间"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="buyTime"
                label="首次续费"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="endTime"
                label="到期时间"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="renewMoney"
                label="续费总额"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="mobile"
                label="手机号"
                fit
                align="center"
              ></el-table-column>
              <el-table-column
                prop="operation"
                label="操作"
                fit
                align="center"
              ></el-table-column>
            </el-table>
            <div class="footer">
              <span>每页{{this.pageRows}}行记录，当前页面：{{this.currentPage}}，总页数：{{this.pageCount}}，总记录数为：{{this.totalRows}}</span>
              <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage3"
                layout="prev, pager, next, jumper"
                :page-count="pageCount"
                :small="pagination_b"
              >
              </el-pagination>
            </div>
          </div>
        </div>

        <div
          class="accountadd"
          data-type="1"
          v-show="isSubLogin"
        >
          <div class="infoForm">
            <div class="infoWrapper">
              <el-form
                ref="form"
                :model="formData"
                label-width="100px"
                @submit.prevent="onSubmit"
                style="margin:20px;width:20%;min-width:200px;"
                label-position="left"
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
                      @selected="onSelected"
                      style="height:30px"
                      class="inputHeight"
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
              <div
                class="save"
                @click="save()"
              >保存</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { addCoountRequest, searchAccountRequest } from '@/api/system/accountList.js'
import VDistpicker from 'v-distpicker'
export default {
  components: { VDistpicker },
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
      },
      mainData: {
        currentPage3: 1,
        state: '',
        keywords: '',
        company: ''
      },
      isSubLogin: false,
      // options: [
      //   { value: '选项1' },
      //   { value: '选项2' },
      //   { value: '选项3' },
      //   { value: '选项4' }
      // ],
      // value: '',
      totalRows: null,
      pageRows: '',
      currentPage: '',
      currentPage3: 1,
      pageCount: null,
      pagination_b: true,
      formTable: [{
        usreName: '',
        company: '',
        state: '',
        shopGrade: '',
        shopNumber: '',
        addTime: '',
        buyTime: '',
        endTime: '',
        renewMoney: '',
        mobile: ''
      }]
    }
  },
  created () {
    this.searchAccount()
  },
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
  methods: {
    onSelected (data) {
      this.formData.provinceCode = data.province.code
      this.formData.cityCode = data.city.code
      this.formData.districtCode = data.area.code
      console.log(data)
      // console.log(this.formData.districtCode)
    },
    getProvinceCode (data) {
      this.formData.provinceCode = data.code
      // console.log(this.formData.provinceCode)
    },
    getCityCode (data) {
      this.formData.cityCode = data.code
      // console.log(this.formData.cityCode)
    },
    getDistrictCode (data) {
      this.formData.districtCode = data.code
      // console.log(this.formData.districtCode)
    },

    // 切换对应的两个tab
    switchTab (subLogin) {
      this.isSubLogin = subLogin
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
    },
    // currnentPage 改变时会触发
    handleCurrentChange () {
      this.searchAccount()
    },
    // ifendcase (state) {
    //   if (state = 1) { return '哈哈哈' } else if (state == '2') { return '嗯嗯嗯' } else { return '进行中' }
    // },
    // 商家账号列表查询
    searchAccount () {
      let obj1 = {
        'currentPage': this.currentPage3,
        'pageRows': '20',
        'state': '',
        'keywords': '',
        'company': ''
      }
      let parameter = Object.assign(obj1, this.mainData)
      searchAccountRequest(parameter).then((res) => {
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          let formList = content.dataList
          let pageObj = content.page
          this.totalRows = pageObj.totalRows
          this.currentPage = pageObj.currentPage
          this.firstPage = pageObj.firstPage
          this.lastPage = pageObj.lastPage
          this.nextPage = pageObj.nextPage
          this.pageCount = pageObj.pageCount
          this.pageRows = pageObj.pageRows

          this.formTable = formList
          // console.log(this.formTable) // formTable是一个里面包含10个对象的数组
        }
      }).catch(() => {
        this.$message.error('保存失败')
      })
    }
  }
}
</script>

<style scoped>
.outside {
  width: 100%;
  background: #e6e9f0;
  overflow: auto;
}
.content {
  width: 100%;
  margin: 10px 10px;
}
.name {
  background: #f5f5f5;
  border-radius: 2px;
  height: 34px;
  line-height: 34px;
  margin-bottom: 18px;
}
.name span {
  margin-right: 5px;
  font-size: 14px;
  line-height: 24px;
  height: 24px;
  color: #999;
}
.name span:nth-child(1) {
  color: #333;
  margin-left: 15px;
}
.nav {
  display: flex;
  width: 100%;
  height: 40px;
  line-height: 40px;
  text-align: center;
  font-size: 14px;
  background: #eef1f6;
  cursor: default;
}
.nav_left {
  width: 106px;
}
.nav_right {
  width: 106px;
}
.title-active {
  font-weight: 700;
  border: 1px solid #ddd;
  border-bottom-color: transparent;
  background: #fff;
  border-top: 2px solid green;
  cursor: pointer;
}
.addInfo {
  width: 100%;
  height: 90px;
  margin: 8px 0;
  padding: 15px;
  display: flex;
  align-items: center;
  border-radius: 2px;
  /* border: 1px solid #000; */
  background: #fff;
}
.info_wrapper {
  width: 100%;
  height: 60px;
  padding: 15px;
}
.info_wrapper > select,
.info_wrapper > input {
  width: 150px;
  height: 30px;
  padding-left: 12px;
  border: 1px solid #ccc;
}
.info_wrapper > button {
  width: 85px;
  height: 30px;
  margin-left: 15px;
  border: 1px solid #5a8bff;
  background: #5a8bff;
  color: #fff;
}
.formtable {
  width: 100%;
  background: #f4f9fc;
  /* border: 1px solid #000; */
}
.footer {
  display: flex;
  margin-top: 10px;
  align-items: center;
  justify-content: flex-end;
}
.footer > span {
  font-size: 14px;
}
.infoForm {
  padding: 15px;
  margin-top: 8px;
  margin-bottom: 8px;
  border-radius: 2px;
  background: #fff;
}
.infoWrapper {
  padding: 15px;
}
.el-form-item {
  font-size: 14px;
  min-width: 400px;
  margin: 7px 0;
}
.el-input {
  width: 150px;
}
.el-select {
  width: 150px;
}
.inputHeight > .el-input__inner {
  height: 30px !important;
}
.save {
  width: 52px;
  height: 26px;
  line-height: 26px;
  background: #86a7cb;
  background-color: #86a7cb;
  font-size: 14px;
  text-align: center;
  cursor: pointer;
  border-radius: 2px;
  color: #fff;
  left: 200px;
  margin-left: 118px;
}
</style>
