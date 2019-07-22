<template>
  <div class="addBrand">
    <div class="addBrandContent">
      <div class="addBrandMain">
        <ul>
          <li>
            <div class="brand_title">
              <span style="color:red">*</span>
              <span class="nameClass">品牌名称：</span>
              <el-input
                v-model="NameInput"
                placeholder="请输入内容"
                size="mini"
              ></el-input>
            </div>
          </li>
          <li>
            <div class="brand_title">
              <span style="color:red">*</span>
              <span class="nameClass">品牌Logo：</span>
              <span class="addImgClass"></span>
            </div>
          </li>
          <li>
            <div
              class="brand_title"
              style="margin-left:10px"
            >
              <span class="nameClass">品牌分类：</span>
              <el-select
                v-model="classSelectValue"
                placeholder="请选择"
                size="mini"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <div class="classDiv">
                <span>&nbsp;刷新</span> &nbsp;|&nbsp;
                <span @click="handleNewBuild()">新建品牌分类</span>&nbsp;|&nbsp;
                <span @click="handleTurnManClassPage()">管理品牌分类</span>
              </div>
            </div>
          </li>
          <li>
            <div
              class="brand_title"
              style="margin-left:-2px"
            >
              <span class="nameClass">品牌优先级：</span>
              <el-input
                v-model="firstInput"
                placeholder="请输入内容"
                size="mini"
              ></el-input>
              <span style="color:#999;margin-left:3px">请填写正整数，数值越大，优先级越高，在小程序前端展示位置越靠前</span>
            </div>
          </li>
          <li>
            <div
              class="brand_title"
              style="margin-left:-19px"
            >
              <span style="color:red">*</span>
              <span class="nameClass">设为推荐品牌：</span>
              <el-radio
                v-model="radio"
                label="1"
              >是</el-radio>
              <el-radio
                v-model="radio"
                label="2"
              >否</el-radio>
              <span style="color:#999">设为推荐品牌，将展示在小程序推荐品牌中列表中</span>
            </div>
          </li>
          <li>
            <div
              class="brand_title"
              style="margin-left:16px"
            >
              <span class="nameClass">添加商品：</span>
              <div
                class="choiseDivClass"
                @click="handleClickChoiseGood()"
              >
                <img :src="choiseGoodImgUrl">
                选择商品
              </div>
              <span style="color:#5a8bff;margin-left:20px">已选择商品数量：0</span>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <!--保存-->
    <div class="footer">
      <div
        class="save"
        @click="saveShopStyle()"
      >{{$t('shopStyle.saveText')}}</div>
    </div>
    <!--添加品牌分类弹窗-->
    <el-dialog
      title="添加品牌分类"
      :visible.sync="dialogVisible"
      width="30%"
      :center='true'
    >
      <div class="dialogMain">
        <p>品牌分类名称：<el-input
            v-model="brandName"
            placeholder="请输入内容"
            size="mini"
          ></el-input>
        </p>
        <p style="margin-top:10px"><span style="margin-right:11px">分类优先级：</span>
          <el-input
            v-model="classificationName"
            placeholder="请输入内容"
            size="mini"
          ></el-input>
        </p>
        <p>请填写正整数，数值越大，优先级越高，在小程序前端展示位置越靠前</p>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="dialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
    <!--选择商品弹窗-->
    <el-dialog
      title="提示"
      :visible.sync="choiseGooddialogVisible"
      width="70%"
    >
      <div class="choiseDialog">
        <div></div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="choiseGooddialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="choiseGooddialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data () {
    return {
      NameInput: '',
      classSelectValue: '',
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      firstInput: '',
      radio: '1',
      choiseGoodImgUrl: this.$imageHost + '/image/admin/icon_jia.png',
      dialogVisible: false,
      choiseGooddialogVisible: false,
      brandName: '',
      classificationName: ''
    }
  },
  methods: {
    // 新建品牌分类弹窗
    handleNewBuild () {
      this.dialogVisible = true
    },
    // 跳转到品牌分类
    handleTurnManClassPage () {
      let obj = {
        index: 1,
        turnIndex: 1
      }
      this.$emit('turnComponents', obj)
    },
    // 点击添加商品按钮
    handleClickChoiseGood () {
      this.choiseGooddialogVisible = true
    }
  }
}
</script>
<style scoped>
.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: fixed;
  z-index: 2;
  bottom: 0;
  width: 89%;
  padding: 10px 0;
}
.save {
  width: 70px;
  height: 30px;
  line-height: 30px;
  border: none;
  background: #5a8bff;
  color: #fff;
  margin: auto;
  cursor: pointer;
}
.addBrand {
  padding: 10px;
  padding-right: 23px;
  min-width: 1400px;
  font-size: 14px;
  height: 100%;
  position: relative;
}
.addBrandContent {
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
  padding-top: 20px;
  padding-bottom: 20px;
}
.addBrandMain {
  padding-left: 30px;
}
ul {
  padding-left: 30px;
}
ul li {
  line-height: 30px;
}
ul li {
  margin-top: 30px;
}
ul li:nth-of-type(1) {
  margin-top: 0;
}
.brand_title {
  display: flex;
  justify-content: flex-start;
}
.nameClass {
  white-space: nowrap;
  margin: 0 5px;
}
.addImgClass {
  width: 70px;
  height: 70px;
  background: url(../../../../../../assets/adminImg/btn_add.png) no-repeat;
  box-shadow: 0 0 0 #fff;
  padding-top: 40px;
  padding-left: 8px;
  color: #9a9a9a;
  border: none;
  margin-right: 10px;
}
.classDiv > span {
  color: #5a8bff;
  cursor: pointer;
}
.choiseDivClass {
  width: 120px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  color: #5a8bff;
  border: 1px solid #ccc;
  cursor: pointer;
  display: inline-block;
}
.dialogMain p:nth-of-type(3) {
  margin-left: 97px;
  margin-top: 10px;
  line-height: 30px;
  color: #999;
}
</style>
<style>
.addBrand .el-input__inner {
  width: 140px !important;
}
.addBrand .el-input {
  width: auto !important;
}
.addBrand label {
  display: flex;
  align-items: center;
}
.addBrand label:nth-of-type(2) {
  margin-right: 10px !important;
}
.addBrand .el-dialog__header {
  background-color: #f3f3f3 !important;
  text-align: center !important;
}
.addBrand .el-dialog__body {
  padding: 0 !important;
}
</style>
