<template>
  <div class="main_container">
    <div class="recommend_container">
      <el-form
        :model="paramsData"
        ref="ruleForm"
        label-width="100px"
      >
        <el-form-item
          :label="$t('recommend.eventName') + '：'"
          prop="recommendName"
        >
          <el-input
            v-model="paramsData.recommendName"
            size="small"
            class="default_width"
          ></el-input>
          <span class="tips">{{$t('recommend.eventNameTips')}}</span>
        </el-form-item>
        <el-form-item
          :label="$t('recommend.addingGoods') + '：'"
          prop="name"
        >
          <el-radio-group v-model="paramsData.chooseType">
            <el-radio :label="0">{{$t('recommend.generalRecommendation')}}</el-radio>
            <el-radio :label="1">{{$t('recommend.intelligentRecommendation')}}</el-radio>
          </el-radio-group>
          <p
            class="tips"
            v-if="paramsData.chooseType === 1"
          >{{$t('recommend.recommendTips')}}</p>
          <p>{{$t('recommend.recommendGoodsNum')}}：<el-input-number
              v-model="paramsData.recommendNumber"
              :min="0"
              :controls="false"
              size="small"
            ></el-input-number> <span class="tips">{{$t('recommend.maxGoods')}}</span></p>
          <p
            class="tips"
            v-if="paramsData.chooseType === 0"
          >{{$t('recommend.maxGoodsTips')}}</p>
          <div
            v-if="paramsData.chooseType === 0"
            class="choose_goods_content"
          >
            <el-radio-group v-model="paramsData.recommendType">
              <el-radio :label="0">{{$t('recommend.allProducts')}}</el-radio>
              <el-radio :label="1">{{$t('recommend.designatedProduct')}}</el-radio>
            </el-radio-group>
            <div
              v-if="paramsData.recommendType === 1"
              class="goods_content"
            >
              <div class="choose_item">
                <span
                  class="item_button"
                  @click="showGoods"
                >
                  + 选择商品
                </span>
                <span
                  class="count"
                  v-if="paramsData.recommendGoods.length > 0"
                >已选择商品{{paramsData.recommendGoods.length}}件</span>
              </div>
              <div class="choose_item">
                <span
                  class="item_button"
                  @click="showSort(1)"
                >
                  + 选择商家分类
                </span>
                <span
                  class="count"
                  v-if="paramsData.recommendSortIds.length > 0"
                >已选择商家分类{{paramsData.recommendSortIds.length}}个</span>
              </div>
              <div class="choose_item">
                <span
                  class="item_button"
                  @click="showSort(2)"
                >
                  + 选择平台分类
                </span>
                <span
                  class="count"
                  v-if="paramsData.recommendCatIds.length > 0"
                >
                  已选择平台分类{{paramsData.recommendCatIds.length}}个
                </span>
              </div>
            </div>
          </div>
        </el-form-item>
        <el-form-item
          :label="$t('recommend.applicationPage') + '：'"
          prop="name"
        >
          <el-checkbox-group v-model="paramsData.recommendUsePage">
            <template v-for="item in pageData">
              <p :key="item.mark">
                <el-checkbox :label="item.mark">{{$t('recommend.pageList')[item.mark]}}</el-checkbox> <span class="tips">{{$t('recommend.pageListTips')[item.mark]}}</span>
                <el-popover
                  placement="right-start"
                  trigger="hover"
                >
                  <el-image
                    style="width: 200px"
                    :src="item.imgUrl"
                  ></el-image>
                  <span
                    slot="reference"
                    class="exempli"
                  >{{$t('recommend.viewExample')}}</span>
                </el-popover>
              </p>
            </template>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item
          :label="$t('recommend.enabledState') + '：'"
          prop="name"
        >
          <el-radio-group v-model="paramsData.status">
            <p class="status">
              <el-radio :label="0">{{$t('recommend.enableNow')}}</el-radio> <span class="tips">{{$t('recommend.enableTips')}}</span>
            </p>
            <p class="status">
              <el-radio :label="1">{{$t('recommend.temporarilyDisabled')}}</el-radio> <span class="tips">{{$t('recommend.disableTips')}}</span>
            </p>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </div>
    <div class="footer">
      <el-button
        @click="submit"
        type="primary"
        size="small"
      >{{$t('recommend.save')}}</el-button>
    </div>
    <chooseGoods
      :tuneUpChooseGoods="showGoodsDialog"
      :chooseGoodsBack="paramsData.recommendGoods"
      @result="getSelectGoods"
    />
    <sortDialog
      :dialogVisible.sync="showSortDialog"
      :classFlag="sortType"
      :backDataArr="sortType === 1 ? paramsData.recommendSortIds : paramsData.recommendCatIds"
      @BusClassTrueArr="getSortIds"
    />
  </div>
</template>

<script>
import { addRecommend, getRecommendInfo, updateRecommend } from '@/api/admin/goodsManage/goodsRecommend/goodsRecommend'
export default {
  components: {
    chooseGoods: () => import('@/components/admin/choosingGoods'),
    sortDialog: () => import('@/components/admin/addingBusClassDialog')
  },
  data () {
    return {
      paramsData: {
        recommendName: null,
        recommendType: 0,
        recommendNumber: 6,
        chooseType: 0,
        status: 0,
        recommendUsePage: [],
        recommendGoods: [],
        recommendSortIds: [],
        recommendCatIds: []
      },
      pageData: [
        { mark: 'cart', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_cart.png` },
        { mark: 'orderlist', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_order.png` },
        { mark: 'bargainitem', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_bargin.png` },
        { mark: 'groupbuyitem', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_group.jpg` },
        { mark: 'search', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_list.jpg` },
        { mark: 'payment', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_success.jpg` },
        { mark: 'order_complete', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_order_complete.jpg` },
        { mark: 'new_search', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_search.jpg` },
        { mark: 'item', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_item.jpg` }
      ],
      showGoodsDialog: false,
      showSortDialog: false,
      sortType: 1
    }
  },
  mounted () {
    this.requestInfo()
  },
  methods: {
    submit () {
      let obj = {
        ...this.paramsData
      }
      if (this.$route.query.id) {
        updateRecommend(obj).then(res => {
          if (res.error === 0) {
            if (res.content === 'CODE_SUCCESS') {
              this.$message.success({
                message: '商品推荐修改成功！',
                duration: 2000,
                onClose: () => {
                  this.$router.push({
                    name: 'recommend'
                  })
                }
              })
            }
          }
        })
      } else {
        addRecommend(obj).then(res => {
          console.log(res)
          if (res.error === 0) {
            if (res.content === 'CODE_SUCCESS') {
              this.$message.success({
                message: '商品推荐添加成功！',
                duration: 2000,
                onClose: () => {
                  this.$router.push({
                    name: 'recommend'
                  })
                }
              })
            }
          }
        })
      }
    },
    showGoods () {
      this.showGoodsDialog = !this.showGoodsDialog
    },
    showSort (type) {
      this.sortType = type
      this.showSortDialog = true
    },
    getSelectGoods (data) {
      this.paramsData.recommendGoods = data
    },
    getSortIds (data) {
      switch (this.sortType) {
        case 2:
          this.paramsData.recommendCatIds = data
          break
        default:
          this.paramsData.recommendSortIds = data
          break
      }
    },
    requestInfo () {
      if (!this.$route.query.id) return false
      getRecommendInfo(this.$route.query.id).then(res => {
        if (res.error === 0) {
          this.paramsData = {
            ...res.content,
            recommendGoods: res.content.recommendGoods ? res.content.recommendGoods.map(item => { return item.goodsId }) : [],
            recommendSortIds: res.content.recommendSortIds ? res.content.recommendSortIds : [],
            recommendCatIds: res.content.recommendCatIds ? res.content.recommendCatIds : []
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.main_container {
  padding: 10px;
  .recommend_container {
    margin-bottom: 50px;
    padding: 10px;
    background-color: #fff;
    .choose_goods_content {
      width: 470px;
      background: #f8f8f8;
      border-radius: 5px;
      border: 1px solid #eee;
      padding: 0 10px;
      .goods_content {
        display: flex;
        flex-direction: column;
        .item_button {
          padding: 4px 8px;
          width: auto;
          border: 1px solid #ccc;
          background: #fff;
          cursor: pointer;
          color: #5a8bff;
        }
        .count {
          margin-left: 15px;
          color: #5a8bff;
        }
      }
    }
  }
  .tips {
    color: #999;
    font-size: 14px;
  }
  .exempli {
    color: #5a8bff;
    font-size: 14px;
    margin-left: 6px;
    cursor: pointer;
  }
  .status {
    line-height: 40px;
  }
  .default_width {
    width: 170px;
  }
  .footer {
    position: absolute;
    bottom: 0;
    right: 27px;
    left: 160px;
    height: 52px;
    padding: 10px 0;
    background-color: #fff;
    text-align: center;
    border-top: 1px solid #eee;
    z-index: 99;
  }
}
</style>
