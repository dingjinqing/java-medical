<template>
  <div class="goodsPageTemplate">
    <el-dialog
      title="选择模板"
      center
      :visible.sync="visible"
      :before-close="handleClose"
    >
      <el-form
        :inline="true"
        :model="formData"
      >
        <el-form-item
          label="页面名称"
          prop="name"
        >
          <el-input
            placeholder="请输入页面名称"
            size="small"
            v-model="formData.name"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="页面分类"
          prop="sort"
        >
          <el-select
            size="small"
            clearable
            v-model="select"
            placeholder="请选择分类"
          >
            <el-option
              v-for="item in dataList"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            @click="handleSearch"
            type="primary"
            size="small"
          >搜索</el-button>
        </el-form-item>
      </el-form>
      <el-table
        highlight-current-row
        @current-change="handleCurrentChange"
        :data="tableData"
        border
        height="250"
        :header-cell-style="{'text-align':'center',background:'#eef1f6'}"
      >
        <el-table-column
          align="center"
          property="pageName"
          label="页面名称"
          width="250"
        ></el-table-column>
        <el-table-column
          align="center"
          property="createTime"
          label="创建时间"
          width="250"
        ></el-table-column>
        <el-table-column
          align="center"
          property="pageType"
          label="是否首页"
          width=""
        ></el-table-column>
      </el-table>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="handleClose"
          size="small"
        >确 定</el-button>
        <el-button
          @click="handleClose"
          size="small"
        >取 消</el-button>
        <!-- 分页 -->
        <section>
          <pagination
            :total="total"
            :page.sync="data.page"
            :limit.sync="data.limit"
            @pagination="fetchDataList"
          />
        </section>
        <el-button @click="handleTest">测试按钮</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
// 引入分页组件
import pagination from '@/components/admin/pagination/pagination'
import { getPageclassificationData } from '@/api/admin/smallProgramManagement/pageClassification/pageClassification'

import { shopDecorateList } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting'
export default {
  name: 'goodsPageTemplate',
  components: { pagination },
  props: {
    visible: {
      type: Boolean
    }
  },
  mounted () {
    // 初始化选择模板的数据
    this.initGetData()
  },
  data () {
    return {
      tableData: [],
      goodsPageId: '',
      dataList: [],
      select: '请选择分类',
      formData: {
        name: '',
        sort: ''
      },
      // 分页的相关数据
      total: 0,
      data: {
        page: null,
        limit: null
      }
    }
  },
  methods: {
    // 测试数据
    handleTest () {
      this.fetchDataList()
    },
    handleSearch () {

    },
    handleClose () {
      this.$emit('close')
    },
    // 选中时的事件
    handleCurrentChange (currentRow) {
      console.log(currentRow)
      console.log(currentRow.pageId)
      this.goodsPageId = currentRow.pageId
    },
    // 初始化数据
    initGetData () {
      // 页面分类查询
      getPageclassificationData({}).then(res => {
        const { error, content } = res
        if (error === 0) {
          this.dataList = content.dataList
        }
      }).catch(err => console.log(err))
      // 表格的数据
      shopDecorateList({
        'pageName': null,
        'catId': null
      }).then(res => {
        const { error, content } = res
        if (error === 0) {
          this.tableData = content.dataList
        }
      }).catch(err => console.log(err))
    },
    // 获取数据
    fetchDataList () {
      // getList({}).then(res => {
      //   console.log(res)
      // }).catch(err => console.log(err))
    }
  }

}
</script>
<style lang="css">
</style>
