<template>
  <div class="content">
    <el-form
      ref="form"
      :model="param"
      :rules="formRules"
      label-width="130px"
      :label-position="right"
    >
      <el-form-item label="活动类型：">
        <!-- <el-radio-group v-model="param.actType"> -->
        <div>
          <el-radio
            :label=1
            v-model="param.actType"
          >
            一口价结算
          </el-radio>
          <span>用户选择多件商品，以商家设置的结算价格进行打包购买</span>
        </div>
        <div>
          <el-radio
            :label=2
            v-model="param.actType"
          >指定折扣结算</el-radio>
          <span>用户选择多件商品，以商家设置的折扣进行打包购买</span>
        </div>
        <!-- </el-radio-group> -->
      </el-form-item>

      <el-form-item label="活动名称：">
        <el-input
          size="small"
          style="width: 150px"
          v-model="param.packageName"
        ></el-input>
        <span>只作为商家记录使用，用户不会看到这个名称</span>
      </el-form-item>

      <el-form-item label="活动时间：">
        <el-date-picker
          v-model="param.validity"
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          format="yyyy-MM-dd HH:mm:ss"
          :range-separator="$t('shipping.to')"
          :start-placeholder="$t('ordinaryCoupon.startTime')"
          :end-placeholder="$t('ordinaryCoupon.endTime')"
          :default-time="['00:00:00','23:59:59']"
          size="small"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="活动规则：">
        <section>
          <div>最多可配置3个商品组，买家在每组商品中分别选择指定件数，即可以“结算总价格”结算</div>
          <div>
            例如：商家可设置3个商品组，买家需在每个商品组中选择1件，3组共3件以总价100元结算
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src1"></el-image>
              <el-button
                slot="reference"
                type="text"
              ></el-button>
            </el-popover>
          </div>
        </section>
      </el-form-item>

      <el-form-item label="结算总价格：">
        <el-input
          size="small"
          style="width: 150px"
          v-model="param.totalMoney"
        ></el-input>&nbsp;&nbsp;元
      </el-form-item>

      <el-form-item label="商品组：">
        <section>
          <el-checkout v-mod="param.group1">商品组1</el-checkout>
          <div>
            <span>名称</span>
            <el-input
              size="small"
              style="width: 80px"
              v-model="param.groupName"
            ></el-input>
            <span>商品组名称，最多可填4个字</span>
          </div>
          <div>
            <span>需选择</span>
            <el-input
              size="small"
              style="width: 80px"
              v-model="param.groupName"
            ></el-input>&nbsp;件
            <span>该商品组需要选购的商品数量，请填写正整数</span>
          </div>
        </section>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  data () {
    return {
      param: {
        actType: 1, // 活动类型
        packageName: '', // 活动名称
        validity: '', // 活动时间
        startTime: '',
        endTime: '',
        totalMoney: '', // 总结算价格
        group1: '',
        groupName: ''
      },
      srcList: {
        src1: `${this.imageHost}//image/admin/new_preview_image/packagesale.jp`
      }
    }
  }
}

</script>
<style lang="scss" scoped>
.content {
  // padding: 10px;
  margin-top: 10px;
  padding: 15px;
  background: #fff;
}
</style>
