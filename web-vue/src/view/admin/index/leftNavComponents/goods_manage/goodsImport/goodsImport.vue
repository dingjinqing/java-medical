<template>
  <div class="container goods-import-page">
    <div class="top">
      <div class="desc-wrap clearfix">
        <p class="desc">说明：商品导入一般需要几到十几分钟，完成时会提示和自动刷新页面，并新增导入操作记录，导入完成之前请勿关闭页面或在此页面进行其他操作，以免造成数据错误</p>
        <el-button
          type="primary"
          size="small"
          @click="importGoodsDialogVisible = !importGoodsDialogVisible"
        >商品导入</el-button>
      </div>
      <ul class="filter-ul">
        <li>
          <label for="">批次号：</label>
          <el-input
            size="small"
            style="width:150px;"
            placeholder="请输入批次号"
            v-model="queryParams.batchId"
          ></el-input>
        </li>
        <li>
          <label for="">操作时间：</label>
          <el-date-picker
            v-model="datepicker"
            type="daterange"
            size="small"
            range-separator="至"
            start-placeholder="请选择时间"
            end-placeholder="请选择时间"
          ></el-date-picker>
        </li>
        <li>
          <el-button
            size="small"
            type="primary"
            @click="initDataList"
          >筛选</el-button>
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
            label="批次号"
            align="center"
            prop="id"
            width="150px"
          ></el-table-column>
          <el-table-column
            label="操作时间"
            align="center"
            prop="createTime"
          ></el-table-column>
          <el-table-column
            label="导入类型"
            align="center"
            prop="isUpdate"
            :formatter="formatType"
            width="150px"
          ></el-table-column>
          <el-table-column
            label="成功数量(sku)"
            align="center"
            prop="successNum"
          ></el-table-column>
          <el-table-column
            label="失败数量(sku)"
            align="center"
          >
            <template slot-scope="{row}">
              <div>{{row.totalNum - row.successNum}}</div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="{row}">
              <div>
                <div v-if="row.totalNum - row.successNum > 0">
                  <el-button
                    type="text"
                    @click="downFailData(row)"
                  >下载失败数据</el-button>
                </div>
                <div><a
                    :href="row.importFilePath"
                    download
                  >下载源文件</a></div>
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
        <p class="import-popup-title">第一步：模板下载</p>
        <div class="card-box">
          <div>
            <span>下载模板：</span>
            <el-button
              type="text"
              @click="downloadExportModule"
            >商品导入文件模板</el-button>
          </div>
        </div>
        <p class="import-popup-title">第二部：选择类型</p>
        <div class="card-box">
          <el-radio-group v-model="uploadParams.isUpdate">
            <el-radio :label="false">新增</el-radio>
            <el-radio :label="true">更新</el-radio>
          </el-radio-group>
          <p>说明：默认为新增，若选择新增对商家编码重复的商品不做更新操作；若选择更新即可更新商品也可新增商品</p>
        </div>
        <p class="import-popup-title">第三部：数据导入</p>
        <div class="card-box">
          <span>上传文件：</span>
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
              >浏览...</el-button>
            </el-upload>
          </div>
          <p>导入规则：文件当前仅支持excel格式；每次导入商品条数请不要超过<span class="red">2000</span>条</p>
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
        >导入</el-button>
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
        return '新增'
      } else if (cellValue === 1) {
        return '更新'
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
        this.$message.warning('请选择要上传的文件')
        return false
      }
      this.$refs.upload.submit()
    },
    beforefileUpload (file) {
      console.log('beforeUpload...')
      let isXls = /\.(xls|xlsx|csv)$/.test(file.name)
      if (!isXls) {
        this.$message.warning('上传文件只支持xls、xlsx格式！')
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
      formdata.append('isUpload', this.uploadParams.isUpdate)
      uploadGoodsApi(formdata).then(res => {
        console.log('res:', res)
        if (res.error === 0) {
          that.$message.success('上传成功')
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
