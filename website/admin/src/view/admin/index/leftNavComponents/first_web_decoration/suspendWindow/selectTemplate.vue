<template>
  <div class="selectTemplate">
    <!-- 选择模板弹窗 -->
    <el-dialog
      title="选择页面"
      :visible.sync="templateDialog"
      width="60%"
      center
    >
      <div style="width: 100%; text-align: center;">
        <el-form
          ref="formDialog"
          :model="formDialog"
          label-width="90px"
        >
          <el-row>
            <el-col :span="8">
              <el-form-item label="页面名称：">
                <el-input
                  v-model="formDialog.pageName"
                  style="width: 150px;"
                  size="small"
                  placeholder="请输入页面名称"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="页面分类：">
                <el-input
                  v-model="formDialog.catId"
                  style="width: 150px;"
                  size="small"
                  placeholder="请选择页面分类"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-button
                type="primary"
                size="small"
                style="margin-top: 5px;"
                @click="getTemplateData ()"
              >搜索</el-button>
            </el-col>
          </el-row>
        </el-form>

        <el-table
          ref="selectPageTable"
          :data="templateData"
          border
          highlight-current-row
          @selection-change="handleCurrentChange"
          :row-key="getRowKeys"
          height="300px"
          style="width: 100%;"
        >
          <el-table-column
            type="selection"
            :reserve-selection="true"
            width="55"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="pageName"
            label="页面名称"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="typeText"
            label="是否首页"
            align="center"
          ></el-table-column>
        </el-table>
        <Pagination
          :page-params.sync="pageParams"
          @pagination="getTemplateData"
        />
      </div>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="templateDialog = false"
        >取 消</el-button>
        <el-button
          type="primary"
          size="small"
          @click="sureClickHandler()"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { shopDecorateList } from '@/api/admin/marketManage/distribution.js'

export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination.vue') // 分页
  },

  props: {
    tuneUpSelectTemplate: { // 调起选择模板弹窗
      type: Boolean,
      default: () => false
    },
    backSelectData: Array // 数据回显

  },
  data () {
    return {
      templateDialog: false, // 模板弹窗
      pageParams: {}, // 分页
      templateData: [], // 模板表格
      templateRow: [], // 模板弹窗回调函数
      requestParams: {},
      formDialog: {
        pageName: null,
        catId: null
      },
      initBackData: [],
      newBackData: []
    }
  },
  watch: {
    tuneUpSelectTemplate () {
      this.templateDialog = true
      console.log(this.templateData)
      this.newBackData = JSON.parse(JSON.stringify(this.backSelectData))
      this.$nextTick(() => {
        this.initBackData = this.backSelectData
        console.log('触发')
        this.$refs.selectPageTable.clearSelection()
        this.handleToBackData()
      })
      console.log(this.$refs)
    }
  },
  mounted () {
    this.isFirst = true
    this.getTemplateData()
  },
  methods: {
    getRowKeys (row) {
      return row.pageId
    },
    // 回显数据
    handleToBackData () {
      console.log(this.initBackData)
      this.templateData.forEach((item, index) => {
        this.initBackData.forEach((itemC, indexC) => {
          if (item.pageId === itemC) {
            let flag = false
            console.log(this.templateRow)
            this.templateRow.forEach((itemD, indexD) => {
              if (itemD.pageId === item.pageId) {
                flag = true
              }
            })
            if (!flag) {
              this.newBackData.forEach((v, i) => {
                if (v === item.pageId) {
                  this.newBackData.splice(i, 1)
                }
              })
              console.log(this.newBackData, indexC)
              this.$refs.selectPageTable.toggleRowSelection(item, true)
            }
            console.log(this.$refs)
          }
        })
      })
    },
    // 选中表格数据
    handleCurrentChange (val) {
      console.log(val, this.newBackData)
      this.templateRow = val
      console.log(this.templateRow)
    },

    // 模板数据回显
    sureClickHandler () {
      this.templateDialog = false
      this.tamplateFlag = true
      let arr = this.templateRow.concat(this.newBackData)
      console.log(arr)
      this.$emit('handleSelectTemplate', arr)
    },

    // 获取模板弹窗表格数据
    getTemplateData () {
      this.requestParams.pageName = this.formDialog.pageName
      this.requestParams.catId = this.formDialog.catId
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      shopDecorateList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.templateData = res.content.dataList
          // 表格数据处理
          this.templateData.map((item, index) => {
            if (item.pageType === 1) {
              item.typeText = '是'
            } else {
              item.typeText = '否'
            }
          })
          // this.$refs.selectPageTable.clearSelection()

          this.handleToBackData()
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
</style>
