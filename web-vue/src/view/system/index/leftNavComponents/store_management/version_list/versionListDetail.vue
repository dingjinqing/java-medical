<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
          <div class="filters_item">
            <el-form
              label-width="auto"
              class="demo-dynamic"
              size="mini"
            >
              <el-form-item
                display:block
                label="版本名称"
              >
                <el-input
                  size="mini"
                  style="width:18%"
                  :placeholder="$t('authRoleList.tip2')"
                  :disabled="true"
                  v-model="versionName"
                />
              </el-form-item>

              <el-form-item
                display:block
                label="版本等级"
              >
                <el-select
                  v-model="level"
                  placeholder="请选择"
                  :disabled="true"
                >
                  <el-option
                    v-for="item in shopVersionList"
                    :key="item.value"
                    :label="item.value+' '+item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                display:block
                label="版本权限"
              >
                <div class="table_box">

                  <el-table
                    border
                    stripe
                    :data="tableData"
                    :show-header='false'
                    style="width: 830px"
                  >
                    <el-table-column
                      align="left"
                      min-width="20%"
                    >
                      <template slot-scope="scope">
                        <el-checkbox-group v-model="checkRowList">
                          <el-checkbox
                            v-if="!isEmpty(scope.row[0])"
                            v-model="scope.row[0].topIndex"
                            :label="scope.row[0].topIndex"
                          >{{$t('versionList.'+scope.row[0].topIndex)}}</el-checkbox>
                        </el-checkbox-group>
                      </template>
                    </el-table-column>
                    <el-table-column align="left">
                      <template slot-scope="scope">
                        <ul
                          v-if="scope.row[0].topIndex===0"
                          class="role_ul"
                          v-for="(subItem,index) in sub_0"
                          :key="index"
                        >
                          <li :class="specialLi">
                            <el-checkbox-group v-model="sub_0List">
                              <el-checkbox
                                :disabled="sub_0CheckList.indexOf(subItem.vsName)!==-1||!isEdit"
                                :class="subItem.vsName"
                                :label="subItem.vsName"
                                :name="subItem.vsName"
                                @change="show(scope.row[0].topIndex,subItem.vsName)"
                              >{{subItem.name}}</el-checkbox>
                            </el-checkbox-group>
                          </li>
                        </ul>

                        <ul
                          v-if="scope.row[0].topIndex===1"
                          class="role_ul"
                          v-for="(subItem,index) in sub_1"
                          :key="index"
                        >
                          <li :class="specialLi">
                            <el-checkbox-group v-model="sub_1List">
                              <el-checkbox
                                :disabled="sub_1CheckList.indexOf(subItem.vsName)!==-1||!isEdit"
                                :class="subItem.vsName"
                                :label="subItem.vsName"
                                :name="subItem.vsName"
                                @change="show(scope.row[0].topIndex,subItem.vsName)"
                              >{{subItem.name}}</el-checkbox>
                            </el-checkbox-group>
                          </li>
                        </ul>

                        <ul
                          v-if="scope.row[0].topIndex===2"
                          class="role_ul"
                          v-for="(subItem,index) in sub_2"
                          :key="index"
                        >
                          <li :class="specialLi">
                            <el-checkbox-group v-model="sub_2List">
                              <el-checkbox
                                :disabled="sub_2CheckList.indexOf(subItem.vsName)!==-1||!isEdit"
                                :class="subItem.vsName"
                                :label="subItem.vsName"
                                :name="subItem.vsName"
                                @change="show(scope.row[0].topIndex,subItem.vsName)"
                              >{{subItem.name}}</el-checkbox>
                            </el-checkbox-group>
                          </li>
                        </ul>

                        <ul
                          v-if="scope.row[0].topIndex===3"
                          class="role_ul"
                          v-for="(subItem,index) in sub_3"
                          :key="index"
                        >
                          <li :class="specialLi">
                            <el-checkbox-group v-model="sub_3List">
                              <el-checkbox
                                :disabled="sub_3CheckList.indexOf(subItem.vsName)!==-1||!isEdit"
                                :class="subItem.vsName"
                                :label="subItem.vsName"
                                :name="subItem.vsName"
                                @change="show(scope.row[0].topIndex,subItem.vsName)"
                              >{{subItem.name}}</el-checkbox>
                            </el-checkbox-group>
                          </li>
                        </ul>

                        <ul
                          v-if="scope.row[0].topIndex===4"
                          class="role_ul"
                          v-for="(subItem,index) in sub_4"
                          :key="index"
                        >
                          <li :class="specialLi">
                            <el-checkbox-group v-model="sub_4List">
                              <el-checkbox
                                :disabled="sub_4CheckList.indexOf(subItem.vsName)!==-1||!isEdit"
                                :class="subItem.vsName"
                                :label="subItem.vsName"
                                :name="subItem.vsName"
                                @change="show(scope.row[0].topIndex,subItem.vsName)"
                              >{{subItem.name}}</el-checkbox>
                            </el-checkbox-group>
                          </li>
                        </ul>

                        <ul
                          v-if="scope.row[0].topIndex===5"
                          class="role_ul"
                          v-for="(subItem,index) in sub_5"
                          :key="index"
                        >
                          <li :class="specialLi">
                            <el-checkbox-group v-model="sub_5List">
                              <el-checkbox
                                :disabled="sub_5CheckList.indexOf(subItem.vsName)!==-1||!isEdit"
                                :class="subItem.vsName"
                                :label="subItem.vsName"
                                :name="subItem.vsName"
                                @change="show(scope.row[0].topIndex,subItem.vsName)"
                              >{{subItem.name}}</el-checkbox>
                            </el-checkbox-group>
                          </li>
                        </ul>
                      </template>
                    </el-table-column>
                  </el-table>

                </div>
              </el-form-item>
              <span
                class="tip1"
                style="color:#999;"
              >数量填-1时表示不限制数量</span>
              <el-form-item
                display:block
                style="margin-top: 15px;width:1024px"
              >

                <el-form
                  label-width="auto"
                  class="dynamic"
                  size="mini"
                  :inline="true"
                  v-if="this.numConfig!==null"
                >
                  <el-col :span="12">
                    <el-form-item label="图片空间大小">
                      <el-input
                        disabled
                        v-model="this.numConfig.picture_num"
                      ></el-input>
                      <span v-if="this.isEdit && this.numConfig.picture_num!==-1">+</span>
                      <el-input
                        v-if="this.isEdit  && this.numConfig.picture_num!==-1"
                        v-model="numConfig.picture_num_plus"
                      ></el-input>
                      <span>M</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="视频空间大小">
                      <el-input
                        disabled
                        v-model="this.numConfig.video_num"
                      ></el-input>
                      <span v-if="this.isEdit  && this.numConfig.video_num!==-1">+</span>
                      <el-input
                        v-if="this.isEdit  && this.numConfig.video_num!==-1"
                        v-model="numConfig.video_num_plus"
                      ></el-input>
                      <span>M</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="商品数量">
                      <el-input
                        disabled
                        v-model="this.numConfig.goods_num"
                      ></el-input>
                      <span v-if="this.isEdit  && this.numConfig.goods_num!==-1">+</span>
                      <el-input
                        v-if="this.isEdit && this.numConfig.goods_num!==-1"
                        v-model="numConfig.goods_num_plus"
                      ></el-input>
                      <span>个</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="门店数量">
                      <el-input
                        disabled
                        v-model="this.numConfig.store_num"
                      ></el-input>
                      <span v-if="this.isEdit && this.numConfig.store_num!==-1">+</span>
                      <el-input
                        v-if="this.isEdit && this.numConfig.store_num!==-1"
                        v-model="numConfig.store_num_plus"
                      ></el-input>
                      <span>个</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="装修页面数量">
                      <el-input
                        disabled
                        v-model="this.numConfig.decorate_num"
                      ></el-input>
                      <span v-if="this.isEdit && this.numConfig.decorate_num!==-1">+</span>
                      <el-input
                        v-if="this.isEdit && this.numConfig.decorate_num!==-1"
                        v-model="numConfig.decorate_num_plus"
                      ></el-input>
                      <span>个</span>
                    </el-form-item>

                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="表单数量">
                      <el-input
                        disabled
                        v-model="this.numConfig.form_num"
                      ></el-input>
                      <span v-if="this.isEdit && this.numConfig.form_num!==-1">+</span>
                      <el-input
                        v-if="this.isEdit && this.numConfig.form_num!==-1"
                        v-model="numConfig.form_num_plus"
                      ></el-input>
                      <span>个</span>
                    </el-form-item>
                  </el-col>
                </el-form>
              </el-form-item>
              <el-button
                v-if="this.isEdit"
                type="primary"
                @click="submit"
                size="mini"
                style="margin-left: 69px;"
              >保存</el-button>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { versionDefRequest, getOneVersionRequest, showShopVersionRequest, editVersionRequest } from '@/api/system/versionList.js'
export default {
  name: 'childConfig',
  props: {
    faClick: { // 父组件的点击
      type: Boolean,
      default: () => false
    },
    isEdit: { // 父组件的点击
      type: Boolean,
      default: () => false
    },
    sendVersion: { // 传版本号
      type: String,
      default: () => 'v0'
    },
    sendShopId: { // 编辑店铺版本权限时候传的shopId
      type: Number,
      default: () => 0
    }
  },
  data () {
    return {
      roleName: null,
      tableData: [],
      mobileList: [],
      groupRoleList: [],
      mobile: null,
      groupRole: null,
      totalRows: null,
      pageRows: '',
      currentPage: 1,
      pageCount: null,
      pagination_b: true,
      page: null,
      AccountId: null,
      RoleId: null,
      centerDialogVisible: false,
      AccountName: null,
      RecId: null,
      Act: null,
      privilegeList: [],
      checkRowList: [],
      checkAllState: false,
      checkedCount: null,
      specialLi: 'specialLi', // 英文适配

      // 定义参数
      versionName: null,
      level: null,
      sub_0: null,
      sub_1: null,
      sub_2: null,
      sub_3: null,
      sub_4: null,
      sub_5: null,
      sub_0List: [],
      sub_1List: [],
      sub_2List: [],
      sub_3List: [],
      sub_4List: [],
      sub_5List: [],
      shopVersionList: this.$t('versionList.shopVersion'),
      numConfig: {},
      main_config: {},
      sub_0CheckList: [],
      sub_1CheckList: [],
      sub_2CheckList: [],
      sub_3CheckList: [],
      sub_4CheckList: [],
      sub_5CheckList: []
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.defaluteData()
  },
  watch: {
    faClick (newData) {
      if (newData === true) {
        this.submitInfo()
      }
      console.log(newData)
    }
  },
  methods: {
    defaluteData () {
      console.log('是编辑吗2222')
      console.log('isedit', this.isEdit)
      console.log(this.sendVersion)
      console.log('啦啦啦啦啦啦啦啦')
      console.log(this.sendVersion !== 'v0')
      this.searchDef()
      if (this.sendVersion !== 'v0') {
        console.log('this.search()')
        this.search()
      }
    },
    searchDef () {
      versionDefRequest().then((res) => {
        if (res.error === 0) {
          this.tableData.push(res.content.sub_0)
          this.tableData.push(res.content.sub_1)
          this.tableData.push(res.content.sub_2)
          this.tableData.push(res.content.sub_3)
          this.tableData.push(res.content.sub_4)
          this.tableData.push(res.content.sub_5)
          this.sub_0 = res.content.sub_0
          this.sub_1 = res.content.sub_1
          this.sub_2 = res.content.sub_2
          this.sub_3 = res.content.sub_3
          this.sub_4 = res.content.sub_4
          this.sub_5 = res.content.sub_5

          console.log(' this.tableData-----------------------------------')
          console.log(this.tableData)
          this.checkedLeft()
          console.log(this.checkRowList)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    search () {
      getOneVersionRequest(this.sendVersion).then((res) => {
        if (res.error === 0) {
          console.log('查回来')
          console.log(res.content)
          let data = res.content.content.main_config
          this.versionName = res.content.versionName
          this.level = res.content.level
          console.log('查询出单独的版本权限')
          console.log(data)
          this.sub_0CheckList = data.sub_0
          this.sub_1CheckList = data.sub_1
          this.sub_2CheckList = data.sub_2
          this.sub_3CheckList = data.sub_3
          this.sub_4CheckList = data.sub_4
          this.sub_5CheckList = data.sub_5

          this.sub_0List = data.sub_0
          this.sub_1List = data.sub_1
          this.sub_2List = data.sub_2
          this.sub_3List = data.sub_3
          this.sub_4List = data.sub_4
          this.sub_5List = data.sub_5
          console.log(this.sub_0CheckList)
          console.log('jieshu')
          this.numConfig = res.content.content.num_config
          console.log(this.numConfig)
          console.log(this.numConfig.picture_num)
          if (this.isEdit === true && this.sendShopId !== 0) {
            console.log('this.searchShops()')
            // 店铺列表页的编辑
            this.searchShop()
          }
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 查询店铺的权限
    searchShop () {
      let param = {
        'shopId': this.sendShopId
      }
      showShopVersionRequest(param).then((res) => {
        if (res.error === 0) {
          console.log('查回来')
          console.log(res.content)
          let data = res.content.main_config
          this.level = res.content.shopType
          this.versionName = res.content.versionName
          console.log('查询出单独的店铺权限')
          console.log(data)
          this.sub_0List = data.sub_0
          this.sub_1List = data.sub_1
          this.sub_2List = data.sub_2
          this.sub_3List = data.sub_3
          this.sub_4List = data.sub_4
          this.sub_5List = data.sub_5
          console.log(this.sub_0List)
          console.log('jieshu')
          this.numConfig = res.content.num_config
          console.log(this.numConfig)
          console.log(this.numConfig.picture_num)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    // 点击右侧后左侧checkbox是否选择
    show (val, val2) {
      console.log(val, val2)
      var a = this.checkRowList.indexOf(val)
      switch (val) {
        case 0:
          if (this.sub_0List.length === 0 && a !== -1) {
            // 全取消了
            this.checkRowList.splice(a, 1)
          } if (this.sub_0List.length !== 0 && a === -1) {
            // 存在
            this.checkRowList.push(val)
          }
          break
        case 1:
          if (this.sub_1List.length === 0 && a !== -1) {
            // 全取消了
            this.checkRowList.splice(a, 1)
          } if (this.sub_1List.length !== 0 && a === -1) {
            // 存在
            this.checkRowList.push(val)
          }
          break
        case 2:
          if (this.sub_2List.length === 0 && a !== -1) {
            // 全取消了
            this.checkRowList.splice(a, 1)
          } if (this.sub_2List.length !== 0 && a === -1) {
            // 存在
            this.checkRowList.push(val)
          }
          break
        case 3:
          if (this.sub_3List.length === 0 && a !== -1) {
            // 全取消了
            this.checkRowList.splice(a, 1)
          } if (this.sub_3List.length !== 0 && a === -1) {
            // 存在
            this.checkRowList.push(val)
          }
          break
        case 4:
          if (this.sub_4List.length === 0 && a !== -1) {
            // 全取消了
            this.checkRowList.splice(a, 1)
          } if (this.sub_4List.length !== 0 && a === -1) {
            // 存在
            this.checkRowList.push(val)
          }
          break
        case 5:
          if (this.sub_5List.length === 0 && a !== -1) {
            // 全取消了
            this.checkRowList.splice(a, 1)
          } if (this.sub_5List.length !== 0 && a === -1) {
            // 存在
            this.checkRowList.push(val)
          }
          break
        default:
          break
      }
      console.log(this.sub_0List)
      console.log(this.sub_0List.length)
      console.log(this.checkRowList)
    },
    // 给左边赋值
    checkedLeft () {
      this.checkRowList = []
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].length > 0) {
          this.checkRowList.push(i)
        }
      }
    },
    // 编辑页面提交信息
    submit () {
      this.main_config.sub_0 = this.sub_0List
      this.main_config.sub_1 = this.sub_1List
      this.main_config.sub_2 = this.sub_2List
      this.main_config.sub_3 = this.sub_3List
      this.main_config.sub_4 = this.sub_4List
      this.main_config.sub_5 = this.sub_5List

      let params = {
        'shopId': this.sendShopId,
        'main_config': this.main_config,
        'num_config': this.numConfig
      }
      editVersionRequest(params).then((res) => {
        if (res.error === 0) {
          console.log('编辑好了')
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 提交信息到父页面
    backHome () {
      let params = {
        'flag': 16
      }
      this.$emit('goHome', params)
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  background: #fff;
  padding-left: 40px;
  padding-top: 20px;
  overflow-y: auto;
}
.role_ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.role_ul li {
  min-width: 80px;
  float: left;
  margin: 0;
  padding: 0;
  display: block;
  width: 128px;
}
.specialLi {
  width: 260px !important;
}
.dynamic /deep/ input {
  width: 100px;
}
.tip1 {
  padding-left: 69px;
}
.line {
  width: 20px;
  //margin-left: 2px;
}
.dynamic /deep/ .el-form-item__content {
  display: inline-flex;
}
.table_box /deep/ .el-checkbox__input.is-disabled + span.el-checkbox__label {
  color: #999;
}

.table_box
  /deep/
  .el-checkbox__input.is-disabled.is-checked
  .el-checkbox__inner {
  background-color: #409eff;
  border-color: #409eff;
  border: 1px solid #fff;
}
</style>
