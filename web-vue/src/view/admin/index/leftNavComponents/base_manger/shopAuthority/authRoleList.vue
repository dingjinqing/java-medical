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
                :label="$t('authRoleList.firstPageLab1')"
              >

                <el-input
                  size="mini"
                  style="width:35%"
                  :placeholder="$t('authRoleList.tip2')"
                  prefix-icon="el-icon-search"
                  v-model="roleName"
                />
              </el-form-item>
              <el-form-item :label="$t('authRoleList.firstPageLab2')">

                <el-checkbox
                  name="checkAll"
                  v-model="checkAllState"
                  @change="checkAll"
                >{{$t('authRoleList.allCheck')}}</el-checkbox>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <div class="table_box">

          <el-table
            :data="tableData"
            border
            stripe
            :show-header='false'
            style="width: 100%"
          >
            <el-table-column
              align="left"
              min-width="40%"
            >
              <template slot-scope="scope">
                <el-checkbox-group v-model="checkRowList">
                  <el-checkbox
                    v-if="!isEmpty(scope.row[0])"
                    v-model="scope.row[0].topIndex"
                    :label="scope.row[0].topIndex"
                    @change="checkRow()"
                  >{{$t('authRoleList.'+scope.row[0].topIndex)}}</el-checkbox>
                </el-checkbox-group>
              </template>
            </el-table-column>
            <el-table-column align="left">
              <template slot-scope="scope">
                <ul
                  class="role_ul"
                  v-for="(subItem,index) in scope.row"
                  :key="index"
                >
                  <li :class="specialLi">
                    <el-checkbox-group v-model="privilegeList">
                      <el-checkbox
                        :class="subItem.enName"
                        :label="subItem.enName"
                        :name="subItem.enName"
                        @change="show(scope.row[0].topIndex,subItem.enName)"
                      >{{$t('authRoleList.'+subItem.enName)}}</el-checkbox>
                    </el-checkbox-group>
                  </li>
                </ul>
              </template>

            </el-table-column>
          </el-table>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defRoleListRequest, editViewRoleRequest } from '@/api/admin/basicConfiguration/shopAuthority.js'
export default {
  name: 'childConfig',
  props: {
    isEdit: { // 是否是编辑
      type: Number,
      default: () => 0
    }
  },
  data () {
    return {
      roleName: null,
      tableData: [{}],
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
      specialLi: 'specialLi' // 英文适配
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.defaluteData()
  },
  watch: {

  },
  methods: {
    defaluteData () {
      console.log('是编辑吗1111')
      console.log(this.isEdit)
      this.search()
    },
    uploadData () {
      console.log('test111111111111111')
      console.log('角色页submitInfo')
      this.submitInfo()
    },
    search () {
      defRoleListRequest().then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.shopMenuList
          console.log(' this.tableData-----------------------------------')
          console.log(this.tableData)
          if (this.isEdit !== 0) {
            // 编辑前的查询
            console.log('编辑前的查询')
            this.beforeEdit()
          } else {
            console.log('赋值所有')
            this.checkedAll()
          }
        } else {
          this.$message.error(res.message)
        }
      })
    },
    beforeEdit () {
      let param = {
        'roleId': this.isEdit
      }
      editViewRoleRequest(param).then((res) => {
        console.log('编辑之前的查询')
        console.log(res)
        if (res.error === 0) {
          this.privilegeList = res.content.privilegeList
          console.log('编辑查询的内容')
          console.log(this.privilegeList)
          var maxL = 0
          for (let n = 0; n < this.tableData.length; n++) {
            maxL = maxL + this.tableData[n].length
            for (let m = 0; m < this.tableData[n].length; m++) {
              for (let i = 0; i < this.privilegeList.length; i++) {
                if (this.privilegeList[i] === this.tableData[n][m].enName) {
                  let idex = this.tableData[n][m].topIndex
                  let a = this.checkRowList.indexOf(idex)
                  if (a === -1) {
                    this.checkRowList.push(idex)
                  }
                  break
                }
              }
            }
          }
          this.checkedCount = maxL
          console.log('this.checkRowList')
          console.log(this.checkRowList)
          this.checkAllState = maxL === this.privilegeList.length
          this.roleName = res.content.roleName
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
      var arr = document.getElementsByName(val2)
      console.log(arr[0].checked === true)
      var a = this.checkRowList.indexOf(val)
      if (arr[0].checked === true) {
        if (a === -1) {
          this.checkRowList.push(val)
        }
      } else {
        let vlength = this.tableData[val].length
        // 取消遍历所有子节点，全部不选中时候把checkRowList中字段删掉
        let num = vlength
        console.log('开始时候', num)
        for (let i = 0; i < vlength; i++) {
          var array = document.getElementsByName(this.tableData[val][i].enName)
          if (array[0].checked === true) {
            console.log(this.tableData[val][i].enName, '选中')
            num++
          } else {
            num--
            console.log(this.tableData[val][i].enName, '取消')
          }
        }
        console.log('总数', num)
        if (num === 0) {
          // 全部取消了
          if (a === -1) {
            console.log('不存在，不删')
          } else {
            this.checkRowList.splice(a, 1)
          }
        }
      }
      this.checkAllState = this.checkedCount === this.privilegeList.length
      console.log(this.checkRowList)
    },
    checkRow () {
      console.log('checkRowList')
      console.log(this.checkRowList)
      this.privilegeList = []
      for (let i = 0; i < this.checkRowList.length; i++) {
        var data = this.tableData[this.checkRowList[i]]
        for (let n = 0; n < data.length; n++) {
          this.privilegeList.push(data[n].enName)
        }
      }
      this.checkAllState = this.checkedCount === this.privilegeList.length
    },
    checkAll () {
      // var doc = document.getElementsByName('checkAll')
      if (this.checkAllState) {
        this.checkedAll()
      } else {
        this.checkRowList = []
        this.privilegeList = []
      }
    },
    checkedAll () {
      this.checkRowList = []
      this.privilegeList = []
      console.log('所有')
      console.log(this.tableData)
      for (let i = 0; i < this.tableData.length; i++) {
        this.checkRowList.push(i)
        console.log(i)
        for (let n = 0; n < this.tableData[i].length; n++) {
          this.privilegeList.push(this.tableData[i][n].enName)
        }
      }
      this.checkedCount = this.privilegeList.length
      this.checkAllState = true
    },
    // 提交信息到父页面
    submitInfo () {
      if (this.isEmpty(this.roleName)) {
        this.$message.error('权限组名称不能为空')
        return false
      }
      let param = {
        'roleName': this.roleName,
        'privilegeList': this.privilegeList
      }
      this.$emit('privilegeInfo', param)
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  height: 400px;
  overflow-y: auto;
}
.role_ul {
  list-style: none;
  padding: 0;
  margin: 0;
  max-width: 560px;
}

.role_ul li {
  min-width: 80px;
  float: left;
  margin: 0;
  padding: 0;
  display: block;
  width: 122px;
}
.specialLi {
  width: 260px !important;
}
</style>
