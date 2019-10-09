<template>
  <div class="receiveDetail">
    <div class="receiveDetailMain">
      <div class="order-container">
        <div class="order-info">
          <ul class="list-verifier-filters">
            <li>
              <label class="list-verifier-filters-label">{{$t('verifierManage.phone')}}：</label>
              <el-input
                size="small"
                class="input-narrow"
                :placeholder="$t('verifierManage.phoneTips')"
              ></el-input>
            </li>
            <li>
              <label class="list-verifier-filters-label">{{$t('verifierManage.wechatNickname')}}：</label>
              <el-input
                size="small"
                class="input-narrow"
                :placeholder="$t('verifierManage.wechatNicknameTips')"
              ></el-input>
            </li>
            <li>
              <el-button
                size="small"
                type="primary"
              >{{$t('verifierManage.filter')}}</el-button>
              <el-button size="small">{{$t('verifierManage.export')}}</el-button>
            </li>
            <div class="list-verifier-filters-right">
              <el-button
                type="primary"
                size="small"
                @click="addVerifierHandle"
              >{{$t('verifierManage.addVerifier')}}</el-button>
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
              :label="$t('verifierManage.userID')"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('verifierManage.nickname')"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('verifierManage.phoneNumber')"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('verifierManage.checkOrderQuantity')"
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
            <pagination
              :page-params.sync="pageParams"
              @pagination="initDataList"
            ></pagination>
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
    addVerifierHandle () {
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
.list-verifier-filters {
  overflow: hidden;
  margin-top: 10px;
  margin-left: 10px;
  line-height: 1.428571429;
}
.list-verifier-filters li {
  float: left;
  width: 360px;
  padding-right: 18px;
}
.list-verifier-filters-label {
  margin-right: 25px;
  line-height: 30px;
  font-size: 14px;
  color: #333;
}
.list-verifier-filters .input-narrow {
  width: 175px;
  color: #555;
}
.list-verifier-filters-right {
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
