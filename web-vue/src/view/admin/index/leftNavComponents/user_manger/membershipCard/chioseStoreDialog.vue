<template>
  <div class="addBrandDialog">
    <div class="addBrandDialogMain">
      <el-dialog
        title="添加门店"
        :visible.sync="dialogVisible"
        width="50%"
      >
        <div class="footer">
          <el-table
            class="version-manage-table"
            header-row-class-name="tableClss"
            :data="storeParamList"
            border
            style="width: 100%"
            ref="multipleTable"
            @selection-change="changeFun"
          >
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column
              prop="storeName"
              label="门店名称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="address"
              label="门店地址"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="manager"
              label="负责人"
              align="center"
            >

            </el-table-column>
            <el-table-column
              prop="mobile"
              label="联系电话"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="businessTime"
              label="营业时间"
              align="center"
            >

            </el-table-column>
            <el-table-column
              prop="businessState"
              label="营业状态"
              align="center"
            >

            </el-table-column>

          </el-table>
          <div class="pagination">
            <div>{{$t('programVersion.currentPage')}}：{{this.currentPage}}，{{$t('programVersion.totalPage')}}：{{this.pageCount}}，{{$t('programVersion.totalRecord')}}：{{this.totle}}</div>
            <el-pagination
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="20"
              layout="prev, pager, next, jumper"
              :total="totle"
            >
            </el-pagination>
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="dialogVisible = false"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="handleToSure()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>
<script>
import { storeList } from '@/api/admin/storeManage/store'
export default {
  props: {
    tuneUpChooseStore: {
      type: Boolean
    },
    storeBackData: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      dialogVisible: false,
      currentPage: 1,
      totle: 1,
      pageCount: 1,
      checkBoxData: [],
      storeParamList: []
    }
  },
  watch: {
    tuneUpChooseStore (data) {
      this.dialogVisible = true
      this.makeTableShowItemSelected(this.getSelectItems())
    },
    allChecked (newData) {
      console.log(newData)
      switch (newData) {
        case true:
          this.storeParamList.map((item, index) => {
            item.ischeck = true
          })
          break
        case false:
          this.storeParamList.map((item, index) => {
            item.ischeck = false
          })
      }
    }
  },
  mounted () {
    // 初始化数据
    this.defalutData()
  },
  methods: {
    defalutData () {
      this.$http.$on('CallChioseStore', res => {
        this.dialogVisible = true
      })
      this.$http.$on('CallAddBrand', res => {
        this.dialogVisible = true
      })
      let storPageParam = {
        currentPage: 0,
        pageRows: 20
      }

      storeList(storPageParam).then(res => {
        if (res.error === 0) {
          console.log(res.content.dataList)
          this.pageParams = res.content.page
          this.storeParamList = res.content.dataList
          this.storeParamList.map((item, index) => {
            if (item.businessState === 0) {
              item.businessState = '关店'
            } else if (item.businessState === 1) {
              item.businessState = '营业'
            }
            item.autoPick = this.number2boolean(item.autoPick)
            item.businessTime = item.openingTime + '-' + item.closeTime
            item.ischeck = true
          })
        } else {
          this.$message.error(this.$t('storeCommon.getstorelistfailed'))
        }
      })
    },
    // render事件
    renderHeader (h, { column }) {
      return <el-checkbox v-model=''></el-checkbox>
    },
    number2boolean (configValue) {
      if (configValue === 1) {
        return true
      } else if (configValue === 0) {
        return false
      }
    },
    changeFun (val) {
      console.log(val)
      if (val.length) {
        this.storeParamList.forEach((item, index) => {
          item.ischeck = true
        })
      } else {
        this.storeParamList.forEach((item, index) => {
          item.ischeck = false
        })
      }

      console.log(val)
      this.checkBoxData = val
    },
    // 当前页改变
    handleCurrentChange () {

    },
    // 确定事件
    handleToSure () {
      let arr = []
      this.storeParamList.forEach(item => {
        if (item.ischeck) {
          arr.push(item)
        }
      })
      console.log(arr)
      this.$emit('getChoosedStore', this.checkBoxData)
      this.dialogVisible = false
      // this.$emit('update:dialogVisible', false)
      this.$refs.multipleTable.clearSelection()
    },
    getSelectItems () {
      let selectedItems = []
      this.storeParamList.forEach(itemA => {
        this.storeBackData.forEach(itemB => {
          if (itemB.storeId === itemA.storeId) {
            selectedItems.push(itemA)
          }
        })
      })
      return selectedItems
    },
    makeTableShowItemSelected (selectedItems) {
      console.log(selectedItems)
      this.$nextTick(() => {
        this.$refs.multipleTable.clearSelection()
      })
      if (selectedItems) {
        selectedItems.forEach(row => {
          this.$nextTick(() => {
            this.$refs.multipleTable.toggleRowSelection(row, true)
          })
        })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.addBrandDialog {
  /deep/ .el-dialog__header {
    text-align: center;
    background: #f3f3f3;
  }

  .footer {
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
      .el-checkbox {
        margin-left: -4px;
      }
    }
    .operation {
      display: flex;
      justify-content: space-around;
      .iconSpn {
        display: block;
        font-size: 20px;
        color: #5a8bff;
        cursor: pointer;
      }
    }
  }
  .pagination {
    background-color: #fff;
    height: 50px;
    line-height: 50px;
    color: #333;
    font-size: 14px;
    display: flex;
    justify-content: flex-end;
    padding-right: 10px;
    /deep/ .el-pagination {
      display: flex;
      align-items: center;
      .el-pager {
        display: flex;
        align-items: center;
      }
    }
  }
}
</style>
