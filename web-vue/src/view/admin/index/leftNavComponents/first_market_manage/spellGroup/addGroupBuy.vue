<template>
  <div class="">
    <div
      class="return-pin-group-box"
      style="padding-top: 20px;"
    >
      <form
        name="formData"
        id="form1"
        method="post"
      >
        <input
          type="hidden"
          name="id"
          value=""
        >
        <table class="tb-pin-group">
          <tbody>
          </tbody>
          <tbody class="t_top">
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span class="tb-full-left"><strong>*</strong>拼团类型：</span>
              </td>
              <td>
                <input
                  type="radio"
                  name="activity_type"
                  value="1"
                  checked=""
                >普通拼团
                <input
                  type="radio"
                  name="activity_type"
                  value="2"
                  style="margin-left:30px;"
                >老带新拼团
                <span class="prompt">所有用户都可以开团，但只有新用户才能参团，保存后不可编辑</span>
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span class="tb-full-left"><strong>*</strong>活动名称：</span>
              </td>
              <td>
                <input
                  type="text"
                  class="name"
                  placeholder="请输入活动名称"
                  name="name"
                  value=""
                >
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span class="tb-full-left"><strong>*</strong>活动商品：</span>
              </td>
              <td>
                <input
                  type="button"
                  value="+ 选择商品"
                  id="sel-goods-btn"
                  class="choose_goods"
                  name="choose_goods"
                >
                <input
                  type="text"
                  name="goods_id"
                  value=""
                >
                <span
                  class="goods-namess"
                  style="display: none;"
                ></span>
                <span
                  class="goods-btn-modify"
                  style="display: none;"
                >修改</span>
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span
                  class="tb-full-left"
                  style="margin-bottom:17px;"
                ><strong>&nbsp;</strong>团长优惠：</span>
              </td>
              <td>
                <span style="float:left;">
                  <input
                    type="checkbox"
                    name="is_grouper_cheap"
                    style="vertical-align: text-top;margin-right: 5px;"
                  >
                  团长优惠
                </span>
                <div>
                  <p class="leader_off_tips">开启团长(开团人)优惠后，团长将享受更优惠价格，有助于提高开团率和成团率。
                  </p>
                  <p
                    class="leader_off_tips"
                    style="margin-left: 123px;"
                  ><span style="color: red;">注：</span>默认成团的团长也能享受团长优惠，为避免不必要的损失，请谨慎设置
                  </p>
                </div>
              </td>
            </tr>
            <tr>
              <td>
                <span class="tb-full-left"><strong>*</strong>优惠设置：</span>
              </td>
            </tr>
            <tr
              style="text-align: center;"
              id="product-info"
            >
              <td colspan="2">
                <el-table
                  border
                  style="width: 100%"
                >
                  <el-table-column
                    prop=""
                    label="商品名称,规格"
                  >
                    <template slot-scope="">
                      <el-input placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop=""
                    label="原价（元）"
                  >
                  </el-table-column>
                  <el-table-column
                    prop=""
                    label="拼团价（元）"
                  >
                  </el-table-column>
                  <el-table-column
                    prop=""
                    label="团长优惠价（元）"
                  >
                  </el-table-column>
                  <el-table-column
                    prop=""
                    label="原库存"
                  >
                  </el-table-column>
                  <el-table-column
                    prop=""
                    label="拼团库存"
                  >
                  </el-table-column>
                  <div style="margin-top: 20px">
                    <el-button @click="setCurrent(1)">批量设置拼团价</el-button>
                    <el-button @click="setCurrent(2)">批量设置拼团库存</el-button>
                    <el-button @click="setCurrent(3)">批量设置团长优惠价（元）</el-button>
                  </div>
                </el-table>
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span class="tb-full-left"><strong>*</strong>有效期：</span>
              </td>
              <td>
                <el-date-picker
                  v-model="value2"
                  type="datetimerange"
                  @change="dateChange(value2)"
                  :picker-options="pickerOptions"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  align="right"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
                <span>
                  <input
                    type="text"
                    class="tb-text date-text"
                    v-model="value2[0]"
                    name="start_time"
                    id="startDate"
                    onclick="picker();"
                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})"
                    autocomplete="off"
                  > 至
                  <input
                    type="text"
                    class="tb-text date-text"
                    v-model="value2[1]"
                    name="end_time"
                    id="endDate"
                    onclick="picker();"
                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})"
                    autocomplete="off"
                  >
                </span>
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span class="tb-full-left"><strong>*</strong>成团人数：</span>
              </td>
              <td>
                <input
                  type="number"
                  onkeyup="value=value.replace(/[^\d]/g,'')"
                  class="limit_amount"
                  placeholder="成团人数"
                  name="limit_amount"
                  value="2"
                  min="2"
                >
                <span class="prompt">不可小于2人,保存后不可编辑</span>
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span class="tb-full-left">下单商品数量：</span>
              </td>
              <td>
                单次下单购买拼团商品数量最小
                <input
                  type="number"
                  onkeyup="value=value.replace(/[^\d]/g,'')"
                  name="limit_buy_num"
                  value=""
                > 件
                <span class="prompt">请填写正整数，不填或为0表示不限制数量</span>
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px"> </td>
              <td>
                单次下单购买拼团商品数量最大
                <input
                  type="number"
                  onkeyup="value=value.replace(/[^\d]/g,'')"
                  name="limit_max_num"
                  value=""
                > 件
                <span class="prompt">请填写正整数，不填或为0表示不限制数量</span>
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span class="tb-full-left"><strong>*</strong>参团限制：</span>
              </td>
              <td>
                每人最多参加
                <input
                  type="number"
                  onkeyup="value=value.replace(/[^\d]/g,'')"
                  class="join_limit"
                  placeholder=""
                  name="join_limit"
                  value="0"
                  min="0"
                >
                次新团
                <span class="prompt">默认为0，0表示不限制数量。仅限制参与其他用户所开的团的数量</span>
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span class="tb-full-left"><strong>*</strong>开团限制：</span>
              </td>
              <td>
                每人最多开启
                <input
                  type="number"
                  onkeyup="value=value.replace(/[^\d]/g,'')"
                  class="open_limit"
                  placeholder=""
                  name="open_limit"
                  value="0"
                  min="0"
                >
                次新团
                <span class="prompt">默认为0，0表示不限制数量。仅限制同一用户的开团数量</span>
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span
                  class="tb-full-left"
                  style="margin-bottom:12px;"
                ><strong>&nbsp;</strong>默认成团：</span>
              </td>
              <td style="display:flex;">
                <span style="margin-right: 20px; float: left; margin-top: 8px;">
                  <input
                    type="checkbox"
                    name="is_default"
                  >
                  开启默认成团
                </span>
                <span style="color: #999;flex:1;margin-top:8px;">开启默认成团后，24小时内人数未满的团，系统将会模拟“匿名买家”凑满人数，使该团成团。
                  你只需要对已付款参团的真实买家发货。建议合理开启，以提高成团率
                </span>
              </td>
            </tr>
            <tr
              class="share_module"
              style="line-height: 33px;"
            >
              <td>
                <span class="tb-full-left"><strong>*</strong>运费设置：</span>
              </td>
              <td>
                <span>
                  <input
                    type="radio"
                    name="free_freight"
                    value="1"
                    checked=""
                  > 免运费
                  <input
                    type="radio"
                    name="free_freight"
                    value="0"
                    checked=""
                  > 使用原商品运费模板
                </span>
              </td>
            </tr>
            <tr style="height: 50px;">
              <td
                style="width: 100px"
                class="show_more show_basic"
                if_show="0"
              >
                <text>展开更多配置</text>
                <img
                  src="http://mpdevimg2.weipubao.cn/image/admin/info_down.png"
                  alt=""
                >
              </td>
            </tr>
            <tr style="height: 50px;">
              <td style="width: 100px">
                <span
                  class="tb-full-left"
                  style="    margin-bottom: 17px;"
                ><strong>&nbsp;</strong>鼓励奖：</span>
              </td>
              <td>
                <div class="coupon_content">
                  <input
                    type="text"
                    name="reward_coupon_id"
                    value=""
                  >
                  <div class="tem_right">
                    <p style="color: #999; padding-bottom: 10px;">买家拼团失败后给予一定奖励，可提升买家复购</p>
                    <div
                      class="coupon_div clearfix"
                      coupon_json=""
                    >
                      )
                      <div class="card_add card_add_click">
                        <img
                          src="http://mpdevimg2.weipubao.cn/image/admin/shop_beautify/add_decorete.png"
                          alt=""
                        >
                        <p>添加优惠券</p>
                      </div>
                    </div>
                    <p style="color:#999999;">最多添加5张优惠券，已过期和已停用的优惠券不能添加</p>
                  </div>
                </div>
              </td>
            </tr>
            <tr
              class="share_module"
              style="line-height: 33px;"
            >
              <td style="vertical-align: text-top !important;"><span class="tb-full-left">活动分享：</span></td>
              <td>
                <div
                  class="fl"
                  style="position: relative;"
                >
                  <input
                    type="radio"
                    name="share_action"
                    value="1"
                    checked=""
                  > 默认样式

                  <el-popover
                    placement="top"
                    width="260"
                  >
                    <img
                      style="width: -moz-available; width: 100%;"
                      src="http://mpdevimg2.weipubao.cn/image/admin/share/pin_share.jpg"
                    >

                    <el-button
                      type="text"
                      slot="reference"
                    >查看示例</el-button>
                  </el-popover>

                  <el-popover
                    placement="top"
                    width="260"
                  >
                    <img
                      style="width: -moz-available; width: 100%;"
                      src="http://mpdevimg2.weipubao.cn/image/admin/share/pin_pictorial.jpg"
                    >
                    <el-button
                      type="text"
                      slot="reference"
                    >海报</el-button>
                  </el-popover>

                  <div>
                    <input
                      type="radio"
                      name="share_action"
                      value="2"
                    > 自定义样式
                  </div>

                  <div
                    style="padding-left: 22px; "
                    class=""
                  >
                    <span>文案：</span><input
                      type="text"
                      name="share_doc"
                      value=""
                      style="margin-left: 18px;"
                    >
                  </div>
                  <div style="padding-left: 22px;">
                    <span>分享图：</span>
                    <input
                      type="radio"
                      name="share_img_action"
                      value="1"
                      checked=""
                    > 活动商品信息图
                    <p style="padding-left: 60px;">
                      <input
                        type="radio"
                        name="share_img_action"
                        value="2"
                      >自定义图片
                    </p>
                    <div
                      class="module_share_image"
                      style="margin-left: 60px;"
                    >
                      <input
                        type=""
                        name="share_img"
                        value=""
                      >
                      <div
                        class="choose_img"
                        style="display: none;"
                      >
                        <img src="">
                        <span>重新选择</span>
                      </div>
                      <input
                        type="button"
                        value=""
                        class="add_image"
                        style="display: inline-block;"
                      >
                      <span style="float: left; margin-top: 25px; margin-left: 20px;">建议尺寸: 800*800像素</span>
                    </div>
                  </div>
                </div>
              </td>
            </tr>

          </tbody>
        </table>
        <el-row>
          <el-col
            :span="2"
            :offset="11"
          >
            <div class="grid-content ">
              <el-button type="primary">主要按钮</el-button>
            </div>
          </el-col>

        </el-row>

      </form>
    </div>
  </div>
</template>
<script>

export default {
  data () {
    return {
      // 规格表数据
      productTableData: {

      },
      // 时间控件
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      value2: ''
    }
  },
  mounted () {

  },
  methods: {
    // 活动时间时间选择
    dateChange (date) {
      console.log(date)
    },
    // 批量设置规格
    setCurrent (index) {
      console.log(index)
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;

  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
  }
}
.tb-full-left {
  margin-left: 5px;
  display: inline-block;
  width: 100px;
}
.tb-full-left > strong {
  color: red;
  margin-right: 10px;
}

.return-pin-group-box {
  padding: 8px 23px;
  background: #ffffff;
}
.prompt {
  color: #999;
  margin-left: 20px;
}
.tb-pin-group input[type="text"],
.tb-pin-group input[type="number"] {
  width: 165px;
  height: 30px;
  line-height: 30px;
  border: 1px solid #eee;
  padding-left: 12px;
}
#sel-goods-btn {
  width: 120px;
  height: 30px;
  background-color: #fff;
  opacity: 1;
  color: #5a8bff;
  border: 1px solid #ddd;
  cursor: pointer;
}
.leader_off_tips {
  display: inline-block;
  color: #999;
  margin-left: 45px;
}
td {
  padding: 8px 10px;
}
#product-info table tr td {
  border: 1px solid #eee;
}
.tb-pin-group td > table > tbody > tr > th {
  width: 25%;
  min-width: 200px;
  height: 35px;
  text-align: center;
  background-color: #f5f5f5;
  border-spacing: 0;
}
input[type="checkbox"] {
  width: 12px;
  height: 12px;
  background-size: auto;
  background-size: 100%;
  margin-right: 3px;
  margin-top: 0;
  position: relative;
  top: 3px;
}
input[name="is_default"] {
  width: 12px;
  height: 12px;
  background-size: auto;
  background-size: 100%;
  position: relative;
  top: 1px;
  margin-right: 5px;
}
input[type="radio"]:checked {
  background: url("/image/admin/check_yes.png") no-repeat;
}
input[type="radio"] {
  margin-top: 0;
}
input[type="radio"] {
  position: relative;
  top: 3px;
  width: 15px;
  height: 15px;
  margin-right: 3px;
}
.show_more {
  color: #5a8bff;
}
</style>
