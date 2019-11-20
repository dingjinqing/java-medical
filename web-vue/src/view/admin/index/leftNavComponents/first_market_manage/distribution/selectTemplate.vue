<template>
  <div class="selectTemplate">
    <!-- 选择模板弹窗 -->
    <el-dialog
      title="选择页面"
      :visible.sync="templateDialog"
      width="50%"
      :close-on-click-modal="false"
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
              >搜索</el-button>
            </el-col>
          </el-row>
        </el-form>

        <el-table
          ref="templateData"
          :data="templateData"
          border
          highlight-current-row
          @current-change="handleCurrentChange"
          height="300px"
          style="width: 100%;"
        >
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
    }
  },
  data () {
    return {
      templateDialog: false, // 模板弹窗
      pageParams: {}, // 分页
      templateData: [], // 模板表格
      templateRow: {}, // 模板弹窗回调函数
      requestParams: {},
      form: {
        rebate_page_id: '' // 推广模版文案id
      },
      formDialog: {
        pageName: null,
        catId: null
      }
    }
  },
  watch: {
    tuneUpSelectTemplate () {
      this.templateDialog = true
    }
  },
  mounted () {
    this.getTemplateData()
  },
  methods: {

    // 选中表格数据
    handleCurrentChange (val) {
      console.log(val)
      this.templateRow = val
      this.form.rebate_page_id = val.pageId
      this.formDialog.pageName = val.pageName
      console.log(this.formDialog.pageName)
    },

    // 模板数据回显
    sureClickHandler () {
      this.templateDialog = false
      this.tamplateFlag = true
      this.$emit('handleSelectTemplate', this.formDialog.pageName, this.form.rebate_page_id)
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
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
</style>
