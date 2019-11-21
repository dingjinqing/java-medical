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
        <span>{{$t('allGoods.allGoodsHeaderData.category') + '：'}}</span>
        <el-select
          v-model="searchParams.sortName"
          size="small"
          style="width: 170px;"
        >
          <el-option
            :label="$t('allGoods.allGoodsHeaderData.chooseCategory')"
            :value="null"
          />
          <el-option
            v-for="(item,index) in goodsCatOptions"
            :label="item.catName+' ('+item.goodsNumberSum+')'"
            :value="item.catId"
            :key="index"
            :style="{paddingLeft: (item.level+1)*20+'px'}"
          />
        </el-select>
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
            <span
              @click="viewEvaluation(scope.row.goodsId)"
              style="color: #5a8bff;cursor: pointer;"
            >{{$t('evaluation.view')}}</span>
            <span
              @click="addEvaluation(scope.row)"
              style="color: #5a8bff;cursor: pointer;"
            >{{$t('evaluation.addEvaluation')}}</span>
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
import { getAllGoodsInitValue } from '@/api/admin/goodsManage/allGoods/allGoods'
import { CommentGoodsList } from '@/api/admin/goodsManage/evaluationManagement/evaluationManagement'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      searchParams: {
        goodsName: null,
        sortName: null
      },
      goodsCatOptions: [],
      loading: false,
      pageParams: {
      },
      dataList: null
    }
  },
  mounted () {
    // 初始化form表单下拉框数据
    this.initFilterData()
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
    /* 初始化form表单下拉框数据 */
    initFilterData () {
      let obj = {
        isOnSale: 1,
        isSaleOut: false,
        selectType: 1
      }
      getAllGoodsInitValue(obj).then(res => {
        let { content } = res
        this.goodsCatOptions = this._disposeGoodsSortAndCatData(content.sysCates, 'catId')
      })
    },
    /* 处理后台传入的商家分类数据 */
    _disposeGoodsSortAndCatData (data, idName) {
      let retObj = {}

      for (let i = 0; i < data.length; i++) {
        let item = data[i]

        // 是否自身节点被创建过（子节点先遍历到了）
        let selfItem = retObj[item[idName]]
        if (selfItem === undefined) {
          // 未遍历到则初始化自己
          retObj[item[idName]] = { 'item': item, children: [] }
          selfItem = retObj[item[idName]]
        } else {
          // 已创建过，（因提前遍历了子节点而创建）
          selfItem.item = item
        }

        let parentItem = retObj[item.parentId]
        // 有父亲直接插入
        if (parentItem !== undefined) {
          parentItem.children.push(selfItem)
        } else {
          // 没有则创建临时父亲
          retObj[item.parentId] = { 'item': null, children: [selfItem] }
        }
      }

      let retArr = []

      if (data.length === 0) {
        return retArr
      }
      let rootArr = retObj['0'].children
      // 处理结果将对象变为数组
      for (let i = 0; i < rootArr.length; i++) {
        let retItem = rootArr[i]
        retArr.push(retItem.item)
        if (retItem.children.length > 0) {
          rootArr.splice(i + 1, 0, ...(retItem.children))
        }
      }
      return retArr
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
</style>
