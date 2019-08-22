<template>
  <div class="membershipCardDetail">
    <div class="membershipCardDetailMain">
      <div class="leftContainer">
        <div class="left_Top"></div>
        <div class="left_middle">
          <div class="example_card">
            <div class="card_detail">
              <img :src="$imageHost+'/image/admin/img_home/testImg.jpeg'">
            </div>
            <div class="effect_date">
              有效期：-
            </div>
          </div>
          <div
            class="score_power"
            v-for="(item,index) in leftNavData"
            :key="index"
          >
            <div
              class="s_power_title"
              :style="getStyle(item)"
            >{{item.title}}</div>
            <div class="s_power_detail">
              <div
                class="man"
                style="display: block;"
                v-for="(itemC,indexC) in item.children"
                :key="indexC"
              >
                <p>{{itemC}}</p>
              </div>
              <p
                class="every_man"
                style="display: none;"
              ></p>
            </div>
          </div>
        </div>
      </div>
      <div class="rightContainer">
        <div class="rightTile">基础设置</div>
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
          class="demo-ruleForm"
        >
          <el-form-item
            label="会员卡名称："
            prop="name"
            class="userCardName"
          >
            <el-input
              v-model="ruleForm.name"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="背景图："
            class="userCardName"
          >
            <div class="backgroundDiv">
              <div class="bgTop">
                <el-radio
                  v-model="ruleForm.bgFlag"
                  label="1"
                >背景色</el-radio>
                <!--颜色选择器-->
                <colorPicker
                  v-model="colorLeft_"
                  :defaultColor="defaultColorleft"
                  v-on:change="headleChangeColorLeft"
                />
              </div>
              <div class="bgBottom">
                <el-radio
                  v-model="ruleForm.bgFlag"
                  label="2"
                >背景图</el-radio>
                <div
                  class="bgImgDiv"
                  @click="handleToAddImg()"
                  :style="`backgroundImage:url(${$imageHost}/image/admin/add_img.png);backgroundRepeat:no-repeat`"
                >

                </div>
              </div>
            </div>
          </el-form-item>
        </el-form>
      </div>

    </div>
    <div class="footer">
      <div
        class="save"
        @click="handleToSave('ruleForm')"
      >{{$t('shopStyle.saveText')}}</div>
    </div>
    <!--图片弹窗-->
    <ImageDalog
      pageIndex='userCardAdd'
      @handleSelectImg='handleSelectImg'
    />
  </div>
</template>
<script>
import ImageDalog from '@/components/admin/imageDalog'
export default {
  components: {
    ImageDalog
  },
  data () {
    return {
      colorLeft_: '',
      defaultColorleft: '#000',
      leftNavData: [
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/discount.png',
          title: '会员权益(折扣)',
          children: []
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/score_mem.png',
          title: '会员折扣(积分)',
          children: ['购物满100宋100积分']
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/charge_icon.png',
          title: '卡充值规则',
          children: ['仅充值']
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/article.png',
          title: '会员卡使用说明',
          children: []
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/store_icon.png',
          title: '使用门店',
          children: ['全部门店']
        }
      ],
      ruleForm: {
        name: '',
        bgFlag: '1'
      },
      rules: {
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 动态添加样式
    getStyle (item) {
      console.log(item)
      return 'backgroundImage:url(' + item.backGroundImgUrl + ')'
    },
    // 点击保存
    handleToSave (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!')
          this.$refs[formName].resetFields()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 颜色选择器选中
    headleChangeColorLeft () {
      console.log(this.colorLeft_)
    },
    // 添加图片
    handleToAddImg () {
      this.$http.$emit('dtVisible')
    },
    // 图片选中
    handleSelectImg (res) {
      console.log(res)
    }
  }
}
</script>
<style scoped lang="scss">
.membershipCardDetail {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-x: hidden;
  .membershipCardDetailMain {
    position: relative;
    background-color: #fff;
    overflow-x: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    height: 100%;
    display: flex;
    padding-bottom: 50px;
    .leftContainer {
      width: 300px;
      margin-right: 20px;
      height: 594px;
      overflow-y: auto;
      border: 1px solid #ccc;
      background: #f5f5f5;
      .left_Top {
        height: 50px;
        width: 100%;
        background: url(../../../../../../assets/adminImg/mem_card.png)
          no-repeat;
        background-size: 100%;
      }
      .left_middle {
        .example_card {
          width: 90%;
          margin: 10px auto;
          border-radius: 10px;
          background: #e6cb96;
          color: #ffffff;
          padding: 10px 12px;
          .card_detail {
            margin-bottom: 30px;
            img {
              width: 50px;
              height: 50px;
              border-radius: 120px;
              margin-right: 10px;
            }
          }
          .effect_date {
            font-size: 13px;
          }
        }
        .score_power {
          background-color: #fff;
          .s_power_title {
            padding: 10px 0;
            background: url(../../../../../../assets/adminImg/score_mem.png)
              no-repeat;
            background-size: 6%;
            background-position: center left;
            padding-left: 25px;
            font-size: 13px;
            color: #333;
            margin-left: 12px;
          }
          .man {
            color: #878787;
            padding: 0 0 10px;
            padding-left: 25px;
            margin-left: 12px;
            p {
              padding-top: 10px;
              color: #878787;
              padding: 0 0 10px;
            }
          }
        }
      }
    }
    .rightContainer {
      width: 60%;
      background: #f8f8f8;
      border: 1px solid #e4e4e4;
      padding: 10px 1%;
      font-size: 13px;
      margin-bottom: 10px;
      height: 1000px;
      /deep/ .el-form-item__label {
        white-space: nowrap;
        text-align: right;
      }
      .rightTile {
        padding-bottom: 10px;
        border-bottom: 1px solid #ddd;
        margin-bottom: 10px;
      }
      .userCardName {
        padding-left: 100px;
        /deep/ .el-input__inner {
          width: 41%;
        }
        .bgTop {
          height: 40px;
          display: flex;
          justify-content: flex-start;
          align-items: center;
          /deep/ .colorBtn {
            width: 65px;
            height: 30px;
          }
        }
        .bgBottom {
          height: 65px;
          display: flex;
          justify-content: flex-start;
          align-items: center;
          .bgImgDiv {
            width: 65px;
            height: 65px;
            border: 1px solid #ccc;
            background-position: center;
            cursor: pointer;
          }
        }
      }
    }
  }
}
.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: absolute;
  z-index: 2;
  bottom: 0;
  padding: 10px 0;
  left: 0;
  right: 0;
  margin-right: 10px;
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
}
</style>
