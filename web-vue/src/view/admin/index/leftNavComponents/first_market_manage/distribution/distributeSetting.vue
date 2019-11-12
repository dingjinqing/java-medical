<template>
  <div>
    <el-form
      ref="form"
      :model="form"
      label-width="140px"
    >
      <el-form-item label="分销开关：">
        <el-switch v-model="form.value1"></el-switch>
        <div class="text">
          开关默认关闭，开启开关，则用户可以申请为店铺分销员，分销员邀请用户注册产生订单，购买者邀请人可获得佣金奖励。关闭开关，手机端个人中心”分销中心“菜单隐藏，用户下单，邀请人不再产生佣金奖励，系统分销机制关闭，邀请不再记录邀请关系。
        </div>
      </el-form-item>

      <el-form-item
        label="分销员审核："
        v-if="form.value1 === true"
      >
        <el-switch v-model="form.value2"></el-switch>
        <span>若开启审核，您需要配置推广文案内容）
          <a href="javascript:void(0);">推广文案配置</a></span>
        <div class="text">
          开启分销员审核功能后，普通用户申请成为分销员时需要经过商家审核。关闭则成为店铺分销员不需要申请审核，全部用户均默认为店铺分销员。
        </div>
        <template v-if="form.value2 === true">
          <el-checkbox v-model="form.checked1">用户申请成为分销员时，需要填写邀请码</el-checkbox>
          <p>勾选后，系统自动生成分销员邀请码。 <a
              href="javascript:void(0);"
              style="color: red;"
            >注：店铺中至少有一个分销员时，可开启此功能。</a> 如需修改邀请码，请到 <a href="javascript:void(0);">分销员列表</a> 中进行设置</p>
          <el-checkbox v-model="form.checked2">用户申请成为分销员时，需要提交个人信息</el-checkbox>

          <div
            v-if="form.checked2 === true"
            style="width: 900px;"
          >
            <el-checkbox
              v-for="(item, index) in checkedList"
              :key="index"
              :model="item.checked"
            >{{ item.label }}</el-checkbox>
          </div>
        </template>
      </el-form-item>

      <el-form-item
        label="分销员排名："
        v-if="form.value1 === true"
      >
        <el-switch v-model="form.value3"></el-switch>
        <div class="text">
          开关默认关闭，开启开关，且拥有返利数据的分销员数大于等于3位时分销员中心显示分销员佣金排名。关闭则不显示分销员佣金排名页面。
        </div>
      </el-form-item>

      <el-form-item
        label="返利有效期："
        v-if="form.value1 === true"
      >
        <el-radio-group v-model="form.radio1">
          <el-radio :label="1">
            <el-input
              style="width: 100px;"
              :disabled="form.radio1 === 2"
            ></el-input> 天
          </el-radio>
          <el-radio :label="2">永久</el-radio>
        </el-radio-group>
        <div class="text">用户被分销员邀请注册开始计算，在该天数限制内该用户购买分销商品给分销员计算佣金返利，一旦超过该天数，则不再给分销员佣金返利，默认为空，为空表示不限制。</div>
      </el-form-item>

      <el-form-item
        label="分销员保护期："
        v-if="form.value1 === true"
      >
        <el-radio-group v-model="form.radio2">
          <el-radio :label="1">
            <el-input
              style="width: 100px;"
              :disabled="form.radio2 === 2"
            ></el-input> 天
          </el-radio>
          <el-radio :label="2">永久</el-radio>
        </el-radio-group>
        <div class="text">在保护期内，分销员发展的客户不会变更绑定关系，保护期过后可通过分享链接重新绑定邀请关系。 超过保护期若未重新建立邀请关系，则原绑定关系仍然有效，可依据返利配置条件返利。若保护期设置为0天，则用户可随时通过他人分享进入小程序实现其邀请人的变更。</div>
      </el-form-item>

      <el-form-item
        label="分销中心页面名称："
        v-if="form.value1 === true"
      >
        <el-input
          style="width: 200px"
          v-model="form.pageName"
        ></el-input>
      </el-form-item>

      <el-form-item
        label="推荐商品："
        v-if="form.value1 === true"
      >
        <el-radio-group v-model="form.recommend">
          <el-radio :label="1">不显示</el-radio>
          <el-radio :label="2">默认</el-radio>
          <el-radio :label="3">自定义</el-radio>
        </el-radio-group>

        <div v-if="form.recommend === 3">
          <p>将从已选商品中随机抽取10个展示在小程序端“分销中心”，引导分销员推广商品</p>
          <el-button @click="hanldeToAddGoodS"><i class="el-icon-plus"></i> 选择商品</el-button>
          <el-table
            :data="tableData"
            border
            style="width: 500px;margin-top: 20px;"
          >
            <el-table-column
              label="商品名称"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              label="价格"
              prop="shopPrice"
              align="center"
            ></el-table-column>
            <el-table-column
              label="库存"
              prop="goodsNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              label="操作"
              align="center"
            >
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="mini"
                  @click="deleteTable(scope.$index)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-form-item>

      <el-form-item
        label="自定义内容："
        v-if="form.value1 === true"
      >
        <el-button @click="chooseSelect"><i class="el-icon-plus"></i> 选择模板</el-button>
        <a
          href="javascript:void(0);"
          style="margin: 0 20px;"
        >刷新</a>
        <a href="javascript:void(0);">添加模板</a>
      </el-form-item>

      <!-- 展开更多配置 -->
      <div
        v-if="form.value1 === true"
        @click="handleToChangeArror"
        class="arrorContent"
      >
        <div
          v-if="arrorFlag"
          class="arror"
        >
          {{ $t('seckill.openConfigure') }}&nbsp;<img :src="arrowArr[0].img_1">
        </div>
        <div
          v-if="!arrorFlag"
          class="arror"
        >
          {{ $t('seckill.closeConfigure') }}&nbsp;<img :src="arrowArr[1].img_2">
        </div>
      </div>

      <div v-if="form.value1 === true && arrorFlag">
        <p class="titleContent">返利提现设置</p>
        <el-form-item label="返利提现开关：">
          <el-switch
            v-model="form.value4"
            style="width: 60px;"
          ></el-switch>
          <span style="color: red;">注：开启提现功能开关前，请阅读 <a href="javascript:void(0);">《返利提现配置操作说明》</a></span>
          <div class="text">
            开关开启，分销员推广返利获得的佣金可提现到微信钱包，分销员在小程序发起返利申请，需后台审核通过才可提现到账
          </div>
          <el-radio
            v-model="form.radio3"
            :label="1"
          >小程序</el-radio>
          <p class="text">注意：使用返利提现功能，请确保小程序已开通微信支付，否则不可提现 <a href="javascript:void(0);">去配置</a></p>
          <el-radio
            v-model="form.radio3"
            :label="2"
          >公众号</el-radio>
          <p class="text">注意：使用返利提现功能，请确保小程序已绑定认证服务号并配置相关支付信息，否则不可提现，未关注公众号的用户将会提现失败 <a href="javascript:void(0);">去配置</a></p>
        </el-form-item>

        <el-form-item label="返利最小提现金额：">
          <el-input style="width: 100px;"></el-input> 元
          <div class="text">
            分销员发起返利提现，单次申请最小提现金额。为防止分销员提现过于频繁，请设置单次最小提现金额。
          </div>
        </el-form-item>

        <el-form-item label="返利到账结算时间：">
          <el-radio
            v-model="form.radio4"
            :label="1"
            style="width: 100%;"
          >订单完成即到账</el-radio>
          <el-radio
            v-model="form.radio4"
            :label="2"
            style="width: 100%;"
          >自定义</el-radio>
          <span>每月 <el-input
              :disabled="form.radio4 === 1"
              style="width: 60px;"
            ></el-input> 日00:00:00结算该周期内对应到账金额</span>
        </el-form-item>

        <p class="titleContent">分销中心推广海报背景图</p>

        <el-form-item>
          <div class="leftContent">
            <div
              class="leftTop"
              :style="{ 'backgroundImage' : this.imgData !== '' ? 'url(' + this.imgData + ')' : 'url(' + this.defaultValue +  ')' }"
            >
              <div class="userInfo">
                <div class="leftInfo">
                  <img
                    src="http://mpdevimg2.weipubao.cn/image/admin/user_touxiang.png"
                    alt=""
                  >
                </div>
                <div class="rightInfo">
                  <p>昵称</p>
                  <p> {{ this.name }} </p>
                </div>
              </div>
            </div>
            <div class="leftBottom">
              <span class="codeArea">二维码区域</span>
              <img
                class="codeTips"
                src="http://mpdevimg2.weipubao.cn/image/admin/usr_codes.png"
                alt=""
              >
            </div>
          </div>
          <div class="rightContent">
            <div>
              <span class="rightLabel">邀请文案：</span>
              <el-input
                v-model="name"
                style="width: 300px;"
              ></el-input>
            </div>
            <div style="margin-top: 20px;">
              <span class="rightLabel">海报背景图：</span>
              <div class="defaultBg">
                <span>默认背景选择：</span>
                <el-select
                  v-model="defaultValue"
                  @change="selectChange"
                >
                  <el-option
                    v-for="(item, index) in options"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </div>
              <div class="customBg">
                <span>上传背景图片：</span>
                <div
                  class="custom"
                  @click="handleToCallImgDialog"
                >
                  <img
                    v-if="imgData === ''"
                    src="http://jmpdevimg.weipubao.cn/image/admin/shop_beautify/add_decorete.png"
                    alt=""
                  >
                  <img
                    v-if="imgData !== ''"
                    :src="imgData"
                    alt=""
                    class="customImg"
                  >
                </div>
                <span>图片尺寸640px*640px</span>
              </div>
            </div>

          </div>
        </el-form-item>

      </div>
    </el-form>

    <div class="footer">
      <el-button
        type="primary"
        size="small"
      >保存</el-button>
    </div>

    <!--选择商品弹窗-->
    <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      @resultGoodsDatas="choosingGoodsResult"
      :chooseGoodsBack="goodsInfo"
    />

    <!-- 选择模板弹窗 -->

    <!-- 选择图片弹窗 -->
    <ImageDalog
      pageIndex='imageDalog'
      :tuneUp='tuneUp'
      :isDraggable='isDraggable'
      :imageSize='imageSize'
      @handleSelectImg='handleSelectImg'
    />

  </div>
</template>

<script>
export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'), // 选择商品弹窗
    ImageDalog: () => import('@/components/admin/imageDalog') // 选择图片弹窗
  },
  data () {
    return {
      imageHost: 'http://jmpdevimg.weipubao.cn/',
      form: {
        value1: true, // 分销开关
        value2: true, // 分销员审核开关
        checked1: true,
        checked2: true,
        value3: false, // 分销员排名开关
        radio1: 1, // 返利有效期
        radio2: 1, // 分销员保护期
        pageName: '分销中心', // 分销中心页面名称
        recommend: 3, // 推荐商品
        value4: true, // 返利体现开关
        radio3: 1,
        radio4: 1 // 返利到账结算时间
      },
      // 分销员信息
      checkedList: [{
        label: '真实姓名',
        checked: true
      }, {
        label: '手机号',
        checked: true
      }, {
        label: '身份证号码',
        checked: true
      }, {
        label: '性别',
        checked: true
      }, {
        label: '生日',
        checked: true
      }, {
        label: '婚姻状况',
        checked: true
      }, {
        label: '教育程度',
        checked: true
      }, {
        label: '所在行业',
        checked: true
      }, {
        label: '所在地',
        checked: true
      }, {
        label: '备注',
        checked: true
      }, {
        label: '图片上传',
        checked: true
      }],
      // 推荐商品表格
      tableData: [],
      arrorFlag: true, // 展开更多配置
      // 展开设置箭头
      arrowArr: [{
        img_1: this.$imageHost + '/image/admin/show_more.png'
      }, {
        img_2: this.$imageHost + '/image/admin/hid_some.png'
      }],
      tuneUpChooseGoods: false, // 商品弹窗
      goodsInfo: [], // 商品弹窗回调数据
      name: '分享给你一个好物店铺快来购物吧！', // 邀请文案
      // 默认背景图
      defaultValue: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_1.jpg',
      options: [{
        label: '背景图1',
        value: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_1.jpg'
      }, {
        label: '背景图2',
        value: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_2.jpg'
      }, {
        label: '背景图3',
        value: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_3.jpg'
      }, {
        label: '背景图4',
        value: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_4.jpg'
      }],
      tuneUpSelectLink: false, // 自定义模板
      tuneUp: false, //  调起添加图片弹窗flag
      imageSize: [640, 640], // 调起添加图片宽高
      isDraggable: false, // 添加商品弹窗是否开启多选底部可拖拽状态
      isAddImgOrChangeFlga: false, // true为添加图片  false为更换列表项中的图片
      imgData: '' // 图片的回调函数
    }
  },
  methods: {
    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 显示商品弹窗
    hanldeToAddGoodS () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },

    // 选择商品弹窗回调显示
    choosingGoodsResult (row) {
      console.log('选择商品弹窗回调显示:', row)
      this.tableData = row
      this.goodsInfo = []
      this.tableData.map((item, index) => {
        this.goodsInfo.push(item.goodsId)
      })
    },

    // 删除推荐商品
    deleteTable (index) {
      this.tableData.splice(index, 1)
      this.goodsInfo.splice(index, 1)
    },

    // 调起链接弹窗
    chooseSelect () {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },

    // 显示图片弹窗
    handleToCallImgDialog () {
      this.tuneUp = !this.tuneUp
    },

    // 添加图片弹窗选中图片数据回传
    handleSelectImg (imgData) {
      console.log(imgData)
      this.imgData = imgData.imgUrl
    },

    // 切换背景图
    selectChange (val) {
      this.imgData = val
    }

  }

}

</script>
<style scoped>
a {
  text-decoration: none;
  color: #5a8bff;
}

.footer {
  position: fixed;
  bottom: 0;
  right: 27px;
  width: 87.8%;
  margin: 0 auto;
  height: 50px;
  line-height: 50px;
  background: #fff;
  text-align: center;
  z-index: 99;
}

.text {
  color: #999;
  font-size: 12px;
  line-height: 2;
}

.arrorContent {
  padding: 10px 0 30px 25px;
  width: 150px;
}

.arrorContent .arror {
  color: rgb(90, 139, 255);
  cursor: pointer;
}

.titleContent {
  width: 100%;
  height: 40px;
  line-height: 40px;
  background: #f8f8f8;
  padding-left: 20px;
  margin-bottom: 10px;
}

.leftContent {
  width: 330px;
  border: 1px solid #e2e2e2;
  float: left;
  margin-right: 20px;
}

.leftTop {
  background-color: #ddd;
  font-size: 14px;
  color: #919191;
  position: relative;
  height: 330px;
  /* background-image: url(http://mpdevimg2.weipubao.cn/image/admin/dis_bg_1.jpg); */
  background-size: 100%, 100%;
}

.leftTop .imgTips {
  position: absolute;
  top: 180px;
  width: 100%;
  height: 75px;
  text-align: center;
  float: left;
}

.leftTop .userInfo {
  position: absolute;
  width: 100%;
  top: 0;
  padding: 10px;
  background: rgba(255, 255, 255, 0.5);
  font-size: 13px;
}

.userInfo .leftInfo {
  float: left;
  width: 60px;
  height: 60px;
  margin-right: 10px;
}

.userInfo .rightInfo {
  float: left;
  height: 60px;
  width: 230px;
  line-height: 1.5;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

.leftBottom {
  padding: 10px 0;
  display: flex;
  justify-content: center;
}

.leftBottom .codeArea {
  width: 75px;
  height: 75px;
  background: #f5f5f5;
  display: inline-block;
  float: left;
  margin-right: 10px;
  text-align: center;
  font-size: 12px;
  line-height: 75px;
}

.leftBottom .codeTips {
  width: 75px;
  height: 75px;
  float: left;
}

.rightContent {
  float: left;
  height: 250px;
  padding: 10px;
  box-sizing: border-box;
  border: 1px solid #e2e2e2;
  background: #f8f8f8;
}

.rightContent .rightLabel {
  display: inline-block;
  width: 90px;
}

.rightContent .defaultBg {
  display: inline-block;
}

.rightContent .customBg {
  margin-left: 90px;
  margin-top: 20px;
}

.rightContent .customBg .custom {
  width: 70px;
  height: 70px;
  line-height: 80px;
  display: inline-block;
  border: 1px solid #ccc;
  text-align: center;
}

.rightContent .customBg .custom .customImg {
  width: 100%;
  height: 100%;
}
</style>
