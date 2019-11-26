<template>
  <div class="addBrand">
    <div class="addBrandContent">
      <div class="addBrandMain">
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
        >
          <el-form-item
            label="品牌名称："
            prop="name"
          >
            <el-input
              size="small"
              v-model="ruleForm.name"
              placeholder="请输入内容"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="品牌英文名称："
            prop="NameEnlishInput"
          >
            <el-input
              size="small"
              v-model="ruleForm.NameEnlishInput"
              placeholder="请输入内容"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="品牌Logo："
            prop="logoImgUrl"
          >
            <div class="brand_title">
              <span
                @click="handleImgDailog()"
                class="addImgClass"
                v-if="this.ruleForm.logoImgUrl?false:true"
              ></span>
              <img
                v-else
                :src="this.ruleForm.logoImgUrl"
                @click="handleImgDailog()"
                class="addImgClass"
              >
            </div>
          </el-form-item>
          <el-form-item
            label="品牌分类："
            prop="classSelectValue"
          >
            <div
              class="brand_title"
              style="margin-left:10px"
            >
              <el-select
                v-model="ruleForm.classSelectValue"
                placeholder="请选择"
                size="mini"
              >
                <el-option
                  v-for="item in options"
                  :key="item.classifyId"
                  :label="item.classifyName"
                  :value="item.classifyId"
                >
                </el-option>
              </el-select>
              <div class="classDiv">
                <span @click="handleRefresh()">&nbsp;刷新</span> &nbsp;|&nbsp;
                <span @click="handleNewBuild()">新建品牌分类</span>&nbsp;|&nbsp;
                <span @click="handleTurnManClassPage()">管理品牌分类</span>
              </div>
            </div>
          </el-form-item>
          <el-form-item
            label="品牌优先级："
            prop="firstInput"
          >
            <div
              class="brand_title"
              style="margin-left:-2px"
            >
              <el-input
                v-model="ruleForm.firstInput"
                placeholder="请输入内容"
                size="mini"
              ></el-input>
              <span style="color:#999;margin-left:3px">请填写正整数，数值越大，优先级越高，在小程序前端展示位置越靠前</span>
            </div>
          </el-form-item>
          <el-form-item
            label="设为推荐品牌："
            prop="radio"
          >
            <div
              class="brand_title"
              style="margin-left:-19px"
            >
              <el-radio
                v-model="ruleForm.radio"
                label="1"
              >是</el-radio>
              <el-radio
                v-model="ruleForm.radio"
                label="2"
              >否</el-radio>
              <span style="color:#999">设为推荐品牌，将展示在小程序推荐品牌中列表中</span>
            </div>
          </el-form-item>
          <el-form-item label="添加商品：">
            <div
              class="brand_title"
              style="margin-left:16px"
            >
              <div
                class="choiseDivClass"
                @click="handleClickChoiseGood()"
              >
                <img :src="choiseGoodImgUrl">
                选择商品
              </div>
              <span style="color:#5a8bff;margin-left:20px">已选择商品数量：{{selectgoodsNum}}</span>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!--保存-->
    <div class="footer">
      <el-button
        size="small"
        type="primary"
        @click="saveShopStyle()"
      >{{$t('shopStyle.saveText')}}</el-button>
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
          @click="handleAddGrandClass()"
        >确 定</el-button>
      </span>
    </el-dialog>

    <!--选择图片弹窗 -->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp='tuneUp'
      :imageSize=[100,100]
      @handleSelectImg='handleSelectImg'
    />
    <!--选择商品弹窗-->
    <ChoosingGoods
      @result='handleToGetGoods'
      :tuneUpChooseGoods='tuneUpChooseGoods'
    />
  </div>
</template>
<script>
import { brandUpdateGetRequest, queryGoodsIdRequest, classificationSelectRequest, addGrandClassRequest, brandAddRequest } from '@/api/admin/brandManagement.js'
import { mapActions, mapGetters } from 'vuex'
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'),
    ChoosingGoods: () => import('@/components/admin/choosingGoods')
  },
  data () {
    var validatePassLogoImgUrl = (rule, value, callback) => { // 自定义校验品牌Logo
      if (value === '') {
        callback(new Error('请选择品牌Logo'))
      } else {
        callback()
      }
    }
    var validatePassRadio = (rule, value, callback) => { // 自定义校验设为推荐品牌
      if (value === '') {
        callback(new Error('请推荐品牌'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: { // 表单数据
        name: '', // 品牌名称
        NameEnlishInput: '', // 品牌英文名称
        logoImgUrl: '', // 品牌Logo imgUrl
        classSelectValue: '', // 品牌分类
        firstInput: '', // 品牌优先级
        radio: '1', // 设为推荐品牌
        goodsIdsArr: [] // 添加商品id
      },
      rules: { // 校验规则
        name: [
          { required: true, message: '请输入品牌名称名称', trigger: 'blur' }
        ],
        NameEnlishInput: [
          { required: true, message: '请输入品牌名称名称', trigger: 'blur' }
        ],
        logoImgUrl: [
          { required: true, validator: validatePassLogoImgUrl, trigger: 'blur' }
        ],
        radio: [
          { required: true, validator: validatePassRadio, trigger: 'change' }
        ]
      },
      tuneUpChooseGoods: false, // 添加商品弹窗flag
      tuneUp: false, // 调起图片弹窗flag
      pageCount: 1,
      total: null,
      currentPage: 1,
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
      classificationName: '',
      bottomDialogSelectOne: '',
      bottomOptionsOne: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      bottomDialogSelectTwo: '',
      bottomOptionsTwo: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      bottomDialogSelectThree: '',
      bottomOptionsThree: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      inputBottomRangeRight: '',
      goodsName: '',
      goodsNum: '',
      goodsGrandOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      goodsGrandVal: '',
      trList: [
        {
          title: '111',
          path: 'pages/index/index',
          spanId: '',
          ischecked: false,
          children: [
            {
              title: '456',
              path: 'pages/index/index'
            },
            {
              title: 'lalala',
              path: 'pages/index/index'
            }
          ]
        },
        {
          title: '门店列表页',
          ischecked: false,
          path: 'pages/storelist/storelist',
          spanId: ''

        },
        {
          title: '购物车页',
          path: 'pages/cart/cart',
          spanId: '',
          ischecked: false,
          children: [
            {
              title: '789',
              path: 'pages/index/index'
            },
            {
              title: '789123',
              path: 'pages/index/index'
            },
            {
              title: 'aaa',
              path: 'pages/index/index'
            }
          ]
        },
        {
          title: '子页',
          ischecked: false,
          path: 'pages/cart/cart',
          spanId: '',
          children: [
            {
              title: 'zzzzz',
              path: 'pages/index/index'
            }
          ]
        },
        {
          title: '子页',
          ischecked: false,
          path: 'pages/cart/cart',
          spanId: '',
          children: [
            {
              title: 'zzzzz',
              path: 'pages/index/index'
            }
          ]
        },
        {
          title: '子页',
          ischecked: false,
          path: 'pages/cart/cart',
          spanId: '',
          children: [
            {
              title: 'zzzzz',
              path: 'pages/index/index'
            }
          ]
        }

      ],
      isCenterFlag: '',
      tbodyFlag: true,
      tdHiddenImg: this.$imageHost + '/upload/7467397/image/20190507/crop_N7Fu7EaKRtaZri18.gif',
      inputBottomRange: '',
      clickIindex: '',
      checkedAll: false,
      logoImgUrl: '',
      NameEnlishInput: '',
      goodsIdsArr: [],
      selectgoodsNum: 0,
      hxgoodsIds: [],
      saveImgUrl: '',
      oldGoodsIds: [] // 编辑来的旧的商品id集合

    }
  },
  computed: {
    ...mapGetters(['goodsIds', 'editGoodsId'])
  },
  watch: {
    checkedAll (newData, oldData) {
      if (newData === true) {
        this.trList.map((item, index) => {
          item.ischecked = true
        })
      } else {
        this.trList.map((item, index) => {
          item.ischecked = false
        })
      }
    },
    goodsIds_: {
      handler (newData, oldData) {
        this.ruleForm.goodsIdsArr = [...new Set(newData)]
        this.selectgoodsNum = this.ruleForm.goodsIdsArr.length
        console.log(this.ruleForm.goodsIdsArr)
      },
      immediate: true
    }
  },
  mounted () {
    // 传递crumbsTitle
    let arr = ['商品管理', '品牌管理', '添加品牌']
    this.changeCrumbstitle(arr)

    // 品牌分类初始化获取及页编辑回显
    this.defaultGrandClass()
  },
  methods: {
    ...mapActions(['changeCrumbstitle', 'transmitGoodsIds']),
    defaultGrandClass () {
      console.log(this.editGoodsId)
      // 品牌分类下拉框数据获取
      classificationSelectRequest().then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.options = res.content
        }
      })
      // 是否是编辑判断
      if (this.editGoodsId !== 'add') {
        if (!this.editGoodsId) return
        queryGoodsIdRequest(this.editGoodsId).then((res) => {
          console.log(res)
          this.ruleForm.name = res.content.brandName
          this.ruleForm.NameEnlishInput = res.content.ename
          this.ruleForm.logoImgUrl = res.content.fullUrlLogo

          this.ruleForm.classSelectValue = res.content.classifyId
          this.ruleForm.firstInput = res.content.first
          console.log(res.content.isRecommend)
          this.ruleForm.radio = res.content.isRecommend.toString()
          this.ruleForm.goodsIdsArr = res.content.goodsIds
          this.hxgoodsIds = res.content.goodsIds
          this.selectgoodsNum = res.content.goodsIds.length
          this.oldGoodsIds = res.content.goodsIds // 将旧的的商品id集合保存
          console.log(res.content.fullUrlLogo.split('cn')[1])
          let imgUrl = res.content.fullUrlLogo.split('cn')[1].substr(1)
          console.log(imgUrl)
          this.saveImgUrl = imgUrl
        })
      }
    },
    // 新建品牌分类弹窗
    handleNewBuild () {
      this.dialogVisible = true
    },
    // 添加品牌分类
    handleAddGrandClass () {
      let obj = {
        'classifyName': this.brandName,
        'first': this.classificationName
      }
      addGrandClassRequest(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success({
            message: '添加品牌分类成功',
            type: 'success'
          })
          this.defaultGrandClass()
        }
        console.log(res)
      }).catch((res) => {
        console.log(res)
        this.$message.success({
          message: '添加品牌失败',
          type: 'success'
        })
      })

      this.dialogVisible = false
    },
    // 跳转到品牌分类
    handleTurnManClassPage () {
      this.$router.push({
        name: 'brand',
        params: {
          toSecond: true
        }
      })
    },
    // 页码改变
    handleCurrentChange () {
      this.defaultGrandClass()
    },
    // 点击选择商品按钮
    handleClickChoiseGood () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    handleToGetGoods (data) { // 商品弹窗选中数据回传函数
      console.log(data)
      this.ruleForm.goodsIdsArr = data
      this.selectgoodsNum = data.length
    },
    // 选择商品弹窗确定
    handleChoiseGooddialog () {
      this.transmitGoodsIds(this.ruleForm.goodsIdsArr)
      this.choiseGooddialogVisible = false
    },
    // 调用图片弹窗
    handleImgDailog () {
      this.tuneUp = !this.tuneUp
    },
    // 图片弹窗选中
    handleSelectImg (res) {
      console.log(res, this.saveImgUrl)
      this.saveImgUrl = res.imgPath
      this.ruleForm.logoImgUrl = res.imgUrl
    },
    // 刷新
    handleRefresh () {
      // 品牌分类初始化获取
      this.defaultGrandClass()
    },
    // 保存
    saveShopStyle () {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          if (!(this.ruleForm.firstInput >= 1 && this.ruleForm.firstInput <= 100)) {
            this.$message.error({
              message: '品牌优先级必须在1~100之间',
              type: 'success'
            })
          } else {
            console.log(this.saveImgUrl)
            console.log(this.editGoodsId)
            if (this.editGoodsId !== 'add') {
              let obj = {
                'id': this.editGoodsId,
                'brandName': this.ruleForm.name,
                'logo': this.saveImgUrl,
                'first': this.ruleForm.firstInput,
                'classifyId': this.ruleForm.classSelectValue,
                'isRecommend': this.ruleForm.radio,
                'goodsIds': this.ruleForm.goodsIdsArr,
                'oldGoodsIds': this.oldGoodsIds,
                'ename': this.ruleForm.NameEnlishInput
              }
              brandUpdateGetRequest(obj).then((res) => {
                console.log(res)
                if (res.error === 0) {
                  this.$message.success({
                    message: '保存成功',
                    type: 'success'
                  })
                } else if (res.error === 131001) {
                  this.$message.error({
                    message: '品牌名称已存在',
                    type: 'success'
                  })
                }
              })
            } else {
              let obj = {
                'brandName': this.ruleForm.name,
                'ename': this.ruleForm.NameEnlishInput,
                'logo': this.saveImgUrl,
                'first': this.ruleForm.firstInput,
                'isRecommend': this.ruleForm.radio,
                'classifyId': this.ruleForm.classSelectValue,
                'goodsIds': this.ruleForm.goodsIdsArr
              }
              brandAddRequest(obj).then((res) => {
                console.log(res)
                if (res.error === 0) {
                  this.$message.success({
                    message: '保存成功',
                    type: 'success'
                  })
                } else if (res.error === 131001) {
                  this.$message.error({
                    message: '品牌名称已存在',
                    type: 'success'
                  })
                }
              })
            }
          }
        }
      })
    }
  }
}
</script>
<style scoped>
.footer {
  position: absolute;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px;
  background-color: #fff;
  text-align: center;
  border-top: 1px solid #eee;
  z-index: 99;
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
}
.addBrandContent {
  background: #fff;
  padding: 10px;
  margin-bottom: 50px;
}
.addBrandMain {
  padding-left: 30px;
}
ul {
  padding-left: 30px;
}
ul li {
  line-height: 30px;
  display: flex;
  white-space: nowrap;
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
.dialogMain {
  margin: 30px 30px 0 30px;
}
.dialogMain p:nth-of-type(3) {
  margin-left: 97px;
  margin-top: 10px;
  line-height: 30px;
  color: #999;
}
.choiseDialog {
  overflow-y: auto;
}
.choiseDialog ul {
  display: flex;
  margin-top: 10px;
}
.choiseDialog ul li {
  margin-top: 0;
}
.choiseDialog ul li:nth-of-type(2) {
  margin: 0 10px;
}
.choiseDialog ul li:nth-of-type(3) {
  margin-right: 10px;
}
.middleBbtnDiv {
  padding: 10px 30px;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
  width: 100%;
}
thead {
  display: table-header-group;
  vertical-align: middle;
  border-color: inherit;
}
thead td {
  background: #faf9f8;
  text-align: center;
  color: #333;
  padding: 8px 10px;
  vertical-align: middle !important;
}

thead td:nth-of-type(1) {
  width: 105px;
  clear: both;
  overflow: hidden;
  /* display: flex;
  align-items: center; */
}
thead td:nth-of-type(1) .tdTopText {
  float: left;
  margin-left: 3px;
}
thead td:nth-of-type(2) {
  width: 276px;
}
thead td:nth-of-type(3) {
  width: 104px;
}
thead td:nth-of-type(4) {
  width: 71px;
}
thead td:nth-of-type(5) {
  width: 65px;
}
thead td:nth-of-type(6) {
  width: 80px;
}
thead td:nth-of-type(7) {
  width: 106px;
}
thead td:nth-of-type(8) {
  width: 127px;
}
tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 7px;
  vertical-align: middle !important;
  text-align: center;
}
img {
  margin-left: 10px;
}
.isLeft {
  text-align: left;
}
.isLeft img {
  width: 40px;
}
.isLeft span {
  display: inline-block;
  vertical-align: top;
  margin-top: 9px;
  margin-left: 5px;
}
.tdCenter {
  text-align: center;
}
.noData {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* width: 650px; */
  flex-direction: column;
  border: 1px solid #eee;
  margin-top: 10px;
}
.noData span {
  margin: 10px;
}
.clickClass {
  background-color: #eee !important;
}
.table_container {
  padding: 10px 30px;
  overflow-y: auto;
  height: 300px;
}
.tdCenter {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
}
.level_1 {
  margin-left: 10px;
}
.level_2 {
  margin-left: 15px;
}
</style>
<style>
/* .addBrand .el-input {
  width: auto !important;
} */
.table_container label {
  display: flex;
  align-items: center;
}
.table_container label:nth-of-type(2) {
  margin-right: 10px !important;
}
.addBrand .el-dialog__header {
  background-color: #f3f3f3 !important;
  text-align: center !important;
}
.addBrand .el-dialog__body {
  padding: 0 !important;
}
.table_container .el-checkbox {
  width: 14px !important;
  float: left !important;
  margin-right: 0 !important;
}
.specialDialog .el-input__inner {
  width: 40px !important;
}
.choiseDialog .el-input__inner {
  width: 140px !important;
}
.choiseDialog .rangeLi .el-input__inner {
  width: 70px !important;
}
.brand_title .el-input__inner {
  width: 140px !important;
}
.brand_title .el-radio {
  display: flex;
  align-items: center;
}
.el-select-dropdown__item {
  margin-top: 0 !important;
}
</style>
<style lang="scss" scoped>
.tablefooter {
  background-color: #fff;
  height: 50px;
  line-height: 50px;
  color: #333;
  font-size: 14px;
  display: flex;
  justify-content: flex-end;
  padding-right: 10px;
  /deep/ .el-pagination {
    display: flex;
    align-items: center;
    .el-pager {
      display: flex;
      align-items: center;
    }
  }
}
.addBrandMain {
  /deep/ .el-form-item__label {
    width: 122px;
  }
  /deep/ .el-input {
    width: 140px;
  }
}
.dialogMain {
  /deep/ .el-input {
    width: 150px;
  }
}
</style>
