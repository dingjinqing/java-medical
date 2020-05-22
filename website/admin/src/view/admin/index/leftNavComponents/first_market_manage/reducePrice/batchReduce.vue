<template>
  <div class="container">

    <!-- 搜索 -->
    <div>
      <el-form label-width="100px">
        <el-form-item
          label="活动时间："
          class="item"
        >
          <el-date-picker
            type="datetime"
            placeholder="开始日期"
            size="small"
            v-model="requestParams.startTime"
            value-format="yyyy-MM-dd HH:mm:ss"
            class="inputWidth"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            type="datetime"
            placeholder="结束日期"
            size="small"
            v-model="requestParams.endTime"
            value-format="yyyy-MM-dd HH:mm:ss"
            class="inputWidth"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="活动名称："
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.name"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="商品编码："
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.code"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="活动状态："
          class="item"
        >
          <el-select
            size="small"
            class="inputWidth"
            placeholder="请选择"
            v-model="requestParams.status"
          >
            <el-option
              v-for="item in statusOption"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div>
        <el-button
          type="primary"
          size="small"
          @click="addHandler"
        >添加限时降价活动</el-button>
        <el-button
          type="primary"
          size="small"
        >导出改价活动</el-button>
        <el-button
          type="primary"
          size="small"
        >查询</el-button>
      </div>
    </div>

    <!-- 表格 -->
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop=""
          label="商品编码"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="商品价格"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="活动名称"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="有效期"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="活动状态"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="添加时间"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>

  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  data () {
    return {
      // 搜索数据
      requestParams: {
        startTime: '',
        endTime: '',
        name: '',
        code: '',
        status: ''
      },
      // 活动状态
      statusOption: [{
        label: '进行中',
        value: '1'
      }, {
        label: '未开始',
        value: '2'
      }, {
        label: '已过期',
        value: '3'
      }, {
        label: '已停用',
        value: '4'
      }],
      tableData: [], // 表格数据
      pageParams: {} // 分页
    }
  },
  watch: {
    lang () {
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  methods: {
    initDataList () {

    },

    addHandler () {
      // 子组件中触发父组件方法ee并传值cc12345
      this.$emit('addHandler')
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  margin-top: 10px;
  background-color: #fff;
  padding: 15px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  //   padding: 15px;
}
.item {
  display: inline-block;
}
.inputWidth {
  width: 170px;
}
</style>
