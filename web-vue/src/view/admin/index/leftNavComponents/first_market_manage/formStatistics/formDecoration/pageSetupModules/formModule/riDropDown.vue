<template>
  <div class="riName">
    <div class="riNameMain">
      <!--模块私有区域-->
      <div class="main">
        <div class="list">
          <span>标题文字：</span>
          <el-input
            v-model="modulesSaveData.form_title"
            size="small"
          ></el-input>
        </div>
        <div class="list">
          <span></span>
          <div class="tips">最多可输入20个字</div>
        </div>
        <div
          class="list"
          style="margin-bottom:0px"
        >
          <span class="opTitle">选项设置：</span>
          <div class="options">
            <div
              class="optionsList"
              v-for="(value,key,index) in modulesSaveData.selects"
              :key="index"
            >
              <div class="title">选项</div>
              <el-input
                v-model="modulesSaveData.selects[key]"
                size="small"
              ></el-input>
              <div
                class="title delete"
                v-if="index>0"
                @click="handleToDElete(key)"
              >删除</div>
            </div>

          </div>
        </div>
        <div class="list">
          <span></span>
          <div
            class="add_opn"
            @click="handleToAddOption()"
          >
            <img :src="$imageHost+'/image/admin/icon_jia.png'">
            添加选项
          </div>
        </div>
        <div class="list">
          <span>条件验证：</span>
          <el-checkbox v-model="modulesSaveData.confirm">必填</el-checkbox>
        </div>
        <!--模块私有end-->
        <div class="sure">
          <el-button
            type="primary"
            size="small"
            @click="handleToClickSure()"
          >确定</el-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
export default {
  mixins: [decMixins],
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      imageTuneUp: false, // 图片选择弹窗调起
      modulesSaveData: {
        'form_title': '下拉',
        'selects': {
          '1': '选项1',
          '2': '选项2'
        },
        'confirm': 0,
        'ok_ajax': 0
      } // 模块保存数据
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        if (this.modulesData !== -1) {
          this.modulesSaveData = this.modulesData
        }
      },
      immediate: true
    },
    // 监听数据变换
    modulesSaveData: { // 模块公共
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  methods: {
    // 点击确定按钮
    handleToClickSure () {
      this.modulesSaveData.ok_ajax = 1
      this.$message.success({
        message: '模块保存成功',
        showClose: true
      })
    },
    // 点击添加选项
    handleToAddOption () {
      let key = Object.keys(this.modulesSaveData.selects).map(item => {
        return Number(item)
      })
      console.log(key)
      let newKey = Math.max(...key) + 1
      this.$set(this.modulesSaveData.selects, newKey, `选项${newKey}`)
      console.log(this.modulesSaveData.selects)
    },
    // 点击删除
    handleToDElete (key) {
      this.$delete(this.modulesSaveData.selects, key)
      console.log(key, this.modulesSaveData)
    }
  }
}
</script>
<style lang="scss" scoped>
.riName {
  .riNameMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 20px 2%;
    .list {
      margin-bottom: 20px;
      .opTitle {
        display: flex;
        align-items: flex-start;
        padding-top: 8px;
      }
      span {
        display: inline-block;
        width: 100px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
      }
      display: flex;
      /deep/ .el-input {
        width: 220px;
      }
      .tips {
        color: #a7a7a7;
        font-size: 12px;
      }
      .iconContainer {
        background-size: 45% !important;
        background-position: center !important;
        width: 70px;
        height: 70px;
        border: 1px solid #e5e5e5;
        position: relative;
        .click_to_change {
          position: absolute;
          cursor: pointer;
          bottom: 0;
          width: 100%;
          text-align: center;
          color: #fff;
          background: rgba(0, 0, 0, 0.5);
          padding: 3px 0;
          font-size: 11px;
        }
        img {
          width: 100%;
        }
      }
      .iconTips {
        color: #a7a7a7;
        font-size: 12px;
        margin-top: 20px;
      }
      .iconSpan {
        align-items: flex-start;
      }
      .add_opn {
        margin-left: 0px;
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5a8bff;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
      }
      .options {
        display: flex;
        flex-direction: column;
        .optionsList {
          display: flex;
          margin-bottom: 10px;
          /deep/ .el-input {
            width: 180px;
          }
          .title {
            display: flex;
            align-items: center;
            margin: 0 5px;
          }
          .delete {
            cursor: pointer;
          }
        }
      }
    }
    .sure {
      display: flex;
      justify-content: center;
    }
    //end
  }
}
</style>
