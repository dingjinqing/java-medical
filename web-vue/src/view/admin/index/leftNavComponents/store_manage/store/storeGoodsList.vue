<template>
  <div class="receiveDetail">
    <div class="receiveDetailMain">
      <div class="order-container">
        <div class="order-info">
          <p class="list-store-name">{{$t('storeGoodsList.storeName')}}:<span>{{storeName}}</span></p>
          <ul class="list-store-filters">
            <li>
              <el-select
                v-model="queryParams.goodsSort"
                size="small"
              >
                <el-option
                  :label="$t('storeGoodsList.goodsSort')"
                  :value="null"
                ></el-option>
                <el-option
                  v-for="item in goodsSorts"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </li>
            <li>
              <el-select
                v-model="queryParams.shelfStatus"
                size="small"
              >
                <el-option
                  :label="$t('storeGoodsList.shelfStatus')"
                  :value="null"
                ></el-option>
                <el-option
                  v-for="item in goodsSorts"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </li>
            <li>
              <el-select
                v-model="queryParams.syncPos"
                size="small"
              >
                <el-option
                  :label="$t('storeGoodsList.syncPos')"
                  :value="null"
                ></el-option>
                <el-option
                  v-for="item in goodsSorts"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </li>
            <li>
              <el-input
                placeholder="搜索商品 | 条码"
                v-model="queryParams.search"
                size="small"
              >
                <i
                  slot="suffix"
                  class="el-input__icon el-icon-search"
                  @click="searchHandle"
                ></i>
              </el-input>
            </li>
            <div class="list-store-filters-right">
              <el-button
                type="primary"
                size="small"
                @click="updateGoodsHandle"
              >更新商品</el-button>
            </div>
          </ul>
        </div>
        <div class="order-content">
          <el-table
            ref="goodsTable"
            :data="goodsData"
            class="tableClass"
            max-height="500"
            border
            @selection-change="selectionChangeHandle"
            :header-cell-style="{
              'background-color':'#f5f5f5',
              'border':'none'
            }"
          >
            <el-table-column
              type="selection"
              width="60"
            >
            </el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.name')"
              prop="goodsName"
              width="300"
              align="left"
            >
              <template slot-scope="{row}">
                <div>
                  <img
                    style="width: 70px;height: 70px;float: left;"
                    :src="row.goodsImg"
                    alt="暂无图片"
                  >
                  <div style="padding:10px;">
                    <!-- <span
                      v-if="row.sourceName !== null"
                      class="goodsTypeSpanWrap"
                    >{{row.sourceName}}</span>
                    <span
                      v-if="row.goodsTypeName !== null"
                      class="goodsSourceSpanWrap"
                    >{{row.goodsTypeName}}</span> -->
                    {{row.goodsName}}
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.specification')"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.classification')"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.inStock')"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.merchantCoding')"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.barcode')"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.operate')"
              prop="goodsName"
              width=""
              align="center"
            >
              <template slot-scope="{row,$index}">
                <el-tooltip
                  v-if="row.shelfStatus === 1"
                  :content="$t('storeGoodsList.shelf')"
                  placement="top"
                >
                  <span
                    class="iconSpan"
                    @click="edit('shelf', row, $index)"
                  >{{$t('storeGoodsList.shelf')}}</span>
                </el-tooltip>
                <el-tooltip
                  v-if="row.shelfStatus === 0"
                  :content="$t('storeGoodsList.shelf')"
                  placement="top"
                >
                  <span
                    class="iconSpan"
                    @click="edit('obtain', row, $index)"
                  >{{$t('storeGoodsList.obtain')}}</span>
                </el-tooltip>
              </template>
            </el-table-column>
            <div slot="empty">
              <div class="noData">
                <img :src="noImg">
              </div>
              <span>暂无相关数据</span>
            </div>
          </el-table>
          <div class="table-page">
            <div>
              <el-button
                size="small"
                @click="shelfGoodsHandle"
              >{{$t('storeGoodsList.shelf')}}</el-button>
              <el-button
                size="small"
                @click="obtainGoodsHandle"
              >{{$t('storeGoodsList.obtain')}}</el-button>
            </div>
            <div>
              <pagination
                :page-params.sync="pageParams"
                @pagination="initDataList"
              ></pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      id: '',
      storeName: '',
      queryParams: {
        goodsSort: null,
        shelfStatus: null,
        syncPos: null,
        search: null
      },
      goodsSorts: [
        { id: 1, name: '图书，音响' },
        { id: 2, name: '家具' },
        { id: 3, name: '厨具' }
      ],
      selected: [],
      goodsData: [
        {
          goodsImg: 'http://mpdevimg2.weipubao.cn/upload/4748160/image/20190929/7ce184c55534ed40.jpg',
          goodsTypeName: '',
          goodsName: '砍价商品3',
          shelfStatus: 1
        }
      ],
      noImg: this.$imageHost + '/image/admin/no_data.png',
      pageParams: {}
    }
  },
  created () {
    this.id = this.$route.query.id
    this.storeName = this.$route.query.name
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    searchHandle () {
      console.log('search...' + this.queryParams)
    },
    updateGoodsHandle () {
      console.log('update...' + this.selected)
    },
    selectionChangeHandle () {
      console.log(arguments)
    },
    edit (param, row, index) {
      console.log(param, row, index)
    },
    shelfGoodsHandle () {

    },
    obtainGoodsHandle () {

    },
    initDataList () {

    }
  }
}
</script>

<style scoped>
.order-container {
  padding: 10px;
}
.order-info {
  background: #fff;
  padding: 15px 18px;
}
.order-content {
  margin-top: 10px;
}
.list-store-name {
  margin-left: 10px;
  margin-bottom: 4px;
  padding-top: 10px;
  font-size: 14px;
}
.list-store-filters {
  overflow: hidden;
  margin-top: 10px;
  margin-left: 10px;
  line-height: 1.428571429;
}
.list-store-filters li {
  float: left;
  padding-right: 18px;
}
.list-store-filters .el-input__inner {
  color: #555;
}
.list-store-filters-right {
  float: right;
}
.goodsTypeSpanWrap {
  border: 1px solid #ff3f3f;
  color: #ff3f3f;
  border-radius: 3px;
  padding: 2px;
  margin-right: 2px;
}
.goodsLabelSpanWrap {
  border: 1px solid #cccccc;
  color: #666;
  border-radius: 3px;
  padding: 2px;
  margin-right: 2px;
  display: inline-block;
}
.iconSpan {
  color: #5a8bff;
  cursor: pointer !important;
}
.noData {
  width: 30px;
  height: 33px;
  margin: 25px auto auto auto;
}
.noData span {
  margin: 10px;
}
.table-page {
  height: 100px;
  padding: 10px;
  background: #fff;
}
</style>
