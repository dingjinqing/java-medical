<template>
  <!-- 店铺基础信息 -->
  <el-container>
    <el-main class="shop_config">
      <ul class="program_details">
        <li class="details_item">
          <span class="item_label">店铺名称：</span>
          <div class="item_content">
            <span>微辅宝电商运营</span>
            <el-button
              type="text"
              class="iconSpan"
              size="small"
            >更改</el-button>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">店铺状态：</span>
          <div class="item_content">
            <el-radio-group>
              <el-radio label="1">已营业</el-radio>
              <el-radio label="0">未营业</el-radio>
            </el-radio-group>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">创建时间：</span>
          <div class="item_content">
            <span>2018-04-03 13:32:21</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">有效期至：</span>
          <div class="item_content">
            <span>2020-04-03</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">后端店铺Logo：</span>
          <div class="item_content">
            <div class="logo-wrap">
              <el-image></el-image>
            </div>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">前端店铺Logo:</span>
          <div class="item_content">
            <p>图片格式必须为：png,bmp,jpeg,jpg,gif；不可大于5M；</p>
            <p>建议使用png格式图片，以保持最佳效果；建议图片尺寸为144px*144px</p>
            <el-group>
              <el-radio>不显示</el-radio>
              <el-radio>自定义</el-radio>
            </el-group>
            <div>
              <div>
                <div class="logo-wrap">
                  <el-image></el-image>
                </div>
                <div>
                  <p>将于前端页面底部显示</p>
                  <el-button type="text">查看示例</el-button>
                  <p>建议使用png格式图片，图片尺寸300px*80px</p>
                </div>
              </div>
              <div>
                <span>链接：</span>
                <el-input></el-input>
                <el-button>选择链接</el-button>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </el-main>
    <el-footer>
      <el-button>保存</el-button>
    </el-footer>
  </el-container>
</template>

<script>
// import { getBaseInfo } from '@/api/admin/basicConfiguration/shopConfig'
export default {
  data () {
    return {}
  },
  created () {
    this.initData()
  },
  methods: {
    initData () {
      // getBaseInfo().then(res => {
      //   if (res.error === 0) {
      //     console.log(res.content)
      //   }
      // })
    }
  }
}
</script>

<style lang="scss" scoped>
.shop_config {
  padding: 10px 25px;
  background: #fff;
  min-height: 750px;
}
.details_item {
  display: flex;
  line-height: 32px;
  font-size: 13px;
  .item_label {
    height: 32px;
    width: 110px;
  }
  .item_content {
    flex: 1;
  }
}
</style>
