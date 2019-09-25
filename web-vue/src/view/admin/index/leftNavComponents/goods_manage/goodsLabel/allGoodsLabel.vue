<template>
  <div class="contentWrap">
    <div class="content">
      <div class="contentHeader">
        <span style="margin-right: 10px;">标签名称:</span>
        <el-input v-model="labelName" size="small" style="width:180px;" placeholder="请输入标签名称"/>
        <el-button type="primary" size="small" @click="fetchGoodsLabelData" style="cursor:pointer;">查询</el-button>
        <el-button type="primary" size="small" style="float: right;" @click="addGoodsLabelClicked">添加商品标签</el-button>
      </div>
      <div class="contentBody">
        <el-table
          :data="goodsLabelData"
          class="tableClass"
          border
          style="width: 100%">
          <el-table-column
            align="center"
            prop="name"
            label="标签名称"/>
          <el-table-column
            align="center"
            prop="updateTime"
            label="更新时间"/>
          <el-table-column
            align="center"
            label="前端应用模块">
            <template slot-scope="{row}">
              <div style="display: flex;justify-content: space-around;align-items: center;">
                <ul>
                  <li v-if="row.goodsDetail === 1">商品详情页</li>
                  <li v-if="row.goodsList === 1">商品列表页</li>
                  <li v-if="row.goodsSelect === 1">商品筛选页</li>
                </ul>
                <span class="operateSpan" @click="useModelSettingClicked(row)">设置</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="level"
            label="优先级"/>
          <el-table-column
            align="center"
            label="商品数量">
            php查询了es,目前未实现
          </el-table-column>
          <el-table-column
            align="center"
            label="操作">
            <template slot-scope="{row}">
              <span class="operateSpan" @click="updateLabelSettingClicked(row)">编辑</span>
              <span class="operateSpan" @click="deleteLabelSettingClicked(row)">删除</span>
            </template>
          </el-table-column>
        </el-table>
        <pagination :page-params.sync="pageParams" @pagination="fetchGoodsLabelData"/>
      </div>
    </div>
    <el-dialog title="选择应用位置" :visible.sync="dialogVisible"  width="40%">
      <div style="font-size: 12px;border: 1px solid #FFD5A3;margin: 0 auto;padding: 5px;background: #FFF7EB;">可以在这里选择商品标签应用位置</div>
      <ul>
        <li><el-checkbox v-model="currentData.goodsDetail" :true-label="1" :false-label="0">商品详情页</el-checkbox></li>
        <li><el-checkbox v-model="currentData.goodsList" :true-label="1" :false-label="0">商品列表页</el-checkbox></li>
        <li><el-checkbox v-model="currentData.goodsSelect" :true-label="1" :false-label="0">商品筛选页</el-checkbox></li>
      </ul>
      <div v-if="currentData.goodsList === 1" style="margin-top: 5px;background: #f8f8f8;padding: 10px 15px 20px 10px;border: 1px solid #eee;border-radius: 5px;">
        <span><em style="color: red;">*</em>标签样式:</span>
        <div style="margin-top: 5px;display: flex;justify-content: space-around;">
          <div style="flex-grow: 1;text-align: center;">
            <div>图片1</div>
            <el-radio v-model="currentData.listPattern" :label="1">{{""}}</el-radio>
          </div>
          <div style="flex-grow: 1;text-align: center;">
            <div>图片2</div>
            <el-radio v-model="currentData.listPattern" :label="2">{{""}}</el-radio>
          </div>
          <div style="flex-grow: 1;text-align: center;">
            <div>图片3</div>
            <el-radio v-model="currentData.listPattern" :label="3">{{""}}</el-radio>
          </div>
          <div style="flex-grow: 1;text-align: center;">
            <div>图片4</div>
            <el-radio v-model="currentData.listPattern" :label="4">{{""}}</el-radio>
          </div>
        </div>
      </div>
      <div slot="footer">
        <el-button @click="updateGoodsLabelUseModel" type="primary">确定</el-button>
        <el-button @click="dialogCancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
// api导入
import {getGoodsLabelList, updateGoodsLabel, deleteGoodsLabel} from '@/api/admin/goodsManage/goodsLabel/goodsLabel'
// 组件导入
import pagination from '@/components/admin/pagination/pagination'
export default {
  name: 'allGoodsLabel',
  components: {pagination},
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
      this.$router.push({name: 'addGoodsLabel'})
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
      this.$confirm('确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
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
      getGoodsLabelList(params).then(res => {
        this.goodsLabelData = res.content.dataList

        this.pageParams.totalRows = res.content.page.totalRows
        this.pageParams.currentPage = res.content.page.currentPage
        this.pageParams.pageRows = res.content.page.pageRows
      })
    }
  },
  mounted () {
    this.fetchGoodsLabelData()
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
  .contentHeader{
    margin: 10px 0px;
  }
  /deep/.tableClass th{
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  ul{
    min-width: 100px;
  }
  ul li{
    padding: 5px 0px;
  }
  .operateSpan{
    font-size: 16px;
    color: #5a8bff;
    cursor: pointer !important;
  }
</style>
