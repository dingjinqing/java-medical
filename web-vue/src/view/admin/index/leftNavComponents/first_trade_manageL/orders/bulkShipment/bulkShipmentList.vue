<template>
  <div class="container bulk-shipment-page">
    <div class="top">
      <div class="explanation">
        <div class="explanation-con">
          <span>{{$t('bulkShipment.desc')}}：</span>
          <ul>
            <ol>1、{{$t('bulkShipment.shipDesc1')}}</ol>
            <ol>2、{{$t('bulkShipment.shipDesc2')}}</ol>
          </ul>
        </div>
        <el-button
          class="bulk-shipment-btn"
          type="primary"
          size="small"
          @click="bulkShipmentClickHandle"
        >{{$t('bulkShipment.bulkShipping')}}</el-button>
      </div>
      <el-form
        :inline="true"
        class="form-filters"
        size="small"
      >
        <el-form-item :label="$t('bulkShipment.batchNum')+'：'">
          <el-input
            v-model="queryParams.batchId"
            class="filter-input"
            :placeholder="$t('bulkShipment.piBatchNum')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('bulkShipment.operateTime')+'：'">
          <el-date-picker
            type="datetime"
            v-model="queryParams.start"
            :placeholder="$t('goodsImport.psTime')"
            size="small"
            style="width:170px;"
            default-time="00:00:00"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd HH:mm:ss"
            :picker-options="startTimePicker"
          ></el-date-picker>
          <span style="display:inline-block; margin:0 10px;line-height:30px;">{{$t('goodsImport.to')}}</span>
          <el-date-picker
            type="datetime"
            v-model="queryParams.end"
            :placeholder="$t('goodsImport.psTime')"
            size="small"
            style="width:170px;"
            default-time="23:59:59"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd HH:mm:ss"
            :picker-options="endTimePicker"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            size="small"
            @click="initDataList"
          >{{$t('bulkShipment.filter')}}</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="content">
      <el-table
        :data="tableData"
        header-row-class-name="tableClss"
        border
      >
        <el-table-column
          :label="$t('bulkShipment.batchNum')"
          align="center"
          prop="id"
        ></el-table-column>
        <el-table-column
          :label="$t('bulkShipment.operateTime')"
          align="center"
          prop="createTime"
        ></el-table-column>
        <el-table-column
          :label="$t('bulkShipment.operater')"
          align="center"
        >
          <template slot-scope="{row}">
            <div>
              <div v-if="row.sysId > 0">
                {{row.userName+"："+row.mobile}}
              </div>
              <div v-else-if="row.accountId">
                {{row.childUserName+'：'+row.childMobile}}
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('bulkShipment.successNum')"
          align="center"
          prop="successNum"
        ></el-table-column>
        <el-table-column
          :label="$t('bulkShipment.failedNum')"
          align="center"
        >
          <template slot-scope="{row}">
            <div>
              {{row.totalNum - row.successNum}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('bulkShipment.operate')"
          align="center"
        >
          <template slot-scope="{row}">
            <div>
              <el-tooltip
                v-if="row.totalNum - row.successNum > 0"
                effect="light"
                placement="top"
                :content="$t('bulkShipment.downfaildata')"
              >
                <el-button
                  type="text"
                  @click="downloadFailData(row)"
                >{{$t('bulkShipment.downfaildata')}}</el-button>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      >
      </pagination>
    </div>
    <!-- 批量发货 -->
    <el-dialog
      :title="$t('bulkShipment.bulkShipping')"
      :visible.sync="bulkShipmentDialogVisible"
    >
      <div class="dialog-content">
        <p class="import-popup-title">{{$t('goodsImport.setp1')}}</p>
        <div class="card-box">
          <div>
            <span>{{$t('goodsImport.downloadTemp')}}：</span>
            <!-- <el-button
              type="text"
              @click="downloadExportModule"
            >{{$t('goodsImport.fileTemp')}}</el-button> -->
            <el-link
              v-if="lang == 'zh_CN'"
              :href="$imageHost+'/temp/excel/zh_CN/order/batchShip.xlsx'"
              type="primary"
              download
            >{{$t('goodsImport.fileTemp')}}</el-link>
            <el-link
              v-else
              :href="$imageHost+'/temp/excel/en_US/order/batchShip.xlsx'"
              type="primary"
              download
            >{{$t('goodsImport.fileTemp')}}</el-link>
            <span>{{lang}}</span>
          </div>
        </div>
        <p class="import-popup-title">{{$t('goodsImport.setp2')}}</p>
        <div class="card-box">
          <span>{{$t('bulkShipment.uploadFile')}}：</span>
          <div style="display:inline-block;">
            <el-input
              size="small"
              style="width:170px;"
              v-model="uploadData.name"
              clearable
            ></el-input>
            <el-upload
              action=""
              style="display:inline-block;"
              ref="upload"
              :data="uploadData"
              :file-list="fileList"
              :auto-upload="false"
              :show-file-list="false"
              :before-upload="beforefileUpload"
              :on-change="fileChange"
              :http-request="uploadFileRequest"
              accept=".xls,.xlsx"
            >
              <el-button
                slot="trigger"
                size="small"
              >{{$t('goodsImport.browse')}}...</el-button>
            </el-upload>
          </div>
          <p>{{$t('bulkShipment.importRules')}}：1、{{$t('bulkShipment.importR1')}}</p>
          <p style="margin-left: 70px;">2、{{$t('bulkShipment.importR2')}}</p>
        </div>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="uploadRequest"
        >{{$t('bulkShipment.confirm')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getBatchListApi, downloadFailOrder, uplaodBulkShipmentFileApi } from '@/api/admin/orderManage/bulkShipment.js'
import { download } from '@/util/excelUtil.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      queryParams: {},
      pageParams: {},
      startTimePicker: {
        disabledDate: time => {
          return time.getTime() > new Date(this.queryParams.endTime).getTime()
        }
      },
      endTimePicker: {
        disabledDate: time => {
          return time.getTime() < new Date(this.queryParams.beginTime).getTime()
        }
      },
      tableData: [],
      bulkShipmentDialogVisible: false,
      uploadData: {
        isUpdate: false
      },
      fileList: []
    }
  },
  watch: {
    'uploadData.name' (val) {
      if (!val) {
        this.$refs.upload.clearFiles()
        this.fileList = []
        this.uploadData = {}
      }
    },
    lang (newData) {
      console.log('触发', newData)
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, this.queryParams)
      getBatchListApi(params).then(res => {
        if (res.error === 0) {
          console.log(res.content)
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },
    bulkShipmentClickHandle () {
      this.bulkShipmentDialogVisible = true
    },
    // 下载失败数据
    downloadFailData (row) {
      let id = row.id
      downloadFailOrder({ id: id }).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '批量发货失败列表.xlsx'
        download(res, decodeURIComponent(fileName))
      })
    },
    beforefileUpload (file) {
      console.log('beforeUpload...')
      let isXls = /\.(xls|xlsx|csv)$/.test(file.name)
      if (!isXls) {
        this.$message.warning(this.$t('goodsImport.support'))
        this.uploadData = {}
        this.fileList = []
        return false
      }
      return true
    },
    fileChange (file, fileList) {
      console.log('fileChange: ', file, fileList)
      this.uploadData = file
      this.fileList = [fileList[fileList.length - 1]]
    },
    uploadFileRequest () {
      let that = this
      that.loading = true
      let formdata = new FormData()
      formdata.append('file', this.fileList[0].raw)
      uplaodBulkShipmentFileApi(formdata).then(res => {
        console.log('res:', res)
        if (res.error === 0) {
          that.$message.success(that.$t('goodsImport.uploadSuccess'))
          that.bulkShipmentDialogVisible = false
        } else {
          that.$message.error(res.message)
        }
      })
    },
    // 点击上传按钮
    uploadRequest () {
      if (this.fileList.length <= 0) {
        this.$message.warning(this.$t('goodsImport.psFile'))
        return false
      }
      this.$refs.upload.submit()
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  font-size: 14px;
  color: #333;
  .top {
    padding: 15px;
    background-color: #fff;
  }
  .content {
    margin-top: 10px;
    padding: 15px;
    background-color: #fff;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
    }
    .iconSpan {
      font-size: 22px;
      color: #5a8bff;
      cursor: pointer;
    }
  }
}
.bulk-shipment-page {
  .explanation {
    background: #f5f5f5;
    padding: 10px;
    overflow: hidden;
  }
  .explanation-con {
    float: left;
    display: flex;
    width: 80%;
    line-height: 27px;
    color: #666;
    span {
      flex-basis: 43px;
    }
    & > div {
      flex: 1;
    }
  }
  .bulk-shipment-btn {
    float: right;
  }
  .form-filters {
    margin-top: 10px;
  }
}
.dialog-content {
  .import-popup-title {
    margin-bottom: 14px;
  }
  .card-box {
    background-color: #f5f5f5;
    border-radius: 6px;
    margin-bottom: 14px;
    padding: 10px 16px 16px;
    p {
      margin-top: 10px;
    }
  }
}
.dialog-footer {
  text-align: center;
}
</style>
