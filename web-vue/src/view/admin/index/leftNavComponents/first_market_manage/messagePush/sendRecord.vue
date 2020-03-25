<template>
  <div class="sendRecord">
    <el-card>
      <div>
        <el-form
          :inline="true"
          :form="formData"
          label-width="100px"
          size="small"
        >
          <el-form-item
            :label="labels.name"
            prop="userName"
          >
            <el-input
              style="width:120px"
              size="small"
              v-model="queryParams.userName"
            ></el-input>
          </el-form-item>
          <el-form-item :label="labels.isVisit">
            <el-select
              style="width:120px"
              placeholder="请选择"
              v-model="queryParams.isOnClick"
              @change="isOnClickValueChange"
            >
              <el-option
                label="全部"
                :value="0"
              >
              </el-option>

              <el-option
                v-for="item in options1"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="">
            <el-button
              type="primary"
              @click="handleFilter"
              size="small"
            >筛选</el-button>
          </el-form-item>

        </el-form>
      </div>
      <div>
        <el-table
          :data="tableData"
          style="width: 100%"
          center
          border
          header-row-class-name="tableClass"
        >
          <el-table-column
            prop="username"
            :label="tableLabels.name"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="templatePlatfrom"
            :label="tableLabels.templatePlatfrom"
            align="center"
          >
            <template>
              公众号
            </template>
          </el-table-column>
          <el-table-column
            prop="sendStatus"
            :label="tableLabels.sendStatus"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.sendStatus === 1?'是':'否'}}
            </template>
          </el-table-column>
          <el-table-column
            prop="isVisit"
            :label="tableLabels.isVisit"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.isVisit === 1?'是':'否'}}
            </template>
          </el-table-column>
          <el-table-column
            prop="visitTime"
            :label="tableLabels.visitTime"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            :label="tableLabels.createTime"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
      <!-- 分页 -->
      <div>
        <pagination
          :pageParams="pageParams"
          @pagination="fetchData"
        />
      </div>
    </el-card>
  </div>
</template>

<script>
import { recordListApi } from '@/api/admin/marketManage/messagePush'
import pagination from '@/components/admin/pagination/pagination'
export default {
  name: `sendRecord`,
  components: { pagination },
  data () {
    return {
      queryParams: {
        templateId: Number(this.$route.query.id),
        userName: '',
        sendType: 0,
        isOnClick: 0
      },
      pageParams: {
        currentPage: 1

      },
      labels: {
        name: `用户昵称`,
        templatePlatfrom: `发送类型`,
        isVisit: `是否已点击`
      },
      sendType: null,
      isOnClick: null,
      /**
       * 表单数据
       */
      formData: {
        userName: ``,
        templatePlatfrom: ``,
        isVisit: ``
      },
      /**
       * 表格假数据
       */
      tableDataFake: [
        {
          name: `用户昵称1`,
          templatePlatfrom: `2`,
          sendStatus: 1,
          isVisit: 1,
          visitTime: `2019-09-24 13:50:36`,
          createTime: `2019-09-24 13:50:36`
        },
        {
          name: `用户昵称2`,
          templatePlatfrom: `1`,
          sendStatus: 1,
          isVisit: 0,
          visitTime: `2019-09-24 13:50:36`,
          createTime: `2019-09-24 13:50:36`
        },
        {
          name: `用户昵称3`,
          templatePlatfrom: `2`,
          sendStatus: 1,
          isVisit: 0,
          visitTime: `2019-09-24 13:50:36`,
          createTime: `2019-09-24 13:50:36`
        }, {
          name: `用户昵称4`,
          templatePlatfrom: `1`,
          sendStatus: 0,
          isVisit: 1,
          visitTime: `2019-09-24 13:50:36`,
          createTime: `2019-09-24 13:50:36`
        }
      ],
      /**
       * 表格数据
       */
      tableData: [],
      tableLabels: {
        name: `用户昵称`,
        templatePlatfrom: `发送类型`,
        sendStatus: `是否已送达`,
        isVisit: `是否已点击`,
        visitTime: `首次访问时间`,
        createTime: `发送时间`
      },
      sendTypeValue: `全部`,
      isOnClickValue: `全部`,
      options: [
        {
          label: `小程序`,
          value: 1
        },
        {
          label: `公众号`,
          value: 2
        }
      ],
      options1: [
        {
          label: `是`,
          value: true
        },
        {
          label: `否`,
          value: false
        }
      ]
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      console.log(this.queryParams)
      recordListApi(this.queryParams).then(res => {
        console.log(res)
        const { content, content: { page }, error } = res
        if (error === 0) {
          this.tableData = content.dataList
          this.pageParams = page
        }
      }
      ).catch(err => console.log(err))
    },
    // 筛选
    handleFilter () {
      console.log(this.pageParams)
      // const params = {
      //   templateId: Number(this.)
      // }
      this.fetchData()
    },
    //
    sendTypeValueChange (val) {
      console.log(val)
      if (val !== `全部`) {
        this.sendType = val
      }
    },
    isOnClickValueChange (val) {
      console.log(val)
      if (val !== `全部`) {
        this.isOnClick = val
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.sendRecord {
  padding: 10px;
  /deep/ .tableClass th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
}
</style>
