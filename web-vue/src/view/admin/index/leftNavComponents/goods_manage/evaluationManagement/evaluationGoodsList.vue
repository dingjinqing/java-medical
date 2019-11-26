<template>
  <div
    class="table_box"
    style="background: none;"
  >
    <div
      class="filters"
      style="margin-left: 0;background: #fff;height: 50px;margin-bottom: 10px;"
    >
      <div class="filters_item">
        <span>{{$t('evaluation.goodsName') + '：'}}</span>
        <el-input
          v-model="searchParams.goodsName"
          :placeholder="$t('evaluation.searchGoods')"
          size="small"
          style="width: 170px;"
        ></el-input>
      </div>
      <div class="filters_item">
        <sortCatTreeSelect ref="catTree" treeType="cat" :selectedId.sync="searchParams.sortName"/>
      </div>
      <div style="margin-left: 37px;">
        <el-button
          @click="initDataList"
          type="primary"
          size="small"
        >{{$t('marketCommon.filter')}}</el-button>
      </div>
    </div>
    <div style="width: 100%; padding:10px;background: #fff;">
      <el-table
        v-loading="loading"
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="dataList"
        border
      >
        <el-table-column
          :label="$t('evaluation.goodsName')"
          align="center"
          width="200"
        >
          <template slot-scope="scope">
            <div class="goods_info">
              <img
                :src="$imageHost+'/'+scope.row.goodsImg"
                alt=""
              >
              <div class="right_info">
                <div class="goods_name">{{scope.row.goodsName}}</div>
                <div
                  class="goods_spec"
                  v-if="scope.row.prdDesc"
                >{{scope.row.prdDesc}}</div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.productCode')"
          prop="goodsSn"
          align="center"
          width="200px"
        >
        </el-table-column>
        <el-table-column
          :label="$t('allGoods.allGoodsHeaderData.category')"
          prop="catName"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('allGoods.allGoodsData.shopPrice')"
          prop="shopPrice"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('allGoods.allGoodsData.goodsNumber')"
          prop="goodsNumber"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.uv')"
          prop="uv"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.pv')"
          prop="pv"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.actualEvaluationNum')"
          prop="realCommNum"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.addEvaluationNum')"
          prop="shopCommNum"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.operating')"
          width="120"
          align="center"
        >
          <template slot-scope="scope">
            <el-tooltip
              :content="$t('evaluation.view')"
              placement="top"
            >
              <span
                class="el-icon-tickets operateSpan"
                @click="viewEvaluation(scope.row.goodsId)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('evaluation.addEvaluation')"
              placement="top"
            >
              <span
                class="el-icon-circle-plus-outline operateSpan"
                @click="addEvaluation(scope.row)"
              ></span>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <pagination
      :page-params.sync="pageParams"
      @pagination="initDataList"
    />
  </div>
</template>

<script>
import sortCatTreeSelect from '@/components/admin/sortCatTreeSelect'
import { CommentGoodsList } from '@/api/admin/goodsManage/evaluationManagement/evaluationManagement'
export default {
  components: {
    sortCatTreeSelect,
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      searchParams: {
        goodsName: null,
        sortName: null
      },
      loading: false,
      pageParams: {
      },
      dataList: null
    }
  },
  mounted () {
    // 初始化国际语言
    this.langDefault()
    this.initDataList()
  },
  methods: {
    initDataList () {
      let obj = {
        ...this.searchParams,
        page: { ...this.pageParams }
      }
      CommentGoodsList(obj).then(res => {
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.dataList = res.content.dataList
        }
      })
    },

    viewEvaluation (goodsId) { console.log(goodsId) },
    addEvaluation (goodsInfo) {
      this.$emit('handleAddEvaluation', goodsInfo)
    }
  }
}
</script>

<style lang="scss" scoped>
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.goods_info {
  display: flex;
  > img {
    width: 60px;
    height: 60px;
    margin-right: 5px;
  }
  > .right_info {
    flex: 1;
    display: flex;
    flex-direction: column;
    text-align: left;
    justify-content: space-between;
    .goods_name {
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      overflow: hidden;
      /*! autoprefixer: off */
      -webkit-box-orient: vertical;
      text-align: left;
      line-height: 1;
    }
  }
}
.operateSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
}
</style>
