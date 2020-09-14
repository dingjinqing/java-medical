<template>
  <div class="allDepartment">
    <allDepartmentHeaderTab :tabIndex="isUpdate ? 4 : 2" />
    <div class="content">
      <el-radio-group
        v-model="level"
        style="margin-bottom: 15px; margin-left: 15px;"
      >
        <el-radio
          :label="1"
          :disabled="isUpdate"
        >添加一级科室</el-radio>
        <el-radio
          :label="2"
          :disabled="isUpdate"
        >添加二级科室</el-radio>
      </el-radio-group>
      <!-- 一级科室表单 -->
      <el-form
        v-show="level === 1"
        ref="departmentFirstForm"
        :rules="departmentRules"
        :model="departmentDataFirst"
        label-width="120px"
      >
        <el-form-item
          label="科室名称："
          prop="name"
        >
          <el-input
            ref="nameFirst"
            v-model="departmentDataFirst.name"
            size="small"
            style="width: 170px;"
          />
        </el-form-item>
        <el-form-item
          label="科室代码："
          prop="code"
        >
          <el-input
            ref="codeFirst"
            v-model="departmentDataFirst.code"
            size="small"
            style="width: 170px;"
          />
        </el-form-item>
        <el-form-item
          label="科室优先级："
          prop="first"
        >
          <el-input-number
            controls-position="right"
            :min="0"
            size="small"
            v-model="departmentDataFirst.first"
          ></el-input-number>
        </el-form-item>
      </el-form>
      <!-- 二级科室表单 -->
      <el-form
        v-show="level === 2"
        ref="departmentSecondForm"
        :rules="departmentRules"
        :model="departmentDataSecond"
        label-width="120px"
      >
        <el-form-item
          label="一级科室："
          prop="firstDepartmentId"
        >
          <el-select
            ref="firstDepartmentSelector"
            v-model="departmentDataSecond.firstDepartmentId"
            size="small"
            style="width: 170px;"
          >
            <el-option
              label="请选择"
              :value="null"
            />
            <el-option
              v-for="(item,index) in firstDepartmentOptions"
              :label="item.name"
              :value="item.id"
              :key="index"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="科室代码："
          prop="code"
        >
          <el-input
            ref="codeSecond"
            v-model="departmentDataSecond.code"
            size="small"
            style="width: 170px;"
          />
        </el-form-item>
        <el-form-item
          label="科室名称："
          prop="name"
        >
          <el-input
            ref="nameSecond"
            v-model="departmentDataSecond.name"
            size="small"
            style="width: 170px;"
          />
        </el-form-item>
        <el-form-item
          label="科室优先级："
          prop="first"
        >
          <el-input-number
            controls-position="right"
            :min="0"
            size="small"
            v-model="departmentDataSecond.first"
          ></el-input-number>
        </el-form-item>
      </el-form>
    </div>
    <div class="contentFooter">
      <el-button
        type="primary"
        size="small"
        @click="save"
      >保存</el-button>
    </div>
    <!--图片dialog-->
    <!-- <ImageDialog
      :tuneUp="imgDialogShow"
      pageIndex='pictureSpace'
      :imageSize="[imgWidth,imgHeight]"
      @handleSelectImg='imgDialogSelectedCallback'
    /> -->
    <!--链接dialog-->
    <!-- <LinkDialog
      :tuneUpSelectLink="linkDialogShow"
      @selectLinkPath="imgLinkDialogSelectedCallback"
    /> -->
  </div>
</template>

<script>
// 导入api
import { getBatchDepartmentList, addDepartment, updateDepartment, getDepartment } from '@/api/admin/doctorManage/allDepartment/departmentManagement.js'
// 导入工具
import { isStrBlank } from '@/util/typeUtil'

// 组件导入
import allDepartmentHeaderTab from './officesHeaderTab'
// import ImageDialog from '@/components/admin/imageDalog'
// import LinkDialog from '@/components/admin/selectLinks'

export default {
  name: 'addDepartment',
  components: {
    allDepartmentHeaderTab
  },
  data () {
    return {
      isUpdate: false,
      level: 1,
      departmentDataFirst: {
        parentId: null,
        name: null,
        isLeaf: 1,
        code: null,
        first: 0
      },
      departmentDataSecond: {
        firstDepartmentId: null,
        parentId: null,
        name: null,
        isLeaf: 1,
        code: null,
        first: 0
      },
      departmentRules: {
        name: [
          { required: true, message: '科室名称不可为空', trigger: 'change' }
        ],
        code: [
          { required: true, message: '科室代码不可为空', trigger: 'change' }
        ],
        firstDepartmentId: [
          { required: true, message: '请选择一级科室', trigger: 'change' }
        ]
      },
      firstDepartmentOptions: []
    }
  },
  methods: {

    /* 页面数据初始化 */
    _initData (parentId) {
      return getBatchDepartmentList(parentId).then(res => {
        this.firstDepartmentOptions = res.content
      })
    },
    /* 修改页面初始化数据 */
    _initDataForUpdate (id) {
      getDepartment(id).then(res => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }
        let department = res.content
        let target = department.level === 1 ? this.departmentDataFirst : this.departmentDataSecond
        target.name = department.name
        target.code = department.code
        target.isLeaf = department.isLeaf
        console.log(target.isLeaf)
        this.level = department.level
        target.parentId = department.parentId
        target.oldParentId = department.parentId
        if (department.level === 2) {
          target.firstDepartmentId = department.parentId
        }
      })
    },
    /* 验证数据正确性 */
    _validateFormData () {
      if (this.level === 1) {
        if (isStrBlank(this.departmentDataFirst.name)) {
          this.$message.warning({ message: '科室名称不可为空' })
          this.$refs.nameFirst.focus()
          return false
        }
        if (isStrBlank(this.departmentDataFirst.code)) {
          this.$message.warning({ message: '科室代码不可为空' })
          this.$refs.codeFirst.focus()
          return false
        }
      } else {
        if (this.departmentDataSecond.firstDepartmentId === null) {
          this.$message.warning({ message: '请选择一级科室' })
          this.$refs.firstDepartmentSelector.focus()
          return false
        }

        if (isStrBlank(this.departmentDataSecond.name)) {
          this.$message.warning({ message: '科室名称不可为空' })
          this.$refs.nameSecond.focus()
          return false
        }
        if (isStrBlank(this.departmentDataSecond.code)) {
          this.$message.warning({ message: '科室代码不可为空' })
          this.$refs.codeSecond.focus()
          return false
        }
      }
      return true
    },
    /* 获得表单有效数据 */
    _getFormData () {
      let formData = { type: 0 }
      if (this.level === 1) {
        formData.name = this.departmentDataFirst.name
        formData.code = this.departmentDataFirst.code
        formData.level = this.level
        formData.parentId = 0
        formData.isLeaf = this.departmentDataFirst.isLeaf
        formData.first = this.departmentDataFirst.first
      } else {
        formData.parentId = this.departmentDataSecond.firstDepartmentId
        formData.name = this.departmentDataSecond.name
        formData.code = this.departmentDataSecond.code
        formData.level = this.level
        formData.isLeaf = this.departmentDataSecond.isLeaf
        formData.first = this.departmentDataSecond.first
      }
      return formData
    },
    /* 保存按钮接口调用 */
    save () {
      if (!this._validateFormData()) {
        return
      }
      let formData = this._getFormData()

      let execFun = this.isUpdate ? updateDepartment : addDepartment
      if (this.isUpdate) {
        formData.id = this.$route.params.id
      }
      execFun(formData).then(res => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }
        this.$router.push({ name: 'officesList' })
      })
    }
  },
  mounted () {
    this.langDefault()
    let id = this.$route.params.id ? this.$route.params.id : 0
    if (id !== 0) {
      this.isUpdate = true
      this._initDataForUpdate(id)
    }
    this._initData(0)
  }
}
</script>

<style lang='scss' scoped>
/deep/.fixBug .el-input--small .el-input__inner {
  line-height: 33px;
}
.content {
  margin: 20px 0px;
  margin-bottom: 50px;
}
.inputTip {
  color: #999;
  display: block;
}
.deleteIcon {
  width: 17px;
  height: 17px;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  line-height: 17px;
  text-align: center;
  position: absolute;
  top: -8px;
  right: -8px;
  cursor: pointer;
  opacity: 0.8;
}
.contentFooter {
  position: absolute;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
  border-top: 1px solid #eee;
  z-index: 99;
}
</style>
