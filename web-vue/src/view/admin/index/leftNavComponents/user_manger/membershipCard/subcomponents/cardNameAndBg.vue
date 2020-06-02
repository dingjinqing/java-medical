<template>
  <div class="cardNameAndBg">
    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      @submit.native.prevent
    >
      <el-form-item
        :label="$t('memberCard.memberCardName')"
        prop="cardName"
        class="userCardName first"
      >
        <el-input
          v-model="ruleForm.cardName"
          size="small"
         @change="checkoutName"
        >
        </el-input>
      </el-form-item>

      <el-form-item
        :label="$t('memberCard.backImg')"
        class="cardBg"
      >
        <div class="backgroundDiv">
          <div class="bgTop">
            <el-radio
              v-model="ruleForm.bgType"
              label="0"
              style="margin-right: 26px;"
            >{{ $t('memberCard.bgColor') }}</el-radio>
             <el-color-picker v-model="ruleForm.bgColor"></el-color-picker>
          </div>
          <div class="bgBottom">
            <el-radio
              v-model="ruleForm.bgType"
              label="1"
            >{{ $t('memberCard.backImg') }}</el-radio>
            <img
              :src="getBgImg()"
              class="bgImgDiv"
              @click="handleToAddImg()"
              v-bind:style="getDefaultBgImg()"
            />
          </div>
        </div>

      </el-form-item>
    </el-form>
    <!--图片弹窗-->
    <ImageDalog
      pageIndex='userCardAdd'
      :tuneUp="imgDisable"
      :imageSize="[540, 300]"
      @handleSelectImg='handleSelectImg'
    />
  </div>
</template>
<script>
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog')
  },
  props: {
    val: {
      type: Object,
      required: true,
      default: () => {
        return {
          cardName: '',
          bgType: '0',
          bgColor: '',
          bgImg: ''
        }
      }
    }
  },
  computed: {
    ruleForm: {
      get () {
        return this.val
      },
      set (val) {
        this.$emit('input', this.ruleForm)
      }

    }
  },
  watch: {
    'ruleForm.cardName': {
      handler (newName, oldName) {
        this.val.cardName = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.bgType': {
      handler (newName, oldName) {
        console.log('ruleForm.bgType')
        this.val.bgType = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.bgColor': {
      handler (newName, oldName) {
        this.val.bgColor = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.bgImg': {
      handler (newName, oldName) {
        this.val.bgImg = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.valid': {
      handler (newName, oldName) {
        this.val.valid = newName
        this.ruleForm = this.val
      },
      immediate: true
    }
  },
  mounted () {
    this.langDefault()
    this.$on('checkRule', () => {
      this.$refs.ruleForm.validate((valid) => {
        console.log(valid)
        this.ruleForm.valid = valid
        if (!valid) {
          this.$message.warning(this.$t('memberCard.inputCardName'))
        }
      })
    })
  },
  data () {
    var validateCardName = (rule, value, callback) => {
      if (this.isBlank(value)) {
        callback(new Error(this.$t('memberCard.inputCardName')))
      } else {
        callback()
      }
    }
    return {
      imgDisable: false,
      bgImgPath: '',
      defaultBgColor: '#ffffff',
      rules: {
        cardName: [
          { required: true, validator: validateCardName, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    isBlank (str) {
      return (!str || /^\s*$/.test(str))
    },
    // 添加图片
    handleToAddImg () {
      this.imgDisable = !this.imgDisable
      this.$http.$emit('dtVisible')
    },
    // 图片选中
    handleSelectImg (res) {
      // this.ruleForm.bgImg = res.imgPath
      this.ruleForm.bgImg = this.$imageHost + '/' + res.imgPath
      console.log(this.ruleForm.bgImg)
    },
    getBgImg () {
      if (this.ruleForm.bgImg) {
        return this.ruleForm.bgImg
      }
    },
    getDefaultBgImg () {
      return 'backgroundImage:url(' + this.$imageHost + '/image/admin/add_img.png);backgroundRepeat:no-repeat'
    },
    checkoutName () {
      this.$refs.ruleForm.validateField('cardName')
    }
  }
}
</script>
<style scoped lang="scss">

/deep/ .el-form-item__label,
/deep/ .el-radio__label{
  font-size: 13px;
}
/deep/ .el-icon-close:before{
  content: ""
}
/deep/ .el-icon-arrow-down:before {
    content: "";
}
/deep/ .el-color-picker__trigger{
  border: 0px solid #e6e6e6;
  height: 35px;
  width: 70px;
}

.cardNameAndBg {
  .userCardName {
    padding-left: 75px;
    /deep/ .el-input {
      width: 41%;
      .el-input__inner {
        width: 100%;
      }
    }
  }
  .first {
    /deep/ .el-form-item__label {
      margin-left: -8px;
    }
  }
  .cardBg {
    /deep/ .el-form-item__label {
      margin-left: -8px;
    }
    padding-left: 75px;
    .backgroundDiv {
      .bgTop {
        height: 40px;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        /deep/ .colorBtn {
          width: 65px;
          height: 30px;
          border: 1px solid #ccc;
        }
        /deep/ .open {
          z-index: 100;
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
</style>
