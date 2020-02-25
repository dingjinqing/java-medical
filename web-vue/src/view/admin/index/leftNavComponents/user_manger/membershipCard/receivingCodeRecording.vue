<template>
  <div class="addBrandDialog" v-if="dialogVisible">
    <el-dialog
  :title="$t('memberCard.title')"
    :visible.sync="dialogVisible"
        center
  width="30%">
  <div>
  <span>{{$t('memberCard.batchId')}}：{{this.otherData.batchName}}</span>
  <span style="float: right;">{{$t('memberCard.total')}}：{{this.otherData.successNum}}/10000</span>
  </div>
  <div style="margin-top: 20px">
  <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        prop="num"
        :label="$t('memberCard.num')"
        width="70">
      </el-table-column>
      <el-table-column
        prop="createTime"
         :label="$t('memberCard.createTime')"
        width="180">
      </el-table-column>
      <el-table-column
        prop="successNum"
        :label="$t('memberCard.successNum')">
         <template slot-scope="scope">
           {{scope.row.successNum}}
           <el-button @click="downloadSuccess(scope.row.batchId)" type="text"  icon="el-icon-download"></el-button>
         </template>
      </el-table-column>
      <el-table-column
        prop="failNum"
        :label="$t('memberCard.failNum')">
         <template slot-scope="scope">
           {{scope.row.failNum}}
           <el-button @click="downloadFail(scope.row.batchId)" type="text"  icon="el-icon-download" style="color: red;"></el-button>
         </template>
      </el-table-column>
    </el-table>
  </div>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="dialogVisible = false" size="mini">{{$t('memberCard.sure')}}</el-button>
    </span>
  </el-dialog>
  </div>
</template>
<script>
import {getExcelList, getSuccessExcel, getFailExcel} from '@/api/admin/memberManage/memberCard.js'
import { download } from '@/util/excelUtil.js'
export default {
  props: {
    turnUp: {
      type: Boolean,
      default: () => false
    },
    batchId: {
      type: Number,
      default: () => null
    }
  },
  data () {
    return {
      dialogVisible: false,
      tableData: [],
      otherData: '',
      isPwd: false
    }
  },
  watch: {
    turnUp (data) {
      console.log('this.batchId22:' + this.batchId)
      if (this.batchId !== null) {
        this.dialogVisible = true
        this.showList()
      }
    }
  },
  mounted () {
    // 初始化数据
    this.defalutData()
    this.langDefault()
  },
  methods: {
    defalutData () {
    },
    showList () {
      let param = this.batchId
      getExcelList(param).then(res => {
        console.log(res)
        if (res.error === 0) {
          res.content.num = 1
          this.tableData = []
          this.tableData.push(res.content)
          this.otherData = res.content
          this.isPwd = res.content.isPwd
        }
      }).catch((err, data) => {
        console.error('err:', err)
      })
    },
    handleClose () {

    },
    downloadSuccess (data) {
      let param = {
        'isPwd': this.isPwd,
        'batchId': data
      }
      getSuccessExcel(param).then(res => {
        this.loading = false
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch((err, data) => {
        console.error('err:', err)
        this.loading = false
      })
    },
    downloadFail (data) {
      let param = {
        'isPwd': this.isPwd,
        'batchId': data
      }
      getFailExcel(param).then(res => {
        this.loading = false
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch((err, data) => {
        console.error('err:', err)
        this.loading = false
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
