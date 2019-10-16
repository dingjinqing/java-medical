<template>
  <div>
    <div class="service_add_page">
      <div class="service_add_content">
        <el-steps
          :active="activeStep"
          simple
        >
          <el-step title="1.编辑基本信息"></el-step>
          <el-step title="2.编辑服务详情"></el-step>
        </el-steps>
        <div
          v-if="this.activeStep === 1"
          class="step_1"
        >
          <el-form
            ref="serviceForm"
            :model="form"
            :rules="rules"
            label-width="180px"
          >
            <el-form-item
              label="服务名称："
              prop="serviceName"
            >
              <el-input
                size="small"
                class="big_input"
                v-model="form.serviceName"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="服务价格："
              prop="servicePrice"
            >
              <el-input
                size="small"
                class="middle_input"
                v-model="form.servicePrice"
              ></el-input>
              <span class="tips">服务详情页显示的服务价，仅显示。不填则不显示服务价格</span>
            </el-form-item>
            <el-form-item
              label="预约订金："
              prop="serviceSubsist"
            >
              <el-input
                size="small"
                class="middle_input"
                v-model="form.serviceSubsist"
              ></el-input>
              <span class="tips">线上支付价格，服务详情页显示为订金字样</span>
            </el-form-item>
            <el-form-item
              label="收费说明："
              prop="chargeResolve"
            >
              <el-input
                size="small"
                class="big_input"
                v-model="form.chargeResolve"
              ></el-input>
              <span class="tips">服务详情页的收费说明内容</span>
            </el-form-item>
            <el-form-item
              label="服务分类："
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
              label="上下架："
              prop="serviceShelf"
            >
              <el-radio-group v-model="form.serviceShelf">
                <el-radio :label="1">上架</el-radio>
                <el-radio :label="0">下架</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item
              label="服务主图："
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
              <p class="tip">建议尺寸：800*800像素</p>
            </el-form-item>
            <el-form-item
              label="服务模式："
              prop="serviceType"
            >
              <el-radio-group v-model="form.serviceType">
                <div class="radio_div">
                  <el-radio :label="0">服务+时间</el-radio>
                </div>
                <div class="radio_div">
                  <el-radio :label="1">服务+时间+技师</el-radio>
                </div>
              </el-radio-group>
            </el-form-item>
            <el-form-item
              label="可服务日期："
              required
            >
              <el-form-item
                style="display:inline-block;"
                inline-message
                prop="startDate"
              >
                <el-date-picker
                  v-model="form.startDate"
                  type="date"
                  size="small"
                  placeholder="选择日期"
                  value-format="yyyy-MM-dd HH:mm:ss"
                ></el-date-picker>
              </el-form-item>
              <span>至</span>
              <el-form-item
                style="display:inline-block;"
                inline-message
                prop="endDate"
              >
                <el-date-picker
                  v-model="form.endDate"
                  type="date"
                  size="small"
                  placeholder="选择日期"
                  value-format="yyyy-MM-dd HH:mm:ss"
                ></el-date-picker>
              </el-form-item>
              <p
                class="tips"
                style="margin-top:10px;"
              >前端用户预约所选择的服务日期按照该限制显示</p>
            </el-form-item>
            <el-form-item
              label="服务时段："
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
              <span>至</span>
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
              >时间段应在门店营业时间内(营业时间：{{ businessHours }})，前端用户所选择的服务时段将按照该时段进行拆分</p>
            </el-form-item>
            <el-form-item
              label="服务时长："
              required
            >
              <el-input-number
                v-model="serviceHour"
                controls-position="right"
                size="small"
                :min="0"
              ></el-input-number>
              <span>小时</span>
              <el-input-number
                v-model="serviceMinute"
                controls-position="right"
                size="small"
                :min="0"
                :max="60"
              ></el-input-number>
              <span>分钟</span>
            </el-form-item>
            <el-form-item
              :label="form.serviceType===0?'同时段可服务人数':'同时段内技师可服务人数'"
              required
            >
              <el-input-number
                v-model="form.servicesNumber"
                controls-position="right"
                size="small"
                :min="0"
              ></el-input-number>
              <p class="tips">{{form.serviceType===0?'该服务在同一时段内可预约的最多人数':'该服务在同一时间段内每个技师可以被预约的次数'}}</p>
            </el-form-item>
          </el-form>
        </div>
        <div
          v-else-if="this.activeStep === 2"
          class="step_2"
        >
          <div class="service_detail_wrap">
            <div class="service_detail_preview">
              <header class="preview_header">服务详情效果预览</header>
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
                  :disabled="false"
                  @input="editorInputHandle"
                  @onClick="onClick"
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
        :imageSize="imageSize"
        @handleSelectImg='imgDialogSelectedCallback'
      />
      <div class="footer">
        <el-button
          size="small"
          type="primary"
          class="footer-btn"
          @click="saveServiceHandle"
        >保存</el-button>
        <el-button
          size="small"
          class="footer-btn"
          @click="nextStepHandle"
        >下一步</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllServiceCats, addService } from '@/api/admin/storeManage/storemanage/serviceManage'
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'),
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor')
  },
  data () {
    return {
      activeStep: 2, // 步骤条
      storeId: '',
      businessHours: '', // 营业时间
      serviceCats: [], // 服务分类下拉
      imageSize: [800, 800], // 图片宽高
      imgLists: [], // 服务主图列表
      imgDialogShow: false, // 添加图片组件
      isDraggable: true, // 是否支持多选
      serviceHour: '', // 服务时长-时
      serviceMinute: '', // 服务时长-分
      form: {
        storeId: '',
        serviceName: '',
        servicePrice: 0,
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
          { required: true, message: '服务名不能为空', trigger: 'blur' }
        ],
        catId: [
          { required: true, message: '必须选择服务分类', trigger: 'change' }
        ],
        serviceImg: [
          { required: true, message: '服务主图不能为空' }
        ],
        serviceType: [
          { required: true, message: '必须选择服务模式', trigger: 'change' }
        ],
        startDate: [
          { required: true, message: '可服务开始时间未填写', trigger: 'blur' }
        ],
        endDate: [
          { required: true, message: '可服务结束时间未填写', trigger: 'blur' }
        ],
        startPeriod: [
          { required: true, message: '服务时段不能为空', trigger: 'blur' }
        ],
        endPeriod: [
          { required: true, message: '服务时段不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.initStatus()
  },
  methods: {
    onClick (e, editor) {
      console.log(e, editor)
    },
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
      this.$set(this.form, 'serviceDuration', Number(this.serviceHour * 60 + this.serviceMinute))
      this.$refs.serviceForm.validate((valid) => {
        if (valid) {
          let params = Object.assign({}, this.form)
          addService(params).then(res => {
            if (res.error === 0) {
              this.$message.success('保存成功')
            }
          })
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
          if (this.activeStep < 3) {
            this.activeStep++
          }
        }
      })
    },
    // 富文本编辑器输入
    editorInputHandle (val) {
      this.$nextTick(function () {
        this.$refs.tinymceEditor.innerHTML = val
      })
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
