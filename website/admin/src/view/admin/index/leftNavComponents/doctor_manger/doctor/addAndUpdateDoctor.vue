<template>
  <div class="doctorInfo">
    <div v-if="reload" id="doctorDiv" class="doctorContent">
      <!-- 添加内容 -->
      <el-form
        ref="doctorForm"
        :model="doctorFormInfo"
        :rules="doctorFormRules"
        label-width="150px"
        size="small"
        label-suffix="："
      >
        <el-form-item label="注册医院" prop="registeredHospital">
          <span>医院名称</span>
        </el-form-item>
        <el-form-item label="医生签名" prop="signature">
          <div
            class="choose_url_img"
            style="border: 1px solid #ccc; width: 150px; height: 78px"
          >
            <el-image
              fit="scale-down"
              :src="imgHost + '/' + signPic"
              style="width: 150px; height: 78px; cursor: pointer"
              @click="addSignPic()"
            />
          </div>
        </el-form-item>
        <el-form-item label="头像" prop="url">
          <el-radio-group v-model="urlType" class="radio_url">
            <el-radio :label="0" style="margin-right: 10px">自定义</el-radio>
            <div class="choose_url_img">
              <el-image
                fit="scale-down"
                :src="imgHost + '/' + showedPic"
                style="width: 78px; height: 78px; cursor: pointer"
                @click="addImage()"
              />
            </div>
            <el-radio :label="1">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="医生姓名" prop="name">
          <el-input
            v-model="doctorFormInfo.name"
            placeholder="请输入医生姓名"
          ></el-input>
        </el-form-item>
        <el-form-item label="医生院内编号" prop="hospitalCode">
          <el-input
            v-model="doctorFormInfo.hospitalCode"
            placeholder="请输入医生院内编号"
          ></el-input>
        </el-form-item>
        <el-form-item label="医生资格编码" prop="certificateCode">
          <el-input
            v-model="doctorFormInfo.certificateCode"
            placeholder="请输入医生资格编码"
          ></el-input>
        </el-form-item>
        <el-form-item label="医生执业编码" prop="professionalCode">
          <el-input
            v-model="doctorFormInfo.professionalCode"
            placeholder="请输入医生执业编码"
          ></el-input>
        </el-form-item>
        <el-form-item label="医生职称" prop="titleId">
          <el-select
            v-model="doctorFormInfo.titleId"
            placeholder="请选择医生职称"
          >
            <el-option
              v-for="item in doctorJobTitles"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="聘任职务" prop="duty">
          <el-select v-model="doctorFormInfo.duty" placeholder="请选择聘任职务">
            <el-option
              v-for="item in hireJobs"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属科室" prop="departmentIdsStr">
          <el-select
            v-model="doctorFormInfo.departmentIdsStr"
            multiple
            placeholder="请选择"
          >
            <el-option-group
              v-for="group in belongParts"
              :key="group.name"
              :label="group.name"
            >
              <el-option
                v-for="item in group.childDepartmentList"
                :key="item.name"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="doctorFormInfo.sex">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
            <el-radio :label="2">未知</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input
            v-model.number="doctorFormInfo.mobile"
            placeholder="请输入手机号码"
          ></el-input>
        </el-form-item>
        <el-form-item label="问诊费用" prop="consultationPrice">
          <el-input
            v-model="doctorFormInfo.consultationPrice"
            ref="conPriceInput"
            placeholder="请填写问诊费用"
          ></el-input>
        </el-form-item>
        <el-form-item label="擅长疾病" prop="treatDisease">
          <el-input
            v-model="doctorFormInfo.treatDisease"
            placeholder="请填写擅长疾病"
          ></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="addDoctorFooter">
      <el-button type="primary" size="small" @click="handleSubmit"
        >保存</el-button
      >
    </div>
    <!--图片dialog-->
    <ImageDalog
      :tuneUp="showSignPicDialog"
      pageIndex="pictureSpace"
      :imageSize="[576, 300]"
      @handleSelectImg="handleSelectSignImg"
    />
    <!--图片dialog-->
    <ImageDalog
      :tuneUp="imgDialogShow"
      pageIndex="pictureSpace"
      :imageSize="[800, 800]"
      @handleSelectImg="handleSelectImg"
    />
  </div>
</template>

<script>
import { addDoctor, getDoctorTitle, getBelongParts, getDoctor, updateDoctor } from '@/api/admin/doctorManage/doctorInfo/doctor'
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog')
  },
  data () {
    var validatePartId = (rule, value, callback) => {
      if (!value.length) {
        callback(new Error('请选择医生所属科室'))
      } else {
        callback()
      }
    }
    var validUrl = (rule, value, callback) => {
      if (!value && this.urlType === 0) {
        callback(new Error('请选择医生头像'))
      } else {
        callback()
      }
    }
    return {
      reload: true,
      doctorFormInfo: {
        registerHospital: '医院名称',
        url: '',
        name: '',
        hospitalCode: '',
        certificateCode: '',
        professionalCode: '',
        titleId: null,
        duty: '',
        departmentIdsStr: [],
        sex: 0,
        mobile: '',
        account_id: 0,
        consultationPrice: 0.00,
        treatDisease: '',
        signature: ''
      },
      doctorFormRules: {
        url: [{ required: true, validator: validUrl, trigger: 'change' }],
        name: [{ required: true, message: '请输入医生姓名', trigger: 'blur' }],
        hospitalCode: [{ required: true, message: '请输入医生院内编号', trigger: 'blur' }],
        certificateCode: [{ required: true, message: '请输入医生资格编码', trigger: 'blur' }],
        professionalCode: [{ required: true, message: '请输入医生执业编码', trigger: 'blur' }],
        titleId: [{ required: true, message: '请选择医生职称', trigger: 'change' }],
        duty: [{ required: true, message: '请选择聘任职务', trigger: 'change' }],
        departmentIdsStr: [{ required: true, validator: validatePartId, trigger: 'change' }],
        sex: [{ required: true, message: '请选择医生性别', trigger: 'change' }],
        mobile: [
          { required: true, message: '请填写手机号', trigger: 'blur' },
          { type: 'number', message: '请填写数字', trigger: 'blur' }
        ]
      },
      doctorJobTitles: {},
      hireJobs: [
        {
          id: 5,
          name: '正高'
        }, {
          id: 4,
          name: '副高'
        }, {
          id: 3,
          name: '中级'
        }, {
          id: 2,
          name: '助理'
        }, {
          id: 1,
          name: '待聘'
        }, {
          id: 0,
          name: '无'
        }
      ],
      belongParts: {},
      urlType: 0,
      imgHost: `${this.$imageHost}`,
      imgDialogShow: false,
      showedPic: '/image/admin/add_img.png',
      signPic: '/image/admin/add_img.png',
      showSignPicDialog: false
    }
  },
  mounted () {
    if (this.$route.query.id) {
      this.id = this.$route.query.id
      this.initDoctor(this.id)
    }
    this.initDoctorTitle()
    this.initDoctorPart()
  },
  watch: {
    '$route.query.id': function (newVal) {
      if (newVal) {
        this.id = this.$route.query.id
        this.initDoctor(this.id)
      } else {
        this.initData()
      }
    }
  },
  methods: {
    // 初始化数据
    initData () {
      this.doctorFormInfo = {
        registerHospital: '医院名称',
        url: '',
        name: '',
        hospitalCode: '',
        certificateCode: '',
        professionalCode: '',
        titleId: 0,
        duty: '',
        departmentIdsStr: [],
        sex: 0,
        mobile: '',
        account_id: 0,
        consultationPrice: 0.00,
        treatDisease: ''
      }
    },
    // 添加医师签名
    addSignPic () {
      this.showSignPicDialog = !this.showSignPicDialog
      this.$http.$emit('dtVisible')
    },
    // 获取签名图片
    handleSelectSignImg (res) {
      this.doctorFormInfo.signature = res.imgPath
      this.signPic = res.imgPath
      console.log(res.imgPath)
    },
    // 获取医师详情
    initDoctor (id) {
      getDoctor(id).then(res => {
        console.log(res)
        if (res.error === 0) {
          let doctorFormInfo = Object.assign({}, this.doctorFormInfo, res.content)
          doctorFormInfo.mobile = Number(doctorFormInfo.mobile)
          doctorFormInfo.departmentIdsStr = doctorFormInfo.departmentIds
          if (doctorFormInfo.url === '') {
            this.urlType = 1
          } else {
            this.urlType = 0
            this.showedPic = doctorFormInfo.url
          }
          this.signPic = doctorFormInfo.signature
          this.doctorFormInfo = doctorFormInfo
        } else {
          this.$message.error({
            message: '获取失败',
            showClose: true
          })
        }
      })
    },
    // 用户头像
    addImage () {
      this.imgDialogShow = !this.imgDialogShow
      this.$http.$emit('dtVisible')
    },
    handleSelectImg (res) {
      this.doctorFormInfo.url = res.imgPath
      this.showedPic = res.imgPath
      console.log(res.imgPath)
    },
    // 职称查询
    initDoctorTitle () {
      let params = {}
      getDoctorTitle(params).then(res => {
        this.doctorJobTitles = res.content
      })
    },
    // 科室列表
    initDoctorPart () {
      let params = {}
      getBelongParts(params).then(res => {
        this.belongParts = res.content
      })
    },
    // 保存
    handleSubmit () {
      let that = this
      that.$refs.doctorForm.validate((valid) => {
        if (valid) {
          let params = this.doctorFormInfo
          // 费用写成浮点型
          if (params.consultationPrice !== '' && isNaN(Number(params.consultationPrice))) {
            this.$message.warning({ message: '请输入正确的数字', type: 'warning' })
            params.consultationPrice = '0.00'
            params.consultationPrice = parseFloat(params.consultationPrice)
            this.$refs.conPriceInput.focus()
            return false
          }
          // 科室需要字符串
          params.departmentIdsStr = params.departmentIdsStr.join(',')
          if (this.urlType === 1) {
            params.url = ''
          }
          console.log(params)
          if (!this.id) {
            addDoctor(params).then(res => {
              console.log(res)
              if (res.error === 0) {
                this.$message.success({
                  message: '添加成功',
                  showClose: true
                })
                this.$router.push({ name: 'doctorList' })
              } else {
                this.$message.error({
                  message: '添加失败',
                  showClose: true
                })
              }
            })
          } else {
            updateDoctor(params).then(res => {
              console.log(res)
              if (res.error === 0) {
                this.$message.success({
                  message: '更新成功',
                  showClose: true
                })
                this.$router.push({ name: 'doctorList' })
              } else {
                this.$message.error({
                  message: '更新失败',
                  showClose: true
                })
              }
            })
          }
        }
      })
    }
  }
}
</script>

<style scoped lang='scss'>
.doctorInfo {
  padding: 10px 10px;
  overflow-y: auto;
  .doctorContent {
    background-color: white;
    padding: 10px 10px 100px;
    .add_belong_part {
      cursor: pointer;
      width: 250px;
    }
    .el-input {
      width: 300px;
    }
    .radio_url {
      display: flex;
      .el-radio {
        padding-top: 10px;
      }
      .choose_url_img {
        width: 80px;
        height: 80px;
        border: 1px solid #ccc;
        margin-right: 10px;
      }
    }
  }
  .addDoctorFooter {
    width: calc(100% - 150px);
    transform: translateX(150px);
    background: #f8f8fa;
    text-align: center;
    box-sizing: border-box;
    height: 50px;
    padding-top: 10px;
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 2;
  }
}
</style>
