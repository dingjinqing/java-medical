<template>
  <div>
    <div class="service_add_page">
      <div class="service_add_content">
        <el-steps
          :active="activeStep"
          simple
        >
          <el-step :title="'1.' + $t('serviceAdd.edit1Tip')"></el-step>
          <el-step :title="'2.' + $t('serviceAdd.edit2Tip')"></el-step>
        </el-steps>
        <div
          v-show="this.activeStep === 1"
          class="step_1"
        >
          <el-form
            ref="serviceForm"
            :model="form"
            :rules="rules"
            label-width="180px"
            size="small"
          >
            <el-form-item
              :label="$t('serviceAdd.serviceName')+ '：'"
              prop="serviceName"
            >
              <el-input
                size="small"
                class="big_input"
                v-model="form.serviceName"
              ></el-input>
            </el-form-item>
            <el-form-item
              :label="$t('serviceAdd.serviceFee')+ '：'"
              prop="servicePrice"
            >
              <el-input
                size="small"
                class="middle_input"
                v-model="form.servicePrice"
              ></el-input>
              <span class="tips">{{$t('serviceAdd.priceTips')}}</span>
            </el-form-item>
            <el-form-item
              :label="$t('serviceAdd.applyDeposit')+ '：'"
              prop="serviceSubsist"
            >
              <el-input
                size="small"
                class="middle_input"
                v-model="form.serviceSubsist"
              ></el-input>
              <span class="tips">{{$t('serviceAdd.subsistTips')}}</span>
            </el-form-item>
            <el-form-item
              :label="$t('serviceAdd.chargeInstruction')+ '：'"
              prop="chargeResolve"
            >
              <el-input
                size="small"
                class="big_input"
                v-model="form.chargeResolve"
              ></el-input>
              <span class="tips">{{$t('serviceAdd.chargeTips')}}</span>
            </el-form-item>
            <el-form-item
              :label="$t('serviceAdd.serviceClass')+ '：'"
              prop="catId"
            >
              <el-select
                size="small"
                v-model="form.catId"
              >
                <el-option
                  v-for="item in serviceCats"
                  :key="item.catId"
                  :value="item.catId"
                  :label="item.catName"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              :label="$t('serviceAdd.underTheShelf')+ '：'"
              prop="serviceShelf"
            >
              <el-radio-group v-model="form.serviceShelf">
                <el-radio :label="1">{{$t('serviceAdd.shelf')}}</el-radio>
                <el-radio :label="0">{{$t('serviceAdd.unShelf')}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item
              :label="$t('serviceAdd.serviceMap')+ '：'"
              prop="serviceImg"
            >
              <div style="display: flex;align-items: center;flex-wrap: wrap;">
                <div
                  v-for="(item,index) in imgLists"
                  :key="index"
                  class="goodsImgWrap"
                >
                  <el-image
                    fit="cover"
                    :src="item.imgUrl"
                    style="width: 78px; height: 78px;"
                  ></el-image>
                  <span
                    @click="deleteGoodsImg(index)"
                    class="deleteIcon"
                  >×</span>
                  <span
                    @click="moveGoodsImgIndex(index,-1)"
                    class="moveIcon"
                    style="left: 0px;"
                  >←</span>
                  <span
                    @click="moveGoodsImgIndex(index,1)"
                    class="moveIcon"
                    style="right: 0px;"
                  >→</span>
                </div>
                <div
                  class="goodsImgWrap"
                  @click="addImagesHandle"
                  v-if="imgLists.length < 5"
                >
                  <el-image
                    fit="scale-down"
                    :src="$imageHost+'/image/admin/add_img.png'"
                    style="width: 78px; height: 78px;cursor: pointer;"
                  />
                </div>
              </div>
              <p class="tip">{{$t('serviceAdd.recommendSize')}}</p>
            </el-form-item>
            <el-form-item
              :label="$t('serviceAdd.serviceMode')+ '：'"
              prop="serviceType"
            >
              <el-radio-group v-model="form.serviceType">
                <div class="radio_div">
                  <el-radio :label="0">{{$t('serviceAdd.serviceMode1')}}</el-radio>
                </div>
                <div class="radio_div">
                  <el-radio :label="1">{{$t('serviceAdd.serviceMode2')}}</el-radio>
                </div>
              </el-radio-group>
            </el-form-item>
            <el-form-item
              :label="$t('serviceAdd.serviceableDate')+ '：'"
              required
              prop="dateInterval"
            >
              <!-- <el-form-item
                style="display:inline-block;"
                inline-message
                prop="startDate"
              >
                <el-date-picker
                  v-model="form.startDate"
                  type="date"
                  size="small"
                  :placeholder="$t('serviceAdd.selectDate')"
                  value-format="yyyy-MM-dd HH:mm:ss"
                ></el-date-picker>
              </el-form-item>
              <span>{{$t('serviceAdd.to')}}</span>
              <el-form-item
                style="display:inline-block;"
                inline-message
                prop="endDate"
              >
                <el-date-picker
                  v-model="form.endDate"
                  type="date"
                  size="small"
                  :placeholder="$t('serviceAdd.selectDate')"
                  value-format="yyyy-MM-dd HH:mm:ss"
                ></el-date-picker>
              </el-form-item> -->
              <el-date-picker
                v-model="form.dateInterval"
                type="daterange"
                :range-separator="$t('serviceAdd.to')"
                :start-placeholder="$t('serviceAdd.selectDate')"
                :end-placeholder="$t('serviceAdd.selectDate')"
                value-format="yyyy-MM-dd HH:mm:ss"
              ></el-date-picker>
              <p
                class="tips"
                style="margin-top:10px;"
              >{{$t('serviceAdd.dateTips')}}</p>
            </el-form-item>
            <el-form-item
              :label="$t('serviceAdd.serviceHours')+ '：'"
              required
            >
              <el-form-item
                style="display:inline-block;"
                prop="startPeriod"
              >
                <el-time-picker
                  size="small"
                  arrow-control
                  v-model="form.startPeriod"
                  value-format="HH:mm"
                  :picker-options="{
                  selectableRange: '00:00:00 - 23:59:59',
                  format:'HH:mm'
                }"
                ></el-time-picker>
              </el-form-item>
              <span>{{$t('serviceAdd.to')}}</span>
              <el-form-item
                style="display:inline-block;"
                prop="endPeriod"
              >
                <el-time-picker
                  size="small"
                  arrow-control
                  v-model="form.endPeriod"
                  value-format="HH:mm"
                  :picker-options="{
                  selectableRange: '00:00:00 - 23:59:59',
                  format: 'HH:mm'
                }"
                ></el-time-picker>
              </el-form-item>
              <p
                class="tips"
                style="margin-top:10px;"
              >{{$t('serviceAdd.endPeriodTips')}}：{{ businessHours }})，{{$t('serviceAdd.endPeriodTips2')}}</p>
            </el-form-item>
            <el-form-item
              :label="$t('serviceAdd.serviceDuration')+ '：'"
              prop="serviceDuration"
              required
            >
              <el-input-number
                v-model="serviceHour"
                controls-position="right"
                size="small"
                :min="0"
              ></el-input-number>
              <span>{{$t('serviceAdd.hour')}}</span>
              <el-input-number
                v-model="serviceMinute"
                controls-position="right"
                size="small"
                :min="0"
                :max="60"
              ></el-input-number>
              <span>{{$t('serviceAdd.minute')}}</span>
            </el-form-item>
            <el-form-item
              :label="form.serviceType===0?$t('serviceAdd.serviceType1'):$t('serviceAdd.serviceType2')"
              required
            >
              <el-input-number
                v-model="form.servicesNumber"
                controls-position="right"
                size="small"
                :min="0"
              ></el-input-number>
              <p class="tips">{{form.serviceType===0?$t('serviceAdd.servicesNumber1'):$t('serviceAdd.servicesNumber2')}}</p>
            </el-form-item>
          </el-form>
        </div>
        <div
          v-show="this.activeStep === 2"
          class="step_2"
        >
          <div class="service_detail_wrap">
            <div class="service_detail_preview">
              <header class="preview_header">{{$t('serviceAdd.servicePrev')}}</header>
              <div class="preview_content">
                <div
                  ref="editorpreview"
                  class="preview_show"
                ></div>
              </div>
            </div>
            <div class="service_detail_edit">
              <div>
                <TinymceEditor
                  v-model="form.content"
                  @input="editorInputHandle"
                  ref="tinymceEditor"
                ></TinymceEditor>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 选择图片组件 -->
      <ImageDalog
        :tuneUp="imgDialogShow"
        :isDraggable='isDraggable'
        pageIndex='pictureSpace'
        :imageSize="[800, 800]"
        @handleSelectImg='imgDialogSelectedCallback'
      />
      <div class="footer">
        <el-button
          size="small"
          type="primary"
          class="footer-btn"
          @click="saveServiceHandle"
        >{{$t('serviceAdd.save')}}</el-button>
        <el-button
          size="small"
          class="footer-btn"
          @click="nextStepHandle"
        >{{this.activeStep === 1?$t('serviceAdd.next'):$t('serviceAdd.prev')}}</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllServiceCats, addService, editService, updateService } from '@/api/admin/storeManage/storemanage/serviceManage'
import('@/util/date')
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'),
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor')
  },
  data () {
    let that = this
    function validInterval (rule, value, callback) {
      if (!that.form.startDate || !that.form.endDate) {
        return callback(new Error(that.$t('serviceAdd.validInterval')))
      }
      callback()
    }
    function validateDuration (rule, value, callback) {
      if (!that.form.serviceDuration) {
        return callback(new Error(that.$t('serviceAdd.validateDuration')))
      }
      callback()
    }
    return {
      activeStep: 1, // 步骤条
      storeId: '',
      businessHours: '', // 营业时间
      serviceCats: [], // 服务分类下拉
      imgLists: [], // 服务主图列表
      imgDialogShow: false, // 添加图片组件
      isDraggable: true, // 是否支持多选
      serviceHour: '', // 服务时长-时
      serviceMinute: '', // 服务时长-分
      form: {
        dateInterval: [],
        id: '', // 编辑时传
        storeId: '',
        serviceName: '',
        servicePrice: 0,
        serviceSubsist: '',
        catId: '',
        serviceShelf: 1,
        serviceImg: '',
        serviceType: 0,
        startDate: '',
        endDate: '',
        startPeriod: '',
        endPeriod: '',
        serviceDuration: null,
        servicesNumber: null,
        content: ''
      },
      rules: {
        serviceName: [
          { required: true, message: this.$t('serviceAdd.serviceNameValid'), trigger: 'blur' }
        ],
        catId: [
          { required: true, message: this.$t('serviceAdd.serviceClassValid'), trigger: 'change' }
        ],
        serviceImg: [
          { required: true, message: this.$t('serviceAdd.serviceMapValid') }
        ],
        serviceType: [
          { required: true, message: this.$t('serviceAdd.serviceModeValid'), trigger: 'change' }
        ],
        startDate: [
          { required: true, message: this.$t('serviceAdd.serviceStartTimeValid'), trigger: 'blur' }
        ],
        endDate: [
          { required: true, message: this.$t('serviceAdd.serviceEndTimeValid'), trigger: 'blur' }
        ],
        startPeriod: [
          { required: true, message: this.$t('serviceAdd.servicePeriodValid'), trigger: 'blur' }
        ],
        endPeriod: [
          { required: true, message: this.$t('serviceAdd.servicePeriodValid'), trigger: 'blur' }
        ],
        dateInterval: [
          { validator: validInterval }
        ],
        serviceDuration: [
          { validator: validateDuration }
        ]
      }
    }
  },
  watch: {
    'form.dateInterval': function (val) {
      if (val && Array.isArray(val) && val.length === 2) {
        this.form.startDate = new Date(val[0]).format('yyyy-MM-dd hh:mm:ss')
        this.form.endDate = new Date(val[1]).format('yyyy-MM-dd hh:mm:ss')
      } else {
        this.form.startDate = ''
        this.form.endDate = ''
      }
    }
  },
  created () {
    this.langDefault()
    this.initStatus()
    this.initDetail()
  },
  methods: {
    initStatus () {
      this.storeId = this.$route.query.id
      this.form.storeId = Number(this.storeId)
      // 显示营业时间
      if (this.$route.query.businessHours) {
        this.businessHours = this.$route.query.businessHours
        const startTime = this.businessHours.split(' ')[0]
        const endTime = this.businessHours.split(' ')[2]
        this.$set(this.form, 'startPeriod', startTime)
        this.$set(this.form, 'endPeriod', endTime)
      }
      // 初始化服务分类
      this.initServiceCats()
    },
    initDetail () {
      if (this.$route.query.serviceId) {
        const serviceId = this.$route.query.serviceId
        this.form.id = serviceId
        let query = {
          serviceId: serviceId
        }
        editService(query).then(res => {
          const content = res.content
          for (const key in content) {
            if (this.form.hasOwnProperty(key)) {
              if (content[key] && (key === 'startDate' || key === 'endDate')) {
                if (content[key].indexOf(':') < 0) {
                  this.form[key] = content[key] + ' 00:00:00'
                } else {
                  this.form[key] = content[key]
                }
              } else {
                this.form[key] = content[key]
              }
            }
          }
          if (this.form.startDate && this.form.endDate) {
            this.form.dateInterval = [new Date(this.form.startDate), new Date(this.form.endDate)]
          } else {
            this.form.dateInterval = []
          }
          // 初始化图片列表
          let serviceImg = JSON.parse(this.form.serviceImg)
          this.imgLists = serviceImg.map(function (item, index) {
            return {
              imgUrl: item
            }
          })
          // 初始化服务时长
          this.serviceHour = Math.ceil(Number(this.form.serviceDuration) / 60)
          this.serviceMinute = Math.ceil(Number(this.form.serviceDuration) % 60)
        })
      }
    },
    initServiceCats () {
      let params = {
        storeId: this.storeId
      }
      getAllServiceCats(params).then(res => {
        if (res.error === 0) {
          this.serviceCats = res.content
        }
      })
    },
    // 添加图片
    addImagesHandle () {
      this.imgDialogShow = !this.imgDialogShow
    },
    /* 商品图片点击回调函数 */
    imgDialogSelectedCallback (imgObj) {
      if (this.imgLists.length >= 5) {
        return
      }
      if (this.imgLists.length + imgObj.length >= 5) {
        return
      }
      this.imgLists.push(...imgObj)
      let imgs = this.imgLists.map(item => item.imgUrl)
      this.form.serviceImg = JSON.stringify(imgs)
    },
    /* 删除商品图片 */
    deleteGoodsImg (index) {
      this.imgLists.splice(index, 1)
    },
    /* 移动商品图片 -1:左移 1:右移 */
    moveGoodsImgIndex (index, direction) {
      let tempArr = this.imgLists.splice(index, 1)
      let arrLength = this.imgLists.length
      let targetIndex = index + direction

      index = targetIndex < 0 ? arrLength : targetIndex > arrLength ? 0 : targetIndex

      this.imgLists.splice(index, 0, tempArr[0])
    },
    // 保存
    saveServiceHandle () {
      let that = this
      this.$set(this.form, 'serviceDuration', Number(this.serviceHour * 60 + this.serviceMinute))
      this.$refs.serviceForm.validate((valid) => {
        if (valid) {
          let params = Object.assign({}, this.form)
          if (!params.id) {
            addService(params).then(res => {
              if (res.error === 0) {
                that.$message.success(this.$t('serviceAdd.successTip'))
                that.$router.push({
                  name: 'store_storemanage_service_list',
                  query: {
                    id: that.storeId,
                    businessHours: that.$route.query.businessHours,
                    businessType: that.$route.query.businessType
                  }
                })
              }
            })
          } else {
            updateService(params).then(res => {
              if (res.error === 0) {
                this.$message.success(this.$t('serviceAdd.updateTip'))
                that.$router.push({
                  name: 'store_storemanage_service_list',
                  query: {
                    id: that.storeId,
                    businessHours: that.$route.query.businessHours,
                    businessType: that.$route.query.businessType
                  }
                })
              }
            })
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 下一步
    nextStepHandle () {
      this.$set(this.form, 'serviceDuration', Number(this.serviceHour * 60 + this.serviceMinute))
      this.$refs.serviceForm.validate((valid) => {
        if (valid) {
          if (this.activeStep === 1) {
            this.activeStep = 2
          } else {
            this.activeStep = 1
          }
        }
      })
    },
    // 富文本编辑器输入
    editorInputHandle (val) {
      this.$refs.editorpreview.innerHTML = val
    }
  }
}
</script>

<style lang="scss" scoped>
.service_add_page {
  position: relative;
  width: 100%;
  .service_add_content {
    margin: 0 25px 50px;
    .step_1 {
      .tips {
        color: #999;
      }
      input {
        padding-left: 20px;
      }
      .big_input {
        width: 400px;
      }
      .middle_input {
        width: 250px;
      }
      .goodsImgWrap {
        width: 80px;
        height: 80px;
        border: 1px solid #ccc;
        background: #f7f7f7;
        margin: 5px 5px;
        position: relative;
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
        .moveIcon {
          width: 17px;
          height: 17px;
          display: none;
          color: #fff;
          background: #ccc;
          border: 1px solid #ccc;
          line-height: 17px;
          text-align: center;
          position: absolute;
          bottom: 0px;
          cursor: pointer;
          opacity: 0.8;
        }
        &:hover .moveIcon {
          display: block;
        }
      }
      .radio_div {
        line-height: 30px;
        margin-top: 11px;
      }
    }
    .step_2 {
      .service_detail_wrap {
        display: flex;
        justify-content: center;
        padding: 25px 0 35px;
        text-align: center;
        margin: 0 auto;
        .service_detail_preview {
          width: 325px;
          min-height: 400px;
          border: 1px solid #dad8d9;
          .preview_header {
            line-height: 55px;
            background: #dad8d9;
          }
          .preview_content {
            height: 455px;
            background: #eee;
            padding: 10px 10px 15px;
            .preview_show {
              border: 1px dashed #ccc;
              width: 100%;
              height: 100%;
              background: #fff;
              text-align: left;
              ol {
                list-style-type: decimal;
              }
              li {
                list-style-type: disc;
              }
              table {
                border: 1px solid #ccc;
                th,
                tr {
                  border: 1px solid #ccc;
                }
              }
            }
          }
        }
        .service_detail_edit {
          width: 684px;
          border: 1px solid #e5e5e5;
          background: #f8f8f8;
          padding: 22px 15px;
          -webkit-border-radius: 5px;
          -moz-border-radius: 5px;
          border-radius: 5px;
          margin-left: 30px;
        }
      }
    }
  }
  .footer {
    position: fixed;
    z-index: 100;
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
