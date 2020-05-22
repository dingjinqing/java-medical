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
          label-width="100px"
        >
          <el-form-item
            label="页面名称："
            class="item"
          >
            <el-input
              v-model="formDialog.pageName"
              class="inputWidth"
              size="small"
              placeholder="请输入页面名称"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="页面分类："
            class="item"
          >
            <el-select
              v-model="formDialog.catId"
              placeholder="请选择"
              size="small"
              class="inputWidth"
            >
              <el-option
                v-for="item in sortList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item class="item">
            <el-button
              type="primary"
              size="small"
              @click="getTemplateData"
            >搜索</el-button>
          </el-form-item>
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
import { getPageCate } from '@/api/admin/decoration/pageSet.js'

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
      formDialog: {
        pageName: null,
        catId: null
      },
      sortList: [] // 页面分类列表
    }
  },
  watch: {
    tuneUpSelectTemplate () {
      this.templateDialog = true
    }
  },
  mounted () {
    this.getTemplateData()
    // 页面分类
    this.getPageSort()
  },
  methods: {

    // 选中表格数据
    handleCurrentChange (val) {
      console.log(val)
      this.templateRow = val
      console.log(this.templateRow)
    },

    // 模板数据回显
    sureClickHandler () {
      this.templateDialog = false
      this.tamplateFlag = true
      this.$emit('handleSelectTemplate', this.templateRow)
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
    },

    // 获取页面分类
    getPageSort () {
      getPageCate().then(res => {
        if (res.error === 0) {
          this.sortList = res.content
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
.inputWidth {
  width: 170px;
}
.item {
  display: inline-block;
}
</style>
