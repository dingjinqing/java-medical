<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('bargain.bargainingModule')}}</h2>
      <!-- 模块私有区域 -->
      <div class="main">
        <el-form
          ref="bargainForm"
          label-width="85px"
          size="small"
        >
          <el-form-item :label="$t('bargain.listStyle') + '：'">
            <el-radio
              v-model="data.list_style"
              :label="0"
            >{{$t('bargain.doubleCol')}}</el-radio>
            <el-radio
              v-model="data.list_style"
              :label="1"
            >{{$t('bargain.singleCol')}}</el-radio>
          </el-form-item>
          <el-form-item :label="$t('bargain.chooseEvent') + '：'">
            <el-button
              @click="selectBargainHandle"
              size="small"
            >+ {{$t('bargain.addEvent')}}</el-button>
            <el-tooltip effect="light">
              <img
                :src="$imageHost+ '/image/admin/analysis_tishi.png'"
                style="position:relative;top:3px;"
              >
              <div
                slot="content"
                style="width: 250px;
                  line-height: 30px;
                  font-size: 14px;
                  word-wrap: break-word;
                  word-break: break-all;
                  background: #fff;"
              >
                {{$t('bargain.addEventTip')}}
              </div>
            </el-tooltip>
          </el-form-item>
          <div
            v-show="tableData && tableData.length > 0"
            style="margin-bottom:15px;"
          >
            <el-table
              :data="bargainTableData"
              border
              size="mini"
            >
              <el-table-column
                :label="$t('bargain.productName')"
                prop="goods_name"
                width="80px"
              >
                <template slot-scope="{row}">
                  <div>
                    <el-image
                      :src="row.goods_img"
                      style="width:30px; height:30px;border: 1px solid #eee;"
                    ></el-image>
                    <span>{{row.goods_name}}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('bargain.startTime')"
                width="90px"
                prop="act_begin_time"
              ></el-table-column>
              <el-table-column
                :label="$t('bargain.bottomPrice')"
                width="50px"
                prop="expectation_price"
              ></el-table-column>
              <el-table-column
                :label="$t('bargain.bargainingStock')"
                width="70px"
                prop="bargain_num"
              ></el-table-column>
              <el-table-column
                :label="$t('bargain.productStatus')"
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
                :label="$t('bargain.operate')"
                width="70px"
              >
                <template slot-scope="{row, $index}">
                  <div class="edits_wrap">
                    <span
                      @click="edit('moveUp', row, $index)"
                      class="iconfont iconshangyi"
                      :title="$t('bargain.moveUp')"
                    ></span>
                    <span
                      @click="edit('moveDown', row, $index)"
                      class="iconfont iconxiayi"
                      :title="$t('bargain.moveDown')"
                    ></span>
                    <span
                      @click="edit('delete', row, $index)"
                      class="iconfont iconshanchu2"
                      :title="$t('bargain.delete')"
                    ></span>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-form-item :label="$t('bargain.productStatus') + '：'">
            <el-checkbox v-model="data.goods_price">{{$t('bargain.originalPrice')}}</el-checkbox>
            <el-checkbox v-model="data.goods_count_down">{{$t('bargain.activityCountdown')}}</el-checkbox>
            <el-checkbox v-model="data.free_btn">{{$t('bargain.bargainBtn')}}</el-checkbox>
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
import vm from '@/main'
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
        list_style: 0,
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
        return vm.$t('bargain.normal')
      } else if (val === 0) {
        return vm.$t('bargain.deactivate')
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
        this.tableData = this.modulesData.bargain_goods.map(function (row) {
          return {
            goodsId: row.goods_id,
            id: row.act_id,
            goodsImg: row.goods_img,
            goodsName: row.goods_name,
            shopPrice: row.goods_price,
            expectationPrice: row.expectation_price,
            stock: row.bargain_num,
            startTime: row.act_begin_time,
            status: row.act_status,
            isOnSale: row.is_on_sale,
            isDelete: row.is_delete,
            endTime: row.act_end_time
          }
        })
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
  mounted () {
    this.langDefault()
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
