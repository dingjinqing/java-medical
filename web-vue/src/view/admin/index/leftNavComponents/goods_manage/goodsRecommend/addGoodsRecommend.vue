<template>
  <div class="main_container">
    <div class="recommend_container">
      <el-form
        :model="paramsData"
        ref="ruleForm"
        label-width="100px"
      >
        <el-form-item
          label="活动名称"
          prop="recommendName"
        >
          <el-input
            v-model="paramsData.recommendName"
            size="small"
            class="default_width"
          ></el-input>
          <span class="tips">只作为商家记录使用，用户不会看到这个名称</span>
        </el-form-item>
        <el-form-item
          label="添加商品"
          prop="name"
        >
          <el-radio-group v-model="paramsData.recommendType">
            <el-radio :label="0">常规推荐</el-radio>
            <el-radio :label="1">智能推荐</el-radio>
          </el-radio-group>
          <p
            class="tips"
            v-if="paramsData.recommendType === 1"
          >系统将根据（搜索、加购、浏览、购买等）用户行为，结合推荐算法，实现千人千面的个性化推荐</p>
          <p>推荐商品数量：<el-input-number
              v-model="paramsData.recommendGoodsNum"
              :min="0"
              :controls="false"
              size="small"
            ></el-input-number> <span class="tips">最多显示200个商品</span></p>
          <p
            class="tips"
            v-if="paramsData.recommendType === 0"
          >选择商品数量超出推荐商品数量，将从已选商品中随机选出推荐数量展示</p>
          <div
            v-if="paramsData.recommendType === 0"
            class="choose_goods_content"
          >
            <el-radio-group v-model="paramsData.chooseGoodsType">
              <el-radio :label="0">全部商品</el-radio>
              <el-radio :label="1">指定商品</el-radio>
            </el-radio-group>
            <div
              v-if="paramsData.chooseGoodsType === 1"
              class="goods_content"
            >
              <div class="choose_item">
                <span class="item_button">
                  + 选择商品
                </span>
              </div>
              <div class="choose_item">
                <span class="item_button">
                  + 选择商家分类
                </span>
              </div>
              <div class="choose_item">
                <span class="item_button">
                  + 选择平台分类
                </span>
              </div>
            </div>
          </div>
        </el-form-item>
        <el-form-item
          label="应用页面"
          prop="name"
        >
          <el-checkbox-group v-model="paramsData.recommendUsePage">
            <template v-for="item in pageData">
              <p :key="item.mark">
                <el-checkbox :label="item.mark">{{item.pageName}}</el-checkbox> <span class="tips">{{item.tips}}</span>
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
                  >查看示例</span>
                </el-popover>
              </p>
            </template>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item
          label="启用状态"
          prop="name"
        >
          <el-radio-group v-model="paramsData.status">
            <p class="status">
              <el-radio :label="0">立即启用</el-radio> <span class="tips">每个页面最多可启用一个推荐模板，启用后显示在对应页面，如有重复，则不启用当前模板</span>
            </p>
            <p class="status">
              <el-radio :label="1">暂时停用</el-radio> <span class="tips">暂时不启用该模板，后续可根据需要启用</span>
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
      >保存</el-button>
    </div>
  </div>
</template>

<script>
import { addRecommend } from '@/api/admin/goodsManage/goodsRecommend/goodsRecommend'
export default {
  data () {
    return {
      paramsData: {
        recommendName: null,
        recommendType: 0,
        recommendGoodsNum: 6,
        chooseGoodsType: 0,
        status: 0,
        recommendUsePage: []
      },
      pageData: [
        { mark: 'cart', pageName: '购物车页', tips: '展示在购物车页底部，用于商品推荐', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_cart.jpg` },
        { mark: 'orderlist', pageName: '订单列表页', tips: '展示在订单列表页底部，用于商品推荐', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_order.jpg` },
        { mark: 'bargainitem', pageName: '砍价活动页', tips: '展示在砍价活动页底部，用于商品推荐', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_bargin.jpg` },
        { mark: 'groupbuyitem', pageName: '参团活动页', tips: '展示在参团活动页底部，用于商品推荐', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_group.jpg` },
        { mark: 'search', pageName: '商品列表页', tips: '展示在商品列表页底部，用于商品推荐', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_list.jpg` },
        { mark: 'payment', pageName: '支付成功页', tips: '展示在支付成功页底部，用于商品推荐', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_success.jpg` },
        { mark: 'order_complete', pageName: '订单完成页', tips: '展示在订单完成页底部，用于商品推荐', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_order_complete.jpg` },
        { mark: 'new_search', pageName: '商品搜索页', tips: '展示在商品搜索页底部，用于商品推荐', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_search.jpg` },
        { mark: 'item', pageName: '商品详情页', tips: '展示在商品详情页底部，用于商品推荐', imgUrl: `${this.$imageHost}/image/admin/new_preview_image/recommend/ex_item.jpg` }
      ]
    }
  },
  methods: {
    submit () {
      let obj = {
        ...this.paramsData,
        recommendType: this.paramsData.chooseGoodsType
      }
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
  }
}
</script>

<style lang="scss" scoped>
.main_container {
  padding: 10px;
  .recommend_container {
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
    width: 180px;
  }
  .footer {
    position: fixed;
    bottom: 0;
    left: 160px;
    right: 10px;
    height: 52px;
    padding: 10px 0;
    background-color: #fff;
    text-align: center;
    z-index: 3;
  }
}
</style>
