<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('spike.secondKillModule')}}</h2>
      <div class="main">
        <el-form>
          <el-form-item :label="$t('bargain.listStyle') + '：'">
            <el-radio
              v-model="data.list_style"
              label="0"
            >{{$t('bargain.doubleCol')}}</el-radio>
            <el-radio
              v-model="data.list_style"
              label="1"
            >{{$t('bargain.singleCol')}}</el-radio>
          </el-form-item>
          <el-form-item :label="$t('bargain.chooseEvent') + '：'">
            <el-button
              @click="selectSpikeHandle"
              size="small"
            >+ {{$t('bargain.addEvent')}}</el-button>
            <el-tooltip effect="light">
              <img :src="$imageHost+ '/image/admin/analysis_tishi.png'">
              <div slot="content">
                {{$t('bargain.addEventTip')}}
              </div>
            </el-tooltip>
          </el-form-item>
          <div
            v-show="spikeTableData && spikeTableData.length > 0"
            style="margin-bottom:15px;"
          >
            <el-table
              :data="spikeTableData"
              border
              size="mini"
            >
              <el-table-column
                :label="$t('bargain.productName')"
                prop="goods_name"
                width="120px"
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
                :label="$t('spike.secondaryInventory')"
                width="70px"
                prop="seckill_num"
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
          <el-form-item :label="$t('spike.displayContent') +'：'">
            <el-checkbox v-model="data.goods_count_down">{{$t('bargain.activityCountdown')}}</el-checkbox>
            <el-checkbox v-model="data.goods_price">{{$t('bargain.originalPrice')}}</el-checkbox>
          </el-form-item>
        </el-form>
      </div>
      <!-- 弹窗选择秒杀活动商品 -->
      <addSpikeGoodsDialog
        :visible.sync="spikeDialogVisible"
        @select="selectSpikeGoodsHandle"
      ></addSpikeGoodsDialog>
    </div>
  </div>
</template>

<script>
import vm from '@/main'
export default {
  components: {
    addSpikeGoodsDialog: () => import('@/components/admin/picture_setting/addSeckillGoodsDialog')
  },
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      data: {
        module_name: 'm_seckill',
        list_style: '0',
        goods_price: true,
        goods_count_down: true,
        seckill_goods: []
      },
      tableData: [],
      spikeDialogVisible: false
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log('newData:', newData, this.modulesData)
        this.data = this.modulesData
        if (this.modulesData.seckill_goods) {
          this.tableData = this.modulesData.seckill_goods.map(function (row) {
            return {
              goodsId: row.goods_id,
              skId: row.act_id,
              secPrice: row.sec_price,
              goodsImg: row.goods_img,
              goodsName: row.goods_name,
              shopPrice: row.goods_price,
              stock: row.seckill_num,
              startTime: row.act_begin_time,
              status: row.act_status,
              isOnSale: row.is_on_sale,
              isDelete: row.is_delete,
              endTime: row.act_end_time
            }
          })
        }
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
  computed: {
    spikeTableData: {
      get: function () {
        let datas = this.tableData.map(function (row, i) {
          return {
            goods_id: row.goodsId,
            act_id: row.skId,
            sec_price: row.secPrice,
            sale_num: row.saleNum,
            goods_img: row.goodsImg,
            goods_name: row.goodsName,
            goods_price: row.shopPrice,
            seckill_num: row.stock,
            act_begin_time: row.startTime,
            act_status: row.status,
            is_on_sale: row.isOnSale,
            is_delete: row.isDelete,
            act_end_time: row.endTime
          }
        })
        this.$set(this.data, 'seckill_goods', datas)
        return datas
      },
      set: function (val) {
        this.$set(this.data, 'seckill_goods', val)
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
  mounted () {
    this.langDefault()
  },
  methods: {
    selectSpikeHandle () {
      this.spikeDialogVisible = true
    },
    selectSpikeGoodsHandle (goods) {
      console.log('goods:', goods)
      this.tableData = goods
    },
    edit (operate, row, $index) {
      let datas = this.spikeTableData
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
      this.spikeTableData = datas
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
    .edits_wrap .iconfont {
      font-size: 13px;
      color: #5a8bff;
      cursor: pointer;
    }
  }
}
</style>
