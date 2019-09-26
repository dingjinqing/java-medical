<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <el-button
          type="primary"
          @click="add()"
        >{{$t('marketCommon.filter')}}</el-button>
      </div>
      <div class="table_box">
        <div class="filters">
          <div class="filters_item"><span>{{$t('storeList.groupName')}}:</span>
            <el-input
              v-model="queryParams.groupName"
              class="inputWidth"
              size="small"
              :placeholder="$t('storeList.groupNamePlaceholder')"
            ></el-input>
          </div>
          <el-button
            @click="initDataList"
            class="btn"
            type="primary"
            size="small"
          >{{$t('marketCommon.filter')}}</el-button>
        </div>
        <el-table
          v-loading="loading"
          :data="tableData"
          style="width:100%;"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
          :cell-style="{
            'text-align':'center'
          }"
        >
          <el-table-column
            prop="groupName"
            :label="$t('storeList.groupName')"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('storeCommon.createTime')"
          ></el-table-column>
          <el-table-column
            prop="numbers"
            :label="$t('storeList.storeNumber')"
          >
          </el-table-column>

          <el-table-column
            :label="$t('marketCommon.operate')"
            width="230px"
          >
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeCommon.edit')"
                  placement="top"
                >
                  <a @click="edit(scope.row.id)">{{$t('storeCommon.edit')}}</a>
                </el-tooltip>

                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeCommon.delete')"
                  placement="top"
                >
                  <a @click="edit(scope.row.id)">{{$t('storeCommon.delete')}}</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeList.checkStores')"
                  placement="top"
                >
                  <a @click="edit(scope.row.id)">{{$t('storeList.checkStores')}}</a>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { storeGroupList } from '@/api/admin/storeManage/store'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,

      pageParams: {},
      queryParams: {},
      tableData: [],

      // 表格原始数据
      originalData: []
    }
  },
  methods: {
    initDataList () {
      this.loading = true

      storeGroupList(Object.assign(this.queryParams, this.pageParams)).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 表格数据处理/渲染
    handleData (data) {
      this.tableData = data
      this.langDefaultFlag = true
    },
    edit (id) {

    }
  },
  watch: {
    // data内变量国际化
    lang () {
      if (this.langDefaultFlag) {
        // 重新渲染表格数据
        let originalData = JSON.parse(JSON.stringify(this.originalData))
        this.handleData(originalData)
      }
    }
  },
  mounted () {
    this.langDefault()
    this.initDataList()
  }

}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  .navBox {
    background-color: #fff;
    padding: 0 15px 14px;
    margin-bottom: 10px;
  }
  .btn {
    margin-left: 5px;
  }
  .table_box {
    background-color: #fff;
    padding: 15px;
    .filters {
      display: flex;
      line-height: 32px;
      margin-left: -15px;
      margin-bottom: 10px;
      .filters_item {
        max-width: 350px;
        display: flex;
        margin-left: 15px;
        > span {
          min-width: 80px;
          font-size: 14px;
        }
      }
    }
    .operation {
      display: flex;
      flex-wrap: wrap;
      margin-left: -5px;
      > .item {
        font-size: 14px;
        color: #66b1ff;
        cursor: pointer;
        margin-right: 8px;
      }
    }
    .businessStateOperate {
      font-size: 14px;
      color: #66b1ff;
      cursor: pointer;
    }
    .tapOneblock {
      display: flex;
      justify-content: flex-end;
      margin-top: 10px;
      > span {
        height: 32px;
        line-height: 32px;
      }
    }
  }
}
</style>
