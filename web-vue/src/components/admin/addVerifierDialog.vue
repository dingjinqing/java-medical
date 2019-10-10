<template>
  <div>
    <!-- 添加核销员弹窗 -->
    <el-button
      type="primary"
      size="small"
      @click="dialogVisible = true"
    >{{$t('verifierManage.addVerifier')}}</el-button>

    <el-dialog
      :title="$t('verifierManage.addVerifier')"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      lock-scroll
      width="900px"
    >
      <div class="verifier-list">
        <header>
          <ul class="verifier-header">
            <li>
              <label>ID</label>
              <el-input
                class="verifier-input"
                size="small"
                v-model="queryParams.userId"
              ></el-input>
            </li>
            <li>
              <label>{{$t('verifierManage.nickname')}}</label>
              <el-input
                class="verifier-input"
                size="small"
                v-model="queryParams.username"
              ></el-input>
            </li>
            <li>
              <label>{{$t('verifierManage.phoneNumber')}}</label>
              <el-input
                class="verifier-input"
                size="small"
                v-model="queryParams.mobile"
              ></el-input>
            </li>
            <li style="width:100px;">
              <el-button
                type="primary"
                size="small"
                @click="searchHandle"
              >{{$t('verifierManage.search')}}</el-button>
            </li>
          </ul>
        </header>
        <section>
          <el-table
            ref="addVerifierTable"
            :data="tableData"
            class="tableClass"
            max-height="340"
            border
            @row-click="rowClickHandle"
            @selection-change="selectionChangeHandle"
            :header-cell-style="{
              'background-color':'#f5f5f5',
              'border':'none'
            }"
          >
            <el-table-column
              type="selection"
              width="100"
            ></el-table-column>
            <el-table-column
              prop="userId"
              label="ID"
            >
            </el-table-column>
            <el-table-column
              prop="username"
              :label="$t('verifierManage.nickname')"
            >
            </el-table-column>
            <el-table-column
              prop="mobile"
              :label="$t('verifierManage.phoneNumber')"
            >
            </el-table-column>
          </el-table>
          <div>
            <pagination
              :page-params.sync="pageParams"
              @pagination="initDataList"
            ></pagination>
          </div>
        </section>
      </div>
      <div slot="footer">
        <el-button
          type="primary"
          size="small"
          @click="addVerifierHandle"
        >{{$t('verifierManage.determine')}}</el-button>
        <el-button
          size="small"
          @click="dialogVisible = false"
        >{{$t('verifierManage.cancel')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAllVerifierList } from '@/api/admin/storeManage/verifierManage'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      dialogVisible: false,
      queryParams: {
        storeId: '',
        userId: '',
        username: '',
        mobile: ''
      },
      pageParams: {},
      tableData: [],
      selected: []
    }
  },
  props: {
    storeId: {
      default: () => ''
    }
  },
  created () {
    this.queryParams.storeId = this.storeId
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getAllVerifierList(params).then(res => {
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      })
    },
    searchHandle () {
      this.initDataList()
    },
    addVerifierHandle () {
      this.$emit('select-change', this.selected)
      this.dialogVisible = false
    },
    rowClickHandle (row) {
      this.$refs.addVerifierTable.toggleRowSelection(row)
    },
    selectionChangeHandle (rows) {
      console.log(rows)
      this.selected = rows
    }
  }
}
</script>

<style scoped>
.verifier-header {
  display: flex;
}
.verifier-header li {
  flex: 1;
}
.verifier-input {
  width: 140px;
  margin-right: 20px;
  margin-left: 4px;
}
.tableClass {
  margin-top: 10px;
}
</style>
