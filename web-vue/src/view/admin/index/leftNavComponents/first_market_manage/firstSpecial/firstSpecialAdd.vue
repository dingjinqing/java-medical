<template>
  <div class="content f-s-add-page">
    <div class="main">
      <div class="nav_list">
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
        >
          <el-tab-pane
            label="全部首单特惠活动"
            name="0"
          ></el-tab-pane>
          <el-tab-pane
            label="进行中"
            name="1"
          ></el-tab-pane>
          <el-tab-pane
            label="未开始"
            name="2"
          ></el-tab-pane>
          <el-tab-pane
            label="已过期"
            name="3"
          ></el-tab-pane>
          <el-tab-pane
            label="已停用"
            name="4"
          ></el-tab-pane>
          <el-tab-pane
            label="添加首单特惠活动"
            name="5"
          ></el-tab-pane>
        </el-tabs>
      </div>
      <div class="first-special-add-content">
        <div class="info-top"></div>
        <el-form
          ref="firstSpecialAddForm"
          label-width="80px"
        >
          <el-form-item label="活动名称：">
            <el-input v-model="form.activeName"></el-input>
            <p>只作为商家记录使用，用户不会看到这个名称</p>
          </el-form-item>
          <el-form-item label="有效期：">
            <div>
              <el-radio-group v-model="form.datetype">
                <el-radio label="固定时间"></el-radio>
                <el-radio label="永久有效"></el-radio>
              </el-radio-group>
            </div>
            <div>
              <el-date-picker
                v-model="form.value1"
                type="datetime"
                placeholder="选择日期时间"
              >
              </el-date-picker>
              至
              <el-date-picker
                v-model="form.value2"
                type="datetime"
                placeholder="选择日期时间"
              >
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item label="活动优先级">
            <el-input-number v-model="form.activeFirst"></el-input-number>
            <p>用于区分不同首单特惠活动的优先级，请填写正整数，数值越大优先级越高</p>
          </el-form-item>
          <el-form-item label="限购数量：">
            <el-radio-group v-model="form.limit">
              <el-radio :label="0">不限制</el-radio>
              <el-radio :label="1">限制数量<el-input-number v-model="form.limitNum"></el-input-number>
              </el-radio>
            </el-radio-group>
            <el-checkbox v-model="form.limitP">超出限购数量后，买家不可继续添加购买该商品</el-checkbox>
          </el-form-item>
          <el-form-item
            label="活动商品"
            required
          ></el-form-item>
          <el-button type="text">收起更多配置</el-button>
          <div class="more-setting">
            <el-form-item label="活动分享：">
              <el-radio-group>
                <el-radio>
                  默认样式
                  <router-link>查看示例</router-link>
                  <a
                    href="javascript:void(0);"
                    download
                  >下载海报</a>
                </el-radio>
                <el-radio>
                  <p></p>
                  自定义样式
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      form: {
        activeName: '',
        datetype: 0,
        value1: '',
        value2: '',
        activeFirst: '',
        limit: 0,
        limitNum: 1,
        limitP: false
      }
    }
  }
}
</script>
