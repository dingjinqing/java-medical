<template>
  <div>
    <el-dialog
      title="选择砍价活动"
      :visible.sync="dialogVisible"
      :append-to-body="true"
      width="825px"
    >
      <div class="dialog_content">
        <div class="bargain_header">
          <span>关键词</span>
          <el-input
            size="small"
            style="width: 180px;"
            v-model="queryParams.keywords"
          ></el-input>
          <el-button
            type="primary"
            size="small"
            @click="initData"
          >搜索</el-button>
        </div>
        <el-table
          ref="addBargainTable"
          :data="tableData"
          height="300px;"
          @selection-change="selectionChangeHandle"
          @row-click="currentClickHandle"
        >
          <el-table-column type="selection"></el-table-column>
          <el-table-column
            label="活动名称"
            prop="bargainName"
          ></el-table-column>
          <el-table-column label="商品信息">
            <template slot-scope="{row}">
              <div>
                <el-image
                  :src='$imageHost + "/" + row.goodsImg'
                  style="width:40px;height:40px;"
                ></el-image>
                <span>{{row.goodsName}}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="售价"
            prop="shopPrice"
          ></el-table-column>
          <el-table-column
            label="底价"
            prop="expectationPrice"
          ></el-table-column>
          <el-table-column
            label="活动库存"
            prop="stock"
          ></el-table-column>
          <el-table-column
            label="开始时间"
            prop="startTime"
          ></el-table-column>
          <el-table-column
            label="结束时间"
            prop="endTime"
          ></el-table-column>
        </el-table>
        <div>
          <pagination
            :page-params.sync="pageParams"
            @pagination="initData"
          ></pagination>
        </div>
      </div>
      <div slot="footer">
        <el-button @click="confirmSelect">确定</el-button>
        <el-button @click="cancelSelect">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getDialogBargainList } from '@/api/admin/marketManage/bargain'
export default {
  name: 'addBargainGoods',
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  props: {
    visible: {
      type: Boolean,
      default: () => false
    }
  },
  data () {
    return {
      tableData: [],
      queryParams: {
        keywords: ''
      },
      pageParams: {},
      selects: []
    }
  },
  computed: {
    dialogVisible: {
      get: function () {
        return this.visible
      },
      set: function (val) {
        this.$emit('update:visible', val)
      }
    }
  },
  mounted () {
    this.initData()
  },
  methods: {
    initData () {
      let that = this
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getDialogBargainList(params).then(res => {
        console.log('content:', res.content)
        if (res.error === 0) {
          that.pageParams = Object.assign({}, res.content.page)
          that.tableData = res.content.dataList
        }
      })
    },
    selectionChangeHandle (rows) {
      this.selects = rows
    },
    currentClickHandle (row) {
      this.$refs.addBargainTable.toggleRowSelection(row)
    },
    cancelSelect () {
      this.$refs.addBargainTable.clearSelection()
      this.dialogVisible = false
    },
    confirmSelect () {
      this.$emit('select', this.selects)
      this.dialogVisible = false
    }
  }
}
</script>
