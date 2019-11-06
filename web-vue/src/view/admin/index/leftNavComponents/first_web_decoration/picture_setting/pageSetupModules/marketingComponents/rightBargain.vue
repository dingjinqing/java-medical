<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>砍价模块</h2>
      <!-- 模块私有区域 -->
      <div class="main">
        <el-form
          ref="bargainForm"
          label-width="85px"
          size="small"
        >
          <el-form-item label="列表样式：">
            <el-radio
              v-model="data.list_style"
              label="0"
            >双列</el-radio>
            <el-radio
              v-model="data.list_style"
              label="1"
            >单列</el-radio>
          </el-form-item>
          <el-form-item label="选择活动：">
            <el-button
              @click="selectBargainHandle"
              size="small"
            >+ 添加活动</el-button>
            <el-tooltip effect="light">
              <img :src="$imageHost+ '/image/admin/analysis_tishi.png'">
              <div slot="content">
                仅可以选择进行中以及未开始的砍<br />
                价活动，将以活动商品形式展示在<br />
                小程序前端，每个“砍价活动“组件<br />
                最多可添加6个砍价活动。对砍价<br />
                活动中商品进行更换时，当前组件<br />
                商品将同步更新。
              </div>
            </el-tooltip>
          </el-form-item>
          <div style="margin-bottom:15px;">
            <el-table
              :data="bargainTableData"
              border
              size="mini"
            >
              <el-table-column
                label="商品名称"
                prop="goods_name"
                width="80px"
              >
                <template slot-scope="{row}">
                  <div>
                    <el-image
                      :src="$imageHost +'/'+ row.goods_img"
                      style="width:30px; height:30px;border: 1px solid #eee;"
                    ></el-image>
                    <span>{{row.goods_name}}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                label="开始时间"
                width="90px"
                prop="act_begin_time"
              ></el-table-column>
              <el-table-column
                label="底价"
                width="50px"
                prop="expectation_price"
              ></el-table-column>
              <el-table-column
                label="砍价库存"
                width="70px"
                prop="bargain_num"
              ></el-table-column>
              <el-table-column
                label="商品状态"
                width="70px"
                prop="act_status"
              >
                <template slot-scope="{row}">
                  <div>
                    {{row.act_status | fmtStatus}}
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                label="操作"
                width="70px"
              >
                <template slot-scope="{row, $index}">
                  <div class="edits_wrap">
                    <span
                      @click="edit('moveUp', row, $index)"
                      class="iconfont iconshangyi"
                      title="上移"
                    ></span>
                    <span
                      @click="edit('moveDown', row, $index)"
                      class="iconfont iconxiayi"
                      title="下移"
                    ></span>
                    <span
                      @click="edit('delete', row, $index)"
                      class="iconfont iconshanchu2"
                      title="删除"
                    ></span>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-form-item label="显示内容：">
            <el-checkbox v-model="data.goods_price">商品原价</el-checkbox>
            <el-checkbox v-model="data.goods_count_down">活动倒计时</el-checkbox>
            <el-checkbox v-model="data.free_btn">去砍价按钮</el-checkbox>
          </el-form-item>
        </el-form>
      </div>
      <!-- 模块私有区域end -->
      <!-- 弹窗选择砍价活动商品 -->
      <addBargainGoodsDialog
        :visible.sync="addBargainDialogVisible"
        @select="selectBargainGoodsHandle"
      ></addBargainGoodsDialog>
    </div>
  </div>
</template>

<script>
export default {
  components: {
    addBargainGoodsDialog: () => import('@/components/admin/picture_setting/addBargainGoodsDialog')
  },
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      data: {
        module_name: 'm_bargain',
        list_style: '0',
        goods_price: true,
        goods_count_down: true,
        free_btn: true,
        bargain_goods: []
      },
      addBargainDialogVisible: false,
      tableData: []
    }
  },
  computed: {
    bargainTableData: {
      get: function () {
        let datas = this.tableData.map(function (row, i) {
          return {
            goods_id: row.goodsId,
            act_id: row.id,
            goods_img: row.goodsImg,
            goods_name: row.goodsName,
            goods_price: row.shopPrice,
            expectation_price: row.expectationPrice,
            bargain_num: row.stock,
            act_begin_time: row.startTime,
            act_status: row.status,
            is_on_sale: row.isOnSale,
            is_delete: row.isDelete,
            act_end_time: row.endTime
          }
        })
        this.$set(this.data, 'bargain_goods', datas)
        return datas
      },
      set: function (val) {
        console.log('set', val)
        this.$set(this.data, 'bargain_goods', val)
      }
    }
  },
  filters: {
    fmtStatus: function (val) {
      if (val === 1) {
        return '正常'
      } else if (val === 0) {
        return '停用'
      }
      return val
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log('newData:', newData, this.modulesData)
        this.data = this.modulesData
      },
      immediate: true
    },
    // 监听数据变换
    data: { // 模块公共
      handler (newData) {
        console.log('update:', newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  methods: {
    selectBargainHandle () {
      this.addBargainDialogVisible = true
    },
    selectBargainGoodsHandle (goods) {
      console.log(goods)
      this.tableData = goods
    },
    edit (operate, row, $index) {
      let datas = this.bargainTableData
      let len = datas.length
      let index = $index
      console.log('editxxx', index, len, datas)
      switch (operate) {
        case 'moveUp':// 下移
          if (index <= 0) return false
          datas.splice(index, 1)
          datas.splice(index - 1, 0, row)
          break
        case 'moveDown':
          if (index >= len) return false
          datas.splice(index, 1)
          datas.splice(index + 1, 0, row)
          break
        case 'delete':
          datas.splice(index, 1)
          break
      }
      this.bargainTableData = datas
    }
  }
}
</script>

<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.rightCommodity {
  .rightCommodityMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
  }
}
.edits_wrap .iconfont {
  font-size: 13px;
  color: #5a8bff;
  cursor: pointer;
}
</style>
