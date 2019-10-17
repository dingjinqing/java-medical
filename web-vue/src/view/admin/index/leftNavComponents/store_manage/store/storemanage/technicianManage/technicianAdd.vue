<template>
  <div>
    <div class="technician_add_page">
      <div class="technician_add_content">
        <div class="technician_add_info">
          <el-form
            ref="technicianForm"
            :model="form"
            :rules="rules"
            label-width="180px"
          >
            <el-form-item
              label="技师名称："
              prop="technicianName"
            >
              <el-input
                size="small"
                class="middle_input"
                v-model="form.technicianName"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="手机号码："
              prop="technicianMobile"
            >
              <el-input
                type="tel"
                size="small"
                class="middle_input"
                v-model="form.technicianMobile"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="头像："
              prop="bgImgPath"
            >
              <div
                class="bg_img"
                @click="selectAvator"
              >
                <div
                  v-if="!form.bgImgPath"
                  style="width:100%; height:100%;"
                >
                  <el-image
                    style="width: 100%; height:100%;"
                    fit="contain"
                    :src="$imageHost + '/image/admin/tech_moren.png'"
                  ></el-image>
                  <p>更改</p>
                </div>
                <div
                  v-else
                  style="width:100%;height:100%;"
                >
                  <el-image
                    style="width: 100%; height:100%;"
                    fit="fill"
                    :src="form.bgImgPath"
                  ></el-image>
                </div>
              </div>
            </el-form-item>
            <el-form-item
              label="介绍："
              class="big_input"
              prop="technicianIntroduce"
            >
              <el-input
                size="small"
                class="big_input"
                placeholder="请用一句话介绍技师"
                v-model="form.technicianIntroduce"
              ></el-input>
            </el-form-item>
            <el-form-item label="所属分组：">
              <el-select
                v-model="form.groupId"
                size="small"
              >
                <el-option
                  label="请选择所属分组"
                  :value="null"
                ></el-option>
                <el-option
                  v-for="item in technicianGroup"
                  :key="item.groupId"
                  :label="item.groupName"
                  :value="item.groupId"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="服务项目：">
              <el-radio-group v-model="form.serviceType">
                <el-radio :label="0">全部项目</el-radio>
                <el-radio :label="1">部分项目</el-radio>
              </el-radio-group>
              <div
                v-show="form.serviceType === 1"
                class="table-wrap"
              >
                <el-table
                  ref="serviceTable"
                  style="width: 100%;"
                  max-height="200"
                  border
                  :data="serviceList"
                  @row-click="rowClickHandle"
                  @selection-change="selectChangeHandle"
                >
                  <el-table-column
                    type="selection"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    label="服务项目"
                    prop="serviceName"
                  ></el-table-column>
                </el-table>
              </div>
            </el-form-item>
            <el-form-item
              label="备注："
              prop="remarks"
            >
              <el-input
                class="big_input"
                v-model="form.remarks"
                type="textarea"
                placeholder="备注不能超过200字"
                :maxlength="200"
              ></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="step_2">
          <div class="technician_detail_preview"></div>
          <div class="technician_detail_edit"></div>
        </div>
      </div>
      <ImageDialog
        :tuneUp="tuneUp"
        pageIndex='pictureSpace'
        @handleSelectImg="handleSelectImg"
      ></ImageDialog>
      <div class="footer">
        <el-button
          size="small"
          type="primary"
          class="footer-btn"
          @click="saveTechnicianInfo"
        >保存</el-button>
        <el-button
          size="small"
          class="footer-btn"
        >取消</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { addTechnicianApi, getTechnicianGroup, getTechnician, getServiceList, updateTechnicianApi } from '@/api/admin/storeManage/storemanage/technicianManage'
import ImageDialog from '@/components/admin/imageDalog'
export default {
  components: { ImageDialog },
  data () {
    return {
      technicianGroup: [], // 技师分组下拉数据
      tuneUp: false, // 是否打开图片选择组件
      serviceList: [], // 服务列表
      selects: [], // 选中的服务
      form: {
        id: '',
        storeId: '',
        technicianName: '',
        technicianMobile: '',
        bgImgPath: '',
        technicianIntroduce: '',
        groupId: null,
        serviceType: 0,
        serviceList: [],
        remarks: ''
      },
      rules: {
        technicianName: [
          { required: true, message: '请输入技师名称', trigger: 'blur' }
        ],
        technicianMobile: [
          { required: true, message: '请输入技师的电话号码', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.form.storeId = Number(this.$route.query.id)
    this.initGroupData()
    this.initServiceData()
    if (this.$route.query.technicianId) {
      this.initForm()
    }
  },
  methods: {
    initGroupData () {
      let params = { storeId: this.form.storeId }
      getTechnicianGroup(params).then(res => {
        if (res.error === 0) {
          this.technicianGroup = res.content
        }
      })
    },
    initServiceData () {
      let params = {
        storeId: this.form.storeId
      }
      getServiceList(params).then(res => {
        if (res.error === 0) {
          this.serviceList = res.content.dataList
        }
      })
    },
    initForm () {
      this.form.id = this.$route.query.technicianId
      let params = {
        id: this.form.id
      }
      getTechnician(params).then(res => {
        if (res.error === 0) {
          let content = res.content
          for (const key in content) {
            if (this.form.hasOwnProperty(key) && key !== 'serviceList') {
              this.form[key] = content[key]
            }
          }
          this.form.groupId = content.seviceGroup.groupId
          content.serviceList.forEach((item, i) => {
            let row = this.serviceList.find(data => data.id === item.id)
            this.$refs.serviceTable.toggleRowSelection(row)
          })
        }
      })
    },
    selectAvator () {
      this.tuneUp = !this.tuneUp
    },
    rowClickHandle (row) {
      this.$refs.serviceTable.toggleRowSelection(row)
    },
    selectChangeHandle (selects) {
      this.selects = selects
    },
    handleSelectImg (imgObj) {
      this.form.bgImgPath = imgObj.imgUrl
    },
    saveTechnicianInfo () {
      this.form.serviceList = this.selects.map(function (item, index) {
        return item.id
      })
      this.$refs.technicianForm.validate((valid) => {
        if (valid) {
          let params = Object.assign({}, this.form)
          if (!this.$route.query.technicianId) {
            addTechnicianApi(params).then(res => {
              if (res.error === 0) {
                this.$message.success('保存成功')
              }
            })
          } else {
            params.id = this.$route.query.technicianId
            updateTechnicianApi(params).then(res => {
              if (res.error === 0) {
                this.$message.success('更新成功')
              }
            })
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.technician_add_page {
  position: relative;
  width: 100%;
  .technician_add_content {
    margin: 0 25px 50px;
    .technician_add_info {
      input {
        padding-left: 20px;
      }
      .big_input {
        width: 400px;
      }
      .middle_input {
        width: 250px;
      }
      .bg_img {
        position: relative;
        width: 65px;
        height: 65px;
        float: left;
        border: 1px solid #ccc;
        margin-left: 5px;
        background-color: #d2d2d2;
        p {
          position: absolute;
          bottom: 0;
          left: 0;
          width: 100%;
          line-height: 1;
          background: rgba(0, 0, 0, 0.4);
          color: #fff;
          font-size: 12px;
          text-align: center;
        }
      }
      .table-wrap {
        width: 400px;
      }
    }
  }
  .footer {
    position: fixed;
    bottom: 0;
    display: flex;
    justify-content: center;
    width: calc(100% - 186px);
    padding: 10px;
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    .footer-btn {
      width: 105px;
      margin: 0 10px;
    }
  }
}
</style>
