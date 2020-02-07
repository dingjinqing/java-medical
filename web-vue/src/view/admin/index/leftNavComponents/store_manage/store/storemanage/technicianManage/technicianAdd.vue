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
              :label="technicianName+'：'"
              prop="technicianName"
              :rules="[
                { required: true, message: nameValid, trigger: 'blur' }
              ]"
            >
              <el-input
                size="small"
                class="middle_input"
                v-model="form.technicianName"
              ></el-input>
            </el-form-item>
            <el-form-item
              :label="$t('technicianAdd.cellphone')+'：'"
              prop="technicianMobile"
              :rules="[{ required: true, message: phoneValid, trigger: 'blur' }]"
            >
              <el-input
                type="tel"
                size="small"
                class="middle_input"
                v-model="form.technicianMobile"
              ></el-input>
            </el-form-item>
            <el-form-item
              :label="$t('technicianAdd.avatar')+'：'"
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
                  <p>{{$t('technicianAdd.change')}}</p>
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
              :label="$t('technicianAdd.intro')+'：'"
              class="big_input"
              prop="technicianIntroduce"
            >
              <el-input
                size="small"
                class="big_input"
                :placeholder="introTips"
                v-model="form.technicianIntroduce"
              ></el-input>
            </el-form-item>
            <el-form-item :label="$t('technicianAdd.ownedGroup')+'：'">
              <el-select
                v-model="form.groupId"
                size="small"
              >
                <el-option
                  :label="$t('technicianAdd.ownedGroupTips')+'：'"
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
            <el-form-item :label="$t('technicianAdd.serviceName')+'：'">
              <el-radio-group v-model="form.serviceType">
                <el-radio :label="0">{{$t('technicianAdd.allProjects')}}</el-radio>
                <el-radio :label="1">{{$t('technicianAdd.partOfProjects')}}</el-radio>
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
                    :label="$t('technicianAdd.serviceName')"
                    prop="serviceName"
                  ></el-table-column>
                </el-table>
              </div>
            </el-form-item>
            <el-form-item
              :label="$t('technicianAdd.remarks')"
              prop="remarks"
            >
              <el-input
                class="big_input"
                v-model="form.remarks"
                type="textarea"
                :placeholder="$t('technicianAdd.remarksTips')"
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
        >{{$t('technicianAdd.save')}}</el-button>
        <el-button
          size="small"
          class="footer-btn"
          @click="cancelTechnicianInfo"
        >{{$t('technicianAdd.cancel')}}</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import utils from '@/util/public.js'
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
      },
      technicianName: this.$t('technicianAdd.technicianName'),
      introTips: this.$t('technicianAdd.introTips'),
      nameValid: this.$t('technicianAdd.nameValid'),
      phoneValid: this.$t('technicianAdd.phoneValid')
    }
  },
  created () {
    this.form.storeId = Number(this.$route.query.id)
    this.langDefault()
    this.initGroupData()
    this.initServiceData()
    if (this.$route.query.technicianId) {
      this.initForm()
    }
    // 配置
    let that = this
    utils.getJobTitle().then(res => {
      if (res) {
        that.technicianName = res + that.$t('technicianAdd.technicianNameR')
        that.introTips = that.$t('technicianAdd.introTipsL') + res
        that.nameValid = that.$t('technicianAdd.nameValidL') + res + that.$t('technicianAdd.technicianNameR')
        that.phoneValid = that.$t('technicianAdd.nameValidL') + res + that.$t('technicianAdd.phoneValidR')
      }
    })
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
      let _this = this
      this.form.serviceList = this.selects.map(function (item, index) {
        return item.id
      })
      this.$refs.technicianForm.validate((valid) => {
        if (valid) {
          let params = Object.assign({}, this.form)
          if (!this.$route.query.technicianId) {
            addTechnicianApi(params).then(res => {
              if (res.error === 0) {
                _this.$message.success(this.$t('technicianAdd.successSave'))
                _this.$router.push({
                  name: 'store_storemanage_technician_list',
                  query: {
                    id: _this.form.storeId,
                    businessHours: _this.$route.query.businessHours,
                    businessType: _this.$route.query.businessType
                  }
                })
              } else {
                _this.$message.error('添加失败')
              }
            })
          } else {
            params.id = this.$route.query.technicianId
            updateTechnicianApi(params).then(res => {
              if (res.error === 0) {
                _this.$message.success(this.$t('technicianAdd.updated'))
                _this.$router.push({
                  name: 'store_storemanage_technician_list',
                  query: {
                    id: _this.form.storeId,
                    businessHours: _this.$route.query.businessHours,
                    businessType: _this.$route.query.businessType
                  }
                })
              } else {
                _this.$message.error('更新失败')
              }
            })
          }
        }
      })
    },
    cancelTechnicianInfo () {
      this.$router.push({
        name: 'store_storemanage_technician_list',
        query: {
          id: this.form.storeId,
          businessHours: this.$route.query.businessHours,
          businessType: this.$route.query.businessType
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
