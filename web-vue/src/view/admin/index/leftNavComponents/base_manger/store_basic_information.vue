<template>
  <!-- 店铺基础信息 -->
  <el-container>
    <el-main class="shop_config">
      <ul class="program_details">
        <li class="details_item">
          <span class="item_label">店铺名称：</span>
          <div
            class="item_content"
            v-if="!changeNameFlag"
          >
            <span>{{form.shopName}}</span>
            <el-button
              type="text"
              class="iconSpan"
              size="small"
              @click="changeNameFlagHandle"
            >更改</el-button>
          </div>
          <div
            class="item_content"
            v-else
          >
            <el-input
              v-model="form.shopName"
              @blur="changeNameHandle"
              style="width: 182px;"
              size="small"
            ></el-input>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">店铺状态：</span>
          <div class="item_content">
            <el-radio-group v-model="form.businessState">
              <el-radio :label="1">已营业</el-radio>
              <el-radio :label="0">未营业</el-radio>
            </el-radio-group>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">创建时间：</span>
          <div class="item_content">
            <span>{{form.created}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">有效期至：</span>
          <div class="item_content">
            <span>{{form.expireTime}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">后端店铺Logo：</span>
          <div class="item_content">
            <div class="logo_wrap">
              <el-image
                :src="$imageHost + '/' + form.shopAvatar"
                fit="fill"
                style="padding:4px; width:100%;"
              ></el-image>
              <span class="logo_span">更改</span>
            </div>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">前端店铺Logo:</span>
          <div class="item_content">
            <p>图片格式必须为：png,bmp,jpeg,jpg,gif；不可大于5M；</p>
            <p>建议使用png格式图片，以保持最佳效果；建议图片尺寸为144px*144px</p>
            <el-radio-group>
              <el-radio>不显示</el-radio>
              <el-radio>自定义</el-radio>
            </el-radio-group>
            <div>
              <div>
                <div class="logo_wrap">
                  <el-image
                    :src="frontLogo"
                    fit="fill"
                    style="padding:4px; width:100%;"
                  ></el-image>
                  <span class="logo_span">更改</span>
                </div>
                <div>
                  <p>将于前端页面底部显示</p>
                  <el-button type="text">查看示例</el-button>
                  <p>建议使用png格式图片，图片尺寸300px*80px</p>
                </div>
              </div>
              <div>
                <span>链接：</span>
                <el-input
                  size="small"
                  style="width: 182px;"
                ></el-input>
                <el-button size="small">选择链接</el-button>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </el-main>
    <el-footer>
      <el-button size="small">保存</el-button>
    </el-footer>
  </el-container>
</template>

<script>
import { getBaseInfo } from '@/api/admin/basicConfiguration/shopConfig'
export default {
  data () {
    return {
      frontLogo: this.$imageHost + '/image/admin/shop_def_y.png',
      form: {
        shopName: '',
        businessState: 0,
        created: '',
        expireTime: '',
        shopAvatar: 'image/admin/shop_def_y.png'
      },
      changeNameFlag: false
    }
  },
  created () {
    this.initData()
  },
  methods: {
    initData () {
      getBaseInfo().then(res => {
        if (res.error === 0) {
          console.log('content', res.content)
          this.form = Object.assign({}, res.content)
        }
      })
    },
    changeNameFlagHandle () {
      this.changeNameFlag = true
    },
    changeNameHandle () {
      this.changeNameFlag = false
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
  .logo_wrap {
    width: 80px;
    height: 90px;
    position: relative;
    border: 1px solid #ccc;
    .logo_span {
      display: inline-block;
      width: 100%;
      height: 20px;
      line-height: 20px;
      position: absolute;
      bottom: 0;
      left: 0;
      color: #fff;
      background: rgba(0, 0, 0, 0.5);
      text-align: center;
      font-size: 12px;
    }
  }
}
</style>
