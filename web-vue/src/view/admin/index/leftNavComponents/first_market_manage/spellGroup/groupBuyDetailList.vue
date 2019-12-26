<template>
  <div class="content">
    <div class="main">
      <el-form
        label-width="100px"
        style="height: 40px;"
      >
        <el-row :gutter=24>
          <el-col :span="5">
            <el-form-item :label="$t('groupBuy.userMobileNumber') + '：'">
              <el-input
                size="small"
                v-model="mobile"
                :placeholder="$t('groupBuy.mobileNumber')"
                maxlength="11"
                clearable
                class="inputWidth"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item :label="$t('groupBuy.userNickname') + '：'">
              <el-input
                size="small"
                v-model="nickname"
                :placeholder="$t('groupBuy.nickname')"
                clearable
                class="inputWidth"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item :label="$t('groupBuy.grouponState') + '：'">
              <el-select
                size="small"
                v-model="stauts"
                class="inputWidth"
              >
                <el-option
                  v-for="item in stateOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-button
              size="small"
              type="primary"
              @click="searchData"
              style="margin: 4px 0 0 0"
            >{{$t('groupBuy.searchDataText')}}
            </el-button>
          </el-col>
        </el-row>
      </el-form>

    </div>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="groupId"
          :label="$t('groupBuy.commanderName')"
          align="center"
        ></el-table-column>       <el-table-column
          prop="commanderName"
          :label="$t('groupBuy.commanderName')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="commanderMobile"
          :label="$t('groupBuy.commanderMobile')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="username"
          :label="$t('groupBuy.username')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          :label="$t('groupBuy.mobile')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="status"
          :label="$t('groupBuy.grouponState')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="isDefault"
          :label="$t('groupBuy.isDefault')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="orderSn"
          :label="$t('groupBuy.orderSn')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="startTime"
          :label="$t('groupBuy.startTime')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="endTime"
          :label="$t('groupBuy.endTime')"
          align="center"
        ></el-table-column>
      </el-table>

      <pagination
        :page-params.sync="pageParams"
        @pagination="initialize"
      />
    </div>

  </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import { detailGroupBuy } from '@/api/admin/marketManage/spellGroup.js'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: {
    wrapper,
    pagination
  },
  data () {
    return {
      mobile: null,
      nickname: null,
      stauts: 0,
      tableData: [],
      pageParams: {},
      stateOptions: {}
    }
  },
  watch: {
    lang () {
      this.stateOptions = this.$t('groupBuy.stateOptions')
    }
  },
  mounted () {
    // 初始化
    this.langDefault()
    this.initialize()
  },
  methods: {
    // 初始化
    initialize () {
      console.log('初始化', this.$route.query.id)
      if (!this.$route.query.id) {
        this.$router.push({ path: `/admin/home/main/spellGroup` })
      }
      this.searchData()
    },
    // 查询
    searchData () {
      this.tableData.loading = true
      let obj = {
        nickName: this.nickname,
        mobile: this.mobile,
        stauts: this.stauts,
        activityId: this.$route.query.id,
        currentPage: this.pageParams.currentPage,
        pageRows: this.pageParams.pageRows
      }
      detailGroupBuy(obj).then(res => {
        console.log(res)
        this.pageParams = res.content.page
        this.tableData = res.content.dataList
        this.tableData.loading = true
      })
    }
  }

}

</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 15px;
    .el-col {
      height: 40px;
      line-height: 40px;
    }
    .wrapper {
      .el-button {
        margin-left: 5px;
      }
    }
  }
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
  padding: 15px;
}
.inputWidth {
  width: 170px;
}
</style>
