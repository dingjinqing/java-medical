<template>
  <div class="container goods-import-page">
    <div class="top">
      <div class="desc-wrap clearfix">
        <p class="desc">{{$t('goodsImport.importExplanation')}}</p>
        <el-button
          type="primary"
          size="small"
          @click="importGoodsDialogVisible = !importGoodsDialogVisible"
        >{{$t('goodsImport.productImport')}}</el-button>
      </div>
      <ul class="filter-ul">
        <li>
          <label for="">{{$t('goodsImport.batchNum')}}：</label>
          <el-input
            size="small"
            style="width:150px;"
            :placeholder="$t('goodsImport.operationTime')"
            v-model="queryParams.batchId"
          ></el-input>
        </li>
        <li>
          <label for="">{{$t('goodsImport.operationTime')}}：</label>
          <el-date-picker
            v-model="datepicker"
            type="daterange"
            size="small"
            :range-separator="$t('goodsImport.to')"
            :start-placeholder="$t('goodsImport.psTime')"
            :end-placeholder="$t('goodsImport.psTime')"
          ></el-date-picker>
        </li>
        <li>
          <el-button
            size="small"
            type="primary"
            @click="initDataList"
          >{{$t('goodsImport.filter')}}</el-button>
        </li>
      </ul>
    </div>
    <div class="center">
      <div class="table-list">
        <el-table
          :data="tableData"
          header-row-class-name="tableClss"
          border
        >
          <el-table-column
            :label="$t('goodsImport.batchNum')"
            align="center"
            prop="id"
            width="150px"
          ></el-table-column>
          <el-table-column
            :label="$t('goodsImport.operationTime')"
            align="center"
            prop="createTime"
          ></el-table-column>
          <el-table-column
            :label="$t('goodsImport.importType')"
            align="center"
            prop="isUpdate"
            :formatter="formatType"
            width="150px"
          ></el-table-column>
          <el-table-column
            :label="$t('goodsImport.numOfSuccess')+'(sku)'"
            align="center"
            prop="successNum"
          ></el-table-column>
          <el-table-column
            :label="$t('goodsImport.numOfFail')+'(sku)'"
            align="center"
          >
            <template slot-scope="{row}">
              <div>{{row.totalNum - row.successNum}}</div>
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('goodsImport.operating')"
            align="center"
          >
            <template slot-scope="{row}">
              <div>
                <div v-if="row.totalNum - row.successNum > 0">
                  <el-button
                    type="text"
                    @click="downFailData(row)"
                  >{{$t('goodsImport.downloadFail')}}</el-button>
                </div>
                <div><a
                    :href="row.importFilePath"
                    download
                  >{{$t('goodsImport.downloadSource')}}</a></div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      >
      </pagination>
    </div>
    <el-dialog
      title="商品导入"
      :visible.sync="importGoodsDialogVisible"
    >
      <div class="dialog-content">
        <p class="import-popup-title">{{$t('goodsImport.setp1')}}</p>
        <div class="card-box">
          <div>
            <span>{{$t('goodsImport.downloadTemp')}}：</span>
            <el-button
              type="text"
              @click="downloadExportModule"
            >{{$t('goodsImport.fileTemp')}}</el-button>
          </div>
        </div>
        <p class="import-popup-title">{{$t('goodsImport.setp2')}}</p>
        <div class="card-box">
          <el-radio-group v-model="uploadParams.isUpdate">
            <el-radio :label="false">{{$t('goodsImport.new')}}</el-radio>
            <el-radio :label="true">{{$t('goodsImport.update')}}</el-radio>
          </el-radio-group>
          <p>{{$t('goodsImport.updateDesc')}}</p>
        </div>
        <p class="import-popup-title">{{$t('goodsImport.setp3')}}</p>
        <div class="card-box">
          <span>{{$t('goodsImport.uploadFile')}}：</span>
          <div style="display:inline-block;">
            <el-input
              size="small"
              style="width:170px;"
              v-model="uploadFile.name"
              clearable
            ></el-input>
            <el-upload
              action=""
              style="display:inline-block;"
              ref="upload"
              :data="uploadParams"
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
          <p>{{$t('goodsImport.importRules')}}<span class="red">2000</span>条</p>
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
        >{{$t('goodsImport.import')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { goodsListApi, downloadFailExportApi, downloadModuleApi, uploadGoodsApi } from '@/api/admin/goodsManage/goodsImport/goodsImport.js'
import { download } from '@/util/excelUtil.js'
import '@/util/date.js'
export default {
  name: 'goodsImport',
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      datepicker: '',
      tableData: [],
      queryParams: {},
      pageParams: {
        currentPage: 1
      },
      importGoodsDialogVisible: false,
      uploadFile: {},
      fileList: [],
      uploadParams: {
        isUpdate: false
      },
      uploadUrl: ''
    }
  },
  watch: {
    datepicker: {
      handler (val) {
        console.log('datepicker:', val)
        let startDate = val[0].format('yyyy-MM-dd')
        let endDate = val[1].format('yyyy-MM-dd')
        this.$set(this.queryParams, 'beginTime', startDate)
        this.$set(this.queryParams, 'endTime', endDate)
      }
    },
    'uploadFile.name' (val) {
      if (!val) {
        this.$refs.upload.clearFiles()
        this.fileList = []
        this.uploadFile = {}
      }
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, this.pageParams, this.queryParams)
      goodsListApi(params).then((res) => {
        if (res.error === 0) {
          let content = res.content
          console.log(content)
          this.tableData = content.dataList
          this.pageParams = content.page
        }
      }).catch((err) => {
        console.error(err)
      })
    },
    formatType (row, column, cellValue, index) {
      if (cellValue === 0) {
        return this.$t('goodsImport.new')
      } else if (cellValue === 1) {
        return this.$t('goodsImport.update')
      }
    },
    // 下载失败数据
    downFailData (row) {
      let id = row.id
      downloadFailExportApi(id).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '下载失败数据.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch(err => {
        console.error(err)
      })
    },
    // 下载导入模板
    downloadExportModule () {
      downloadModuleApi().then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch(err => {
        console.error(err)
      })
    },
    uploadRequest () {
      if (this.fileList.length <= 0) {
        this.$message.warning(this.$t('goodsImport.psFile'))
        return false
      }
      this.$refs.upload.submit()
    },
    beforefileUpload (file) {
      console.log('beforeUpload...')
      let isXls = /\.(xls|xlsx|csv)$/.test(file.name)
      if (!isXls) {
        this.$message.warning(this.$t('goodsImport.support'))
        this.uploadFile = {}
        this.fileList = []
        return false
      }
      return true
    },
    fileChange (file, fileList) {
      console.log('fileChange: ', file, fileList)
      this.uploadFile = file
      this.fileList = [fileList[fileList.length - 1]]
    },
    uploadFileRequest () {
      let that = this
      that.loading = true
      let formdata = new FormData()
      formdata.append('file', this.fileList[0].raw)
      formdata.append('isUpdate', this.uploadParams.isUpdate)
      uploadGoodsApi(formdata).then(res => {
        console.log('res:', res)
        if (res.error === 0) {
          that.$message.success(that.$t('goodsImport.uploadSuccess'))
          that.importGoodsDialogVisible = false
        } else {
          that.$message.error(res.message)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.container {
  padding: 10px;
  font-size: 14px;
  height: 100%;
  position: relative;
  color: #333;
  .top {
    background-color: #fff;
    padding: 15px;
  }
  .center {
    background-color: #fff;
    margin-top: 10px;
    padding: 15px;
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
.clearfix {
  &::after {
    content: "";
    display: block;
    height: 0;
    clear: both;
  }
}
.goods-import-page {
  .top {
    .desc-wrap {
      padding: 10px;
      background: #f5f5f5;
      .desc {
        float: left;
        line-height: 32px;
        color: #666;
      }
      .el-button {
        float: right;
      }
    }
    .filter-ul {
      padding: 8px 0;
      li {
        display: inline-block;
        margin-right: 15px;
        label {
          display: inline-block;
          width: 100px;
          padding-right: 12px;
          text-align: right;
        }
      }
    }
  }
  .center {
    .table-list {
      a {
        color: #409eff;
        text-decoration: none;
      }
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
}
</style>
