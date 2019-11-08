<template>
  <div>
    <el-dialog
      title="选择秒杀活动"
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
          ref="addSpikeTable"
          :data="tableData"
          height="300px;"
          @selection-change="selectionChangeHandle"
          @row-click="currentClickHandle"
        >
          <el-table-column type="selection"></el-table-column>
          <el-table-column
            label="活动名称"
            prop="name"
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
        <el-button
          size="small"
          type="primary"
          @click="confirmSelect"
        >确定</el-button>
        <el-button
          size="small"
          @click="cancelSelect"
        >取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { seckillList } from '@/api/admin/marketManage/seckill.js'
export default {
  name: 'addSpikeGoods',
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
      let params = Object.assign({
        state: [1]
      }, this.queryParams, this.pageParams)
      seckillList(params).then(res => {
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
      this.$refs.addSpikeTable.toggleRowSelection(row)
    },
    cancelSelect () {
      this.$refs.addSpikeTable.clearSelection()
      this.dialogVisible = false
    },
    confirmSelect () {
      this.$emit('select', this.selects)
      this.dialogVisible = false
    }
  }
}
</script>
