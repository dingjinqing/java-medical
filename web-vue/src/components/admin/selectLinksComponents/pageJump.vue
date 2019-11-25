<template>
  <div>
    <div class="pageJump_container">
      <div class="top_left">
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
          class="demo-ruleForm"
        >
          <el-form-item
            label="链接名称："
            prop="pageName"
          >
            <el-input
              v-model="ruleForm.pageName"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="页面分类："
            prop="pageClassify"
          >
            <el-input
              v-model="ruleForm.pageClassify"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              size="mini"
              @click="handleToSaveNewPath()"
            >保存</el-button>
          </el-form-item>
        </el-form>

      </div>

    </div>
    <div class="alerm">注意：由于微信限制，目前仅支持小程序关联的公众号文章链接</div>
    <div class="content">
      <table width='100%'>
        <thead>
          <tr>
            <td>名称</td>
            <td>链接</td>
            <td>操作</td>
          </tr>
        </thead>
        <tbody v-if="tbodyFlag">
          <tr
            v-for="(item,index) in trList"
            :key="index"
            :class="clickIindex===index?'clickClass':''"
            @click="handleClick(index)"
          >
            <td>{{item.pathName}}</td>
            <td class="link">{{item.linkPath}}</td>
            <td
              class="tb_decorate_a"
              @click="deleRr(index)"
            >
              删除
            </td>
          </tr>
        </tbody>

      </table>
      <div
        class="noData"
        v-if="!tbodyFlag"
      >
        <img :src="noImg">
        <span>暂无相关数据</span>
      </div>
    </div>
  </div>
</template>
<script>
import { webListApi, webSaveApi } from '@/api/admin/selectLinksApi/selectLinksApi'
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      pageName: '',
      pagePath: '',
      tbodyFlag: true,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      trList: [],
      clickIindex: null,
      pageClassify: '',
      ruleForm: {
        pageName: '',
        pageClassify: ''
      },
      rules: {
        pageName: [{ required: true, message: '请输入链接名称', trigger: 'blur' }],
        pageClassify: [{ required: true, message: '请输入要跳转的网页链接', trigger: 'blur' }]
      }
    }
  },
  mounted () {
    // 初始化数据
    this.handleToInitData()
  },
  methods: {
    ...mapActions(['choisePagePath']),
    // 初始化数据
    handleToInitData () {
      webListApi().then(res => {
        console.log(res)
        if (res.error === 0) {
          if (!res.content.length) {
            this.tbodyFlag = false
          } else {
            this.tbodyFlag = true
          }
          this.trList = res.content
        }
      })
    },
    // 行选中高亮
    handleClick (index) {
      this.clickIindex = index
      this.choisePagePath(this.trList[index].path)
    },
    // 保存
    handleToSaveNewPath () {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          let params = {
            pathName: this.ruleForm.pageName,
            linkPath: this.ruleForm.pageClassify
          }
          webSaveApi(params).then(res => {
            console.log(res)
            if (res.error === 170010) {
              this.$message.error({
                message: res.message
              })
            } else if (res.error) {
              this.$message.success({
                message: res.message
              })
              this.handleToInitData()
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
      console.log(1)
    },
    // 删除
    deleRr (index) {
      console.log(index)
      this.trList.splice(index, 1)
    }
  }
}
</script>
<style scoped>
.tb_decorate_a {
  color: #0000ee;
  cursor: pointer;
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
.pageJump_container {
  display: flex;
  justify-content: space-around;
  padding-bottom: 10px;
  margin: 10px 0 0 10px;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
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
  width: 120px;
}
thead td:nth-of-type(2) {
  width: 460px;
}
tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 10px;
  vertical-align: middle !important;
  text-align: center;
}
.top_left {
  display: flex;
  align-items: center;
  /* margin-left: 7px; */
}
.top_middle {
  display: flex;
  align-items: center;
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
.alerm {
  margin-bottom: 10px;
  padding-left: 28px;
  margin-top: 10px;
  font-size: 14px;
  color: red;
}
.demo-ruleForm {
  display: flex;
  justify-content: space-around;
}
</style>
<style>
.pageJump_container .el-input {
  width: 170px !important;
}
</style>
