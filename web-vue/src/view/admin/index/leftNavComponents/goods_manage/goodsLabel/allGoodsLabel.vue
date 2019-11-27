<template>
  <div class="contentWrap">
    <!-- class="content" -->
    <div>
      <div class="contentHeader">
        <span style="font-size: 14px;">{{ $t('allGoodsLabel.labelName') + '：' }}</span>
        <el-input
          v-model="labelName"
          size="small"
          style="width:170px;"
          clearable
          :placeholder="$t('allGoodsLabel.inputLabelName')"
        />
        <el-button
          type="primary"
          size="small"
          @click="fetchGoodsLabelData"
          style="cursor:pointer;"
        >{{$t('allGoodsLabel.search')}}</el-button>
        <el-button
          type="primary"
          size="small"
          style="float: right;"
          @click="addGoodsLabelClicked"
        >{{$t('allGoodsLabel.addLabel')}}</el-button>
      </div>
      <div class="contentBody">
        <el-table
          :data="goodsLabelData"
          class="tableClass"
          border
          style="width: 100%"
        >
          <el-table-column
            align="center"
            prop="name"
            :label="$t('allGoodsLabel.labelName')"
          />
          <el-table-column
            align="center"
            prop="updateTime"
            :label="$t('allGoodsLabel.updateTime')"
          />
          <el-table-column
            align="center"
            :label="$t('allGoodsLabel.webUseModel')"
          >
            <template slot-scope="{row}">
              <div style="display: flex;justify-content: space-around;align-items: center;">
                <ul>
                  <li v-if="row.goodsDetail === 1">{{$t('allGoodsLabel.goodsDetailPage')}}</li>
                  <li v-if="row.goodsList === 1">{{$t('allGoodsLabel.goodsListPage')}}</li>
                  <li v-if="row.goodsSelect === 1">{{$t('allGoodsLabel.goodsSelectPage')}}</li>
                </ul>
                <el-tooltip
                  :content="$t('allGoodsLabel.setting')"
                  placement="top"
                >
                  <span
                    class="el-icon-edit-outline operateSpan"
                    @click="useModelSettingClicked(row)"
                  ></span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="level"
            :label="$t('allGoodsLabel.priority')"
          />
          <el-table-column
            align="center"
            :label="$t('allGoodsLabel.goodsNumber')"
          >
            php查询了es,目前未实现
          </el-table-column>
          <el-table-column
            align="center"
            :label="$t('allGoodsLabel.operate')"
          >
            <template slot-scope="{row}">
              <el-tooltip
                :content="$t('allGoodsLabel.update')"
                placement="top"
              >
                <span
                  class="el-icon-edit-outline operateSpan"
                  @click="updateLabelSettingClicked(row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('allGoodsLabel.delete')"
                placement="top"
              >
                <span
                  class="el-icon-delete operateSpan"
                  @click="deleteLabelSettingClicked(row)"
                ></span>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="fetchGoodsLabelData"
        />
      </div>
    </div>
    <el-dialog
      :title="$t('allGoodsLabel.chooseUsePosition')"
      :visible.sync="dialogVisible"
      width="40%"
      center
    >
      <div style="font-size: 12px;border: 1px solid #FFD5A3;margin: 0 auto;padding: 5px;background: #FFF7EB;">{{$t('allGoodsLabel.chooseUsePositionTip')}}</div>
      <ul>
        <li>
          <el-checkbox
            v-model="currentData.goodsDetail"
            :true-label="1"
            :false-label="0"
          >{{$t('allGoodsLabel.goodsDetailPage')}}</el-checkbox>
        </li>
        <li>
          <el-checkbox
            v-model="currentData.goodsList"
            :true-label="1"
            :false-label="0"
          >{{$t('allGoodsLabel.goodsListPage')}}</el-checkbox>
        </li>
        <li>
          <el-checkbox
            v-model="currentData.goodsSelect"
            :true-label="1"
            :false-label="0"
          >{{$t('allGoodsLabel.goodsSelectPage')}}</el-checkbox>
        </li>
      </ul>
      <div
        v-if="currentData.goodsList === 1"
        style="margin-top: 5px;background: #f8f8f8;padding: 10px 15px 20px 10px;border: 1px solid #eee;border-radius: 5px;"
      >
        <span><em style="color: red;">*</em>{{$t('allGoodsLabel.labelStyle')}}:</span>
        <div style="margin-top: 5px;display: flex;justify-content: space-around;">
          <div style="flex-grow: 1;text-align: center;">
            <div>图片1</div>
            <el-radio
              v-model="currentData.listPattern"
              :label="1"
            >{{""}}</el-radio>
          </div>
          <div style="flex-grow: 1;text-align: center;">
            <div>图片2</div>
            <el-radio
              v-model="currentData.listPattern"
              :label="2"
            >{{""}}</el-radio>
          </div>
          <div style="flex-grow: 1;text-align: center;">
            <div>图片3</div>
            <el-radio
              v-model="currentData.listPattern"
              :label="3"
            >{{""}}</el-radio>
          </div>
          <div style="flex-grow: 1;text-align: center;">
            <div>图片4</div>
            <el-radio
              v-model="currentData.listPattern"
              :label="4"
            >{{""}}</el-radio>
          </div>
        </div>
      </div>
      <div slot="footer">
        <el-button
          @click="updateGoodsLabelUseModel"
          type="primary"
          size="small"
        >{{$t('allGoodsLabel.confirm')}}</el-button>
        <el-button
          @click="dialogCancel"
          size="small"
        >{{$t('allGoodsLabel.cancel')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
// api导入
import { getGoodsLabelPageList, updateGoodsLabel, deleteGoodsLabel } from '@/api/admin/goodsManage/goodsLabel/goodsLabel'
// 组件导入
import pagination from '@/components/admin/pagination/pagination'
export default {
  name: 'allGoodsLabel',
  components: { pagination },
  data () {
    return {
      labelName: null,
      goodsLabelData: [],
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      currentData: {},
      currentEditRow: {},
      dialogVisible: false
    }
  },
  methods: {
    addGoodsLabelClicked () {
      this.$router.push({ name: 'addGoodsLabel' })
    },
    useModelSettingClicked (row) {
      this.currentData = {
        ...row
      }
      this.currentEditRow = row
      this.dialogVisible = true
    },
    updateGoodsLabelUseModel () {
      updateGoodsLabel(this.currentData).then(res => {
        this.currentEditRow.goodsDetail = this.currentData.goodsDetail
        this.currentEditRow.goodsList = this.currentData.goodsList
        this.currentEditRow.goodsSelect = this.currentData.goodsSelect
        this.currentEditRow.listPattern = this.currentData.listPattern

        this.currentData = {}
        this.dialogVisible = false
      })
    },
    dialogCancel () {
      this.currentData = {}
      this.dialogVisible = false
    },
    updateLabelSettingClicked (row) {
      this.$router.push({
        name: 'updateGoodsLabel',
        params: {
          id: row.id
        }
      })
    },
    deleteLabelSettingClicked (row) {
      this.$confirm(this.$t('allGoodsLabel.confirmDelete'), this.$t('allGoodsLabel.confirmTip'), {
        confirmButtonText: this.$t('allGoodsLabel.confirm'),
        cancelButtonText: this.$t('allGoodsLabel.cancel'),
        type: 'warning'
      }).then(() => {
        return deleteGoodsLabel(row.id)
      }).then(res => {
        this.fetchGoodsLabelData()
      })
    },
    fetchGoodsLabelData () {
      let params = {
        labelName: this.labelName,
        ...this.pageParams
      }
      getGoodsLabelPageList(params).then(res => {
        this.goodsLabelData = res.content.dataList

        this.pageParams.totalRows = res.content.page.totalRows
        this.pageParams.currentPage = res.content.page.currentPage
        this.pageParams.pageRows = res.content.page.pageRows
      })
    }
  },
  mounted () {
    this.fetchGoodsLabelData()
    this.langDefault()
  }
}
</script>
<style scoped>
.contentWrap {
  padding: 10px 10px;
}
.content {
  background-color: white;
  padding: 10px 10px 20px 10px;
}
.contentHeader {
  padding: 10px;
  background: #fff;
  margin-bottom: 10px;
}
.contentBody {
  background: #fff;
  padding: 10px;
}
/deep/.tableClass th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
ul {
  min-width: 100px;
}
ul li {
  padding: 5px 0px;
}
.operateSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
}
</style>
