<template>
  <div class="storeWrap">
    <div v-if="reload" id="storeDiv" class="storeContent">
      <!-- 头部导航 headerSteps-->
      <el-steps
        :active="stepData.currentStep"
        simple
        v-if="storeFormInfo.storeType === 0"
      >
        <el-step :title="step1" icon="el-icon-edit"></el-step>
        <el-step :title="step2" icon="el-icon-edit"></el-step>
      </el-steps>

      <!-- 主要内容区 -->
      <el-form
        v-show="this.stepData.currentStep == 0"
        ref="storeForm"
        :model="storeFormInfo"
        :rules="storeFormRules"
        label-width="120px"
        style="margin-top: 20px"
        size="small"
        label-suffix="："
      >
        <el-form-item label="门店类型" prop="storeType">
          <el-radio v-model="storeFormInfo.storeType" :label="0">门店</el-radio>
          <el-radio v-model="storeFormInfo.storeType" :label="1">医院</el-radio>
        </el-form-item>
        <el-form-item :label="$t('addStore.storeName')" prop="storeName">
          <el-input
            v-model="storeFormInfo.storeName"
            :placeholder="$t('addStore.storeNameTip')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('addStore.principal')" prop="manager">
          <el-input
            v-model="storeFormInfo.manager"
            :placeholder="$t('addStore.principalTip')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('addStore.contactNum')" prop="mobile">
          <el-input
            v-model="storeFormInfo.mobile"
            :placeholder="$t('addStore.contactNumTip')"
          ></el-input>
        </el-form-item>
        <el-form-item label="门店编码" prop="storeCode">
          <el-input
            v-model="storeFormInfo.storeCode"
            placeholder="请输入门店编码"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('addStore.businessStatus')"
          prop="businessState"
        >
          <el-radio-group v-model="storeFormInfo.businessState">
            <el-radio :label="1">{{ $t('addStore.open') }}</el-radio>
            <el-radio :label="0">{{ $t('addStore.close') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('addStore.BusinessHours')" prop="businessType">
          <el-radio-group v-model="storeFormInfo.businessType">
            <el-radio :label="1">{{ $t('addStore.everyDay') }}</el-radio>
            <el-radio :label="0">{{ $t('addStore.workDay') }}</el-radio>
          </el-radio-group>
          <el-time-picker
            v-model="storeFormInfo.openingTime"
            :placeholder="$t('addStore.startTime')"
            style="width: 12%; margin-left: 20px"
            format="HH:mm"
            value-format="HH:mm"
          ></el-time-picker>
          <span>-</span>
          <el-time-picker
            v-model="storeFormInfo.closeTime"
            :placeholder="$t('addStore.endTime')"
            style="width: 12%"
            format="HH:mm"
            value-format="HH:mm"
          ></el-time-picker>
          <p style="margin-left: 172px; color: #a0a0a0">
            {{ $t('addStore.timeTip') }} 9:00-21:00
          </p>
        </el-form-item>
        <el-form-item :label="$t('addStore.ownedGroup')" prop="group">
          <el-select
            v-model="storeFormInfo.group"
            :placeholder="$t('addStore.groupTip')"
          >
            <el-option :label="$t('addStore.selectGroup')" value=""></el-option>
            <el-option
              v-for="item in storeGroups"
              :key="item.groupId"
              :label="item.groupName"
              :value="item.groupId"
            ></el-option>
          </el-select>
          <el-button type="text" @click="refreshGroups">{{
            $t('addStore.refresh')
          }}</el-button>
          <span>|</span>
          <el-button type="text" @click="addGroups">{{
            $t('addStore.addNewGroup')
          }}</el-button>
        </el-form-item>
        <!-- <el-form-item
          :label="$t('addStore.storeNum') "
          prop="storeNumber"
        >
          <hc-input-number
            type="integer"
            inline
            v-model.number="storeFormInfo.storeNumber"
            :placeholder="$t('addStore.storeNumTip')"
            :maxlength="9"
            show-word-limit
          ></hc-input-number>
        </el-form-item> -->
        <el-form-item :label="$t('addStore.location')" prop="provinceCode">
          <div>
            <areaLinkage
              ref="areaLink"
              :areaCode="areaLinkage"
              @areaData="handleAreaData"
            />
          </div>
        </el-form-item>
        <el-form-item :label="$t('addStore.mapLocation')" prop="address">
          <el-input
            :placeholder="$t('addStore.locationTip')"
            v-model="storeFormInfo.address"
          ></el-input>
          <el-button type="text" @click="codeAddress">{{
            $t('addStore.mapLocation')
          }}</el-button>
          <div class="store-map" ref="storemap"></div>
        </el-form-item>
        <el-form-item
          :label="$t('addStore.specialService')"
          prop="storeService"
        >
          <el-checkbox-group v-model="storeService">
            <el-checkbox
              v-for="(item, index) in serveList"
              :key="index"
              :label="item.label"
              name="type"
            ></el-checkbox>
          </el-checkbox-group>
          <el-input
            :placeholder="$t('addStore.serviceTip')"
            v-model="addService"
          ></el-input>
          <el-button type="text" @click="addServeHandler">{{
            $t('addStore.add')
          }}</el-button>
        </el-form-item>
        <el-form-item :label="$t('addStore.storePhoto')" prop="storeImgs">
          <div
            style="
              display: flex;
              align-items: center;
              flex-wrap: wrap;
              overflow: hidden;
            "
          >
            <div
              v-for="(item, index) in storeFormInfo.storeImgs"
              :key="index"
              class="storeImgWrap"
            >
              <el-image
                fit="cover"
                :src="$imageHost + '/' + item"
                style="width: 78px; height: 78px"
              ></el-image>
              <span class="deleteIcon" @click="deleteStoreImg(index)">×</span>
            </div>
            <div
              class="storeImgWrap"
              @click="addStoreImg"
              v-if="
                storeFormInfo.storeImgs && storeFormInfo.storeImgs.length < 5
              "
            >
              <el-image
                fit="scale-down"
                :src="imgHost + '/image/admin/add_img.png'"
                style="width: 78px; height: 78px; cursor: pointer"
              />
            </div>
            <p style="width: 100%; color: #999; margin-bottom: 15px">
              最多可上传5张图，每张大小不可超过5M，格式要求为jpg，png
            </p>
          </div>
        </el-form-item>
        <el-form-item :label="$t('addStore.storeDetails')" prop="storeDetail">
          <div class="edit-wrap">
            <TinymceEditor
              v-model="storeFormInfo.content"
              ref="tinymceEditor"
            />
          </div>
        </el-form-item>
      </el-form>
      <!--图片dialog-->
      <ImageDalog
        :tuneUp="selfImgDialogShow"
        pageIndex="pictureSpace"
        @handleSelectImg="imgDialogSelectedCallback"
        isDraggable
        :imageSize="[750, 520]"
      />

      <!-- 门店配送信息 -->
      <div
        v-show="this.stepData.currentStep == 1"
        class="create_content"
        style="display: block"
      >
        <div class="containter">
          <div class="content_left">
            <img src="@/assets/image/admin/left_bg.png" alt="" />
            <div class="line1">
              <span>{{ $t('addStore.storeSelfRaising') }}</span>
              <a href="javascript:void(0);">{{
                $t('addStore.viewSelfRaisingTip')
              }}</a>
            </div>
            <div class="line2">{{ $t('addStore.selfopenTip') }}</div>
            <div class="line3">
              <el-switch
                :disabled="
                  !(storeFormInfo.latitude && storeFormInfo.longitude) ||
                  (deliverConfig &&
                    deliverConfig.fetch === 0 &&
                    storeFormInfo.autoPick === 0)
                "
                v-model="storeFormInfo.autoPick"
                active-color="#E6A23C"
                inactive-color="#ccc"
                :active-value="1"
                :inactive-value="0"
              ></el-switch
              >&nbsp;&nbsp;&nbsp;&nbsp;
              <span v-if="this.storeFormInfo.autoPick == 1">{{
                $t('addStore.turnedOn')
              }}</span>
              <span v-if="this.storeFormInfo.autoPick == 0">{{
                $t('addStore.closed')
              }}</span>
            </div>
          </div>
          <div class="content_right">
            <img src="@/assets/image/admin/right_bg.png" alt="" />
            <div class="line1">
              <span>{{ $t('addStore.Town') }}</span>
              <a href="javascript:void(0);">{{ $t('addStore.viewTownLan') }}</a>
            </div>
            <div class="line2">{{ $t('addStore.townOpenTip') }}</div>
            <div class="line3">
              <el-switch
                :disabled="
                  !(storeFormInfo.latitude && storeFormInfo.longitude) ||
                  (deliverConfig &&
                    deliverConfig.cityService === 0 &&
                    storeFormInfo.cityService === 0)
                "
                v-model="storeFormInfo.cityService"
                active-color="#E6A23C"
                inactive-color="#ccc"
                :active-value="1"
                :inactive-value="0"
              ></el-switch
              >&nbsp;&nbsp;&nbsp;&nbsp;
              <span v-if="storeFormInfo.cityService == 1">{{
                $t('addStore.turnedOn')
              }}</span>
              <span v-if="storeFormInfo.cityService == 0">{{
                $t('addStore.closed')
              }}</span>
            </div>
          </div>
        </div>
        <!-- 同城配送信息 -->
        <el-form
          ref="deliveryForm"
          :model="deliveryMessage"
          :rules="deliveryFormRules"
          class="deliveryMsg"
          label-width="140px"
          size="small"
          label-suffix="："
        >
          <el-form-item :label="$t('addStore.receiptAddress')">
            <span>{{ address + storeFormInfo.address }}</span
            ><br />
            <span style="color: #999; font-size: 14px">{{
              $t('addStore.pickUpTip')
            }}</span>
          </el-form-item>
          <div v-if="storeFormInfo.autoPick == 1">
            <el-form-item
              :label="$t('addStore.selfCollectingTime')"
              prop="pickDetail"
            >
              <div class="pick-up-time">
                <div>
                  <el-radio
                    v-model="storeFormInfo.pickTimeAction"
                    :label="1"
                  ></el-radio>
                  <span>{{ $t('addStore.storeOpeningTime') }}</span>
                  <el-popover
                    placement="top"
                    width="300"
                    trigger="hover"
                    :content="$t('addStore.pickupCondition')"
                  >
                    <i
                      slot="reference"
                      style="color: #ccc"
                      class="el-icon-question"
                    ></i>
                  </el-popover>
                </div>
                <div>
                  <el-radio
                    v-model="storeFormInfo.pickTimeAction"
                    :label="2"
                  ></el-radio>
                  <span>{{ $t('addStore.submitOrder') }}</span>
                  <hcInputNumber
                    type="integer"
                    v-model.number="storeFormInfo.pickDetail.duration"
                    controls-position="right"
                    style="width: 100px"
                    inline
                  />
                  <!-- <el-input
                    v-model.number="storeFormInfo.pickDetail.duration"
                    controls-position="right"
                    style="width:100px;"
                  ></el-input> -->
                  <el-select
                    v-model="storeFormInfo.pickDetail.type"
                    style="width: 80px"
                  >
                    <el-option
                      :label="$t('addStore.hour')"
                      :value="1"
                    ></el-option>
                    <el-option
                      :label="$t('addStore.day')"
                      :value="2"
                    ></el-option>
                  </el-select>
                  <span>{{ $t('addStore.afterPickup') }}</span>
                  <el-popover
                    placement="top"
                    width="300"
                    trigger="hover"
                    :content="$t('addStore.choosePickupTime')"
                  >
                    <i
                      slot="reference"
                      style="color: #ccc"
                      class="el-icon-question"
                    ></i>
                  </el-popover>
                </div>
              </div>
            </el-form-item>
          </div>
          <div v-if="storeFormInfo.cityService == 1">
            <el-form-item
              :label="$t('addStore.deliveryArea')"
              prop="deliveryArea"
            >
              {{ $t('addStore.aroundTheStore') }}&nbsp;&nbsp;
              <hcInputNumber
                type="price"
                v-model.number="deliveryMessage.deliveryArea"
                style="width: 80px"
                inline
              />
              <!-- <el-input
                v-model.number="deliveryMessage.deliveryArea"
                style="width: 80px;"
              ></el-input> -->
              &nbsp;&nbsp;{{ $t('addStore.withinKilo') }}
            </el-form-item>
            <el-form-item
              :label="$t('addStore.distributionPrice')"
              prop="deliveryPrice"
            >
              <hcInputNumber
                type="price"
                v-model.number="deliveryMessage.deliveryPrice"
                style="width: 80px"
                inline
              />
              <!-- <el-input
                v-model.number="deliveryMessage.deliveryPrice"
                style="width: 80px;"
              ></el-input> -->
              &nbsp;&nbsp;{{ $t('addStore.yuan') }}
            </el-form-item>
            <el-form-item
              :label="$t('addStore.mailStrategy')"
              prop="deliveryPolicy"
            >
              {{ $t('addStore.fullPayTip') }}&nbsp;&nbsp;
              <hcInputNumber
                type="price"
                v-model.number="deliveryMessage.deliveryPolicy"
                style="width: 80px"
                inline
              />
              <!-- <el-input
                v-model.number="deliveryMessage.deliveryPolicy"
                style="width: 80px;"
              ></el-input> -->
              &nbsp;&nbsp;{{ $t('addStore.fullPayTip2') }}
            </el-form-item>
            <el-form-item
              :label="$t('addStore.deliveryMethod')"
              prop="deliveryType"
            >
              <el-checkbox
                name="deliveryType"
                v-model="deliveryMessage.deliveryTypeStore"
                :true-label="1"
                :false-label="0"
                >{{ $t('addStore.businessSelfDelivery') }}&nbsp;&nbsp;<span
                  style="color: #999"
                  >({{ $t('addStore.bs_Tip') }})</span
                ></el-checkbox
              ><br />
              <el-checkbox
                name="deliveryType"
                v-model="deliveryMessage.deliveryTypeThird"
                @change="selectedDistribution = []"
                :true-label="1"
                :false-label="0"
                >{{ $t('addStore.thridDelivery') }}&nbsp;&nbsp;<span
                  style="color: #999"
                  >({{ $t('addStore.thridPremise') }})</span
                ></el-checkbox
              >
              <div v-if="deliveryMessage.deliveryTypeThird === 1">
                <!-- <el-select v-model="deliveryMessage.delivery">
                  <el-option
                    v-for="(item, index) in thirdDistributionData"
                    :key="index"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select> -->
                <selectLabel
                  :datas="thirdDistributionData"
                  v-model="selectedDistribution"
                  select-value="id"
                  select-label="customName"
                ></selectLabel>
              </div>
            </el-form-item>
            <el-form-item label="定时起送" prop="regularDeliveryType">
              <el-radio-group v-model="deliveryMessage.regularDeliveryType">
                <el-radio :label="0">关闭</el-radio>
                <el-radio :label="1">开启</el-radio>
              </el-radio-group>
              <span style="color: #999; margin-left: 20px"
                >由下单用户选择起送（发货）时间</span
              >
              <div v-if="deliveryMessage.regularDeliveryType === 1">
                支持用户选择下单
                <hcInputNumber
                  type="integer"
                  v-model.number="deliveryMessage.regularTimeDetail.duration"
                  style="width: 100px"
                  inline
                />
                <!-- <el-input
                  v-model.number="deliveryMessage.regularTimeDetail.duration"
                  style="width:100px;"
                ></el-input> -->
                <el-select
                  v-model="deliveryMessage.regularTimeDetail.type"
                  size="small"
                  style="width: 80px"
                >
                  <el-option label="小时" :value="1"></el-option>
                  <el-option label="天" :value="2"></el-option>
                </el-select>
                后的指定时段内配送
              </div>
            </el-form-item>
          </div>
        </el-form>
      </div>
      <!-- 底部按钮组件 -->
      <div class="storeFooter">
        <template>
          <el-button
            size="small"
            v-if="
              this.stepData.currentStep == 0 && storeFormInfo.storeType === 0
            "
            @click="nextClickHandler"
            >{{ $t('addStore.next') }}</el-button
          >
          <el-button
            size="small"
            v-if="
              this.stepData.currentStep == 1 && storeFormInfo.storeType === 1
            "
            @click="prevClickHandler"
            >{{ $t('addStore.previous') }}</el-button
          >
        </template>
        <el-button
          type="primary"
          size="small"
          v-if="this.stepData.currentStep == 1 || storeFormInfo.storeType === 1"
          @click="saveClickHandler"
          >{{ $t('addStore.save') }}</el-button
        >
      </div>
    </div>
  </div>
</template>
<!-- 腾讯地图 -->
<script charset="utf-8" src="https://map.qq.com/api/js?v=2.exp&key=YPOBZ-DNIKF-Y6KJM-NDW7D-VYIFZ-QEBIO"></script>
<script>
import { addStore, getStore, updateStore, allStoreGroup, getDeliveryConfig } from '@/api/admin/storeManage/store'
/* 组件导入 */
import { delayJudgment } from '@/util/pageName'
export default {
  components: {
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue'),
    ImageDalog: () => import('@/components/admin/imageDalog'),
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor'),
    selectLabel: () => import('@/components/admin/selectLabel')
  },
  data () {
    let that = this
    let validateArea = function (rule, value, callback) {
      if (!value) {
        callback(new Error(that.$t('addStore.provinceValid')))
      }
      if (!that.storeFormInfo.cityCode) {
        callback(new Error(that.$t('addStore.cityValid')))
      }
      if (!that.storeFormInfo.districtCode) {
        callback(new Error(that.$t('addStore.zoneValid')))
      }
      callback()
    }
    let validateAddress = function (rule, value, callback) {
      if (!that.storeFormInfo.latitude && !that.storeFormInfo.longitude) {
        callback(new Error(that.$t('addStore.targetingValid')))
      }
      callback()
    }
    let validBusinessTime = function (rule, value, callback) {
      let openTime = that.storeFormInfo.openingTime
      let closeTime = that.storeFormInfo.closeTime
      let openTimes = openTime.split(':')
      let closeTimes = closeTime.split(':')
      if (!that.storeFormInfo.openingTime || !that.storeFormInfo.closeTime) {
        callback(new Error(that.$t('addStore.enterHours')))
      }
      if (Number(openTimes[0]) > Number(closeTimes[0])) {
        callback(new Error(that.$t('addStore.businessHoursValid')))
      } else if (Number(openTimes[0]) === Number(closeTimes[0]) && Number(openTimes[1]) > Number(closeTimes[1])) {
        callback(new Error(that.$t('addStore.businessHoursValid')))
      }
      callback()
    }
    function validPickDetail (rule, value, callback) {
      if (that.storeFormInfo.pickTimeAction === 2) {
        if (!that.storeFormInfo.pickDetail.duration) {
          callback(new Error(that.$t('addStore.fillPickupTime')))
        }
      }
      callback()
    }
    function validDeliveryType (rule, value, callback) {
      if (that.storeFormInfo.cityService === 1) {
        if (!that.deliveryMessage.deliveryTypeThird && !that.deliveryMessage.deliveryTypeStore) {
          callback(new Error(that.$t('addStore.selectDeliveryMethod')))
        } else if (that.deliveryMessage.deliveryTypeThird && that.selectedDistribution.length === 0) {
          callback(new Error('请选择第三方配送方式'))
        }
      }
      callback()
    }
    function validRegular (rule, value, callback) {
      if (that.storeFormInfo.cityService === 1 && that.deliveryMessage.regularDeliveryType === 1) {
        if (!that.deliveryMessage.regularTimeDetail.duration) {
          callback(new Error('请输入定时配送时间'))
        }
      }
      callback()
    }
    function validNum (rule, value, callback) {
      if (/\D/g.test(value)) {
        callback(new Error('门店编码必须为数字'))
      }
      callback()
    }
    return {
      reload: true,
      stepData: {
        currentStep: 0
      },
      step1: this.$t('addStore.storeBasic'),
      step2: this.$t('addStore.storeDisInfo'),
      areaLinkage: {
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      },
      storeFormInfo: {
        storeType: 0,
        storeName: '',
        manager: '',
        mobile: '',
        businessState: 0,
        businessType: 0,
        openingTime: '',
        closeTime: '',
        group: '',
        storeId: null,
        // storeNumber: '',
        address: '',// 地图定位详细地址
        service: '', // 填写的服务
        storeImgs: [],
        storeDetail: '',
        provinceCode: '',
        cityCode: '',
        districtCode: '',
        latitude: '',
        longitude: '',
        autoPick: 0, // 设定自提
        cityService: 0, // 设定同城配送
        content: '',
        pickTimeAction: 1, // 自提
        pickDetail: {
          duration: '',
          type: 1
        },
        storeCode: ''
      },
      storeFormRules: {
        storeName: [{ required: true, message: this.$t('addStore.enterStoreName'), trigger: 'blur' }],
        manager: [{ required: true, message: this.$t('addStore.enterPersoninCharge'), trigger: 'blur' }],
        mobile: [{ required: true, message: this.$t('addStore.enterphone'), trigger: 'blur' }],
        businessType: [{ required: true, validator: validBusinessTime, trigger: 'change' }],
        // storeNumber: [{ required: true, message: this.$t('addStore.enterStoreNum'), trigger: 'blur' }, { validator: validNum }],
        provinceCode: [{ required: true, message: this.$t('addStore.selectArea') }, { validator: validateArea, trigger: 'blur' }],
        address: [{ required: true, message: this.$t('addStore.enterArea'), trigger: 'blur' }, { validator: validateAddress, trigger: 'change' }],
        storeImgs: [{ required: true, message: this.$t('addStore.selectPhoto'), trigger: ['blur', 'change'] }],

      },
      selfImgDialogShow: false,
      storeGroups: [],
      addService: '', // 添加的特色服务
      // 特色服务列表
      serveList: [{
        label: 'WIFI',
        checked: false
      }, {
        label: this.$t('addStore.parkingSpace'),
        checked: false
      }, {
        label: this.$t('addStore.smokingArea'),
        checked: false
      }, {
        label: this.$t('addStore.teaSnacks'),
        checked: false
      }, {
        label: this.$t('addStore.box'),
        checked: false
      }],
      imgHost: `${this.$imageHost}`,
      // 同城配送信息
      deliveryMessage: {
        deliveryArea: '',
        deliveryPrice: '',
        deliveryPolicy: '',
        deliveryTypeThird: 0,
        deliveryTypeStore: 0,
        regularDeliveryType: 0,
        regularTimeDetail: {
          duration: '',
          type: 1 // 1小时 2日
        }
      },
      // 同城配置信息校验
      deliveryFormRules: {
        pickDetail: [{ required: true, validator: validPickDetail, trigger: 'blur' }],
        deliveryArea: [{ required: true, message: this.$t('addStore.enterDeliveryArea'), trigger: 'blur' }],
        deliveryPrice: [{ required: true, message: this.$t('addStore.enterDeliveryPrice'), trigger: 'blur' }],
        deliveryType: [{ required: true, validator: validDeliveryType, trigger: 'change' }],
        regularDeliveryType: [{ required: true, validator: validRegular, trigger: 'blur' }]
      },
      map: null,
      geocoder: null,
      marker: null,
      markersArray: [],
      address: '',
      thirdDistributionData: [], // 第三方配送公司
      selectedDistribution: [], // 已选择的配送公司
      deliverConfig: null // 基础配置-自提fetch-同城配送cityService
    }
  },
  computed: {
    storeService: {
      get: function () {
        let services = []
        if (this.storeFormInfo.service) {
          services = JSON.parse(this.storeFormInfo.service)
          console.log(services)
        }
        return services
      },
      set: function (val) {
        if (val) {
          this.$set(this.storeFormInfo, 'service', JSON.stringify(val))
        } else {
          this.$set(this.storeFormInfo, 'service', '')
        }
      }
    }
  },
  watch: {
    '$route.query.id': function (newVal) {
      if (newVal) {
        this.id = this.$route.query.id
        this.initStore(this.id)
      } else {
        this.initData()
      }
    },
    selectedDistribution: function (newVal) {
      this.$refs.deliveryForm.validateField('deliveryType')
    }
  },
  mounted () {
    if (this.$route.query.id) {
      this.id = this.$route.query.id
      this.initStore(this.id)
    } else {
      this.initMap()
    }
    delayJudgment(this, this.$route.query.id, this.$t('pageName.editStore'))
    this.initGroup()
    // this.initDelivery()
    this.langDefault()
  },
  methods: {
    initData () {
      this.storeFormInfo = {
        storeName: '',
        manager: '',
        mobile: '',
        businessState: 0,
        businessType: 0,
        openingTime: '',
        closeTime: '',
        group: '',
        storeNumber: '',
        address: '',// 地图定位详细地址
        service: '', // 填写的服务
        storeImgs: [],
        storeDetail: '',
        provinceCode: '',
        cityCode: '',
        districtCode: '',
        latitude: '',
        longitude: '',
        autoPick: 0, // 设定自提
        content: '',
        durationType: 1,
        pickDetail: {
          duration: '',
          type: 1
        }
      }
    },
    initStore (id) {
      let that = this
      let params = {
        storeId: id
      }
      getStore(params).then(res => {
        if (res.error === 0) {
          this.areaLinkage.provinceCode = res.content.provinceCode
          this.areaLinkage.cityCode = res.content.cityCode
          this.areaLinkage.districtCode = res.content.districtCode

          if (res.content.storeImgs) {
            res.content.storeImgs = JSON.parse(res.content.storeImgs)
          } else {
            res.content.storeImgs = []
          }
          if (res.content.service) {
            let services = JSON.parse(res.content.service)
            let servicesMap = services.map(item => {
              return {
                label: item,
                checked: true
              }
            })
            that.serveList = that.uniqObj(that.serveList.concat(servicesMap))
            that.storeService = services
          }
          if (res.content.latitude && res.content.longitude) {
            that.initMap(res.content.latitude, res.content.longitude)
          } else {
            that.initMap()
          }
          let storeFormInfo = Object.assign({}, that.deliveryMessage, that.storeFormInfo, res.content)
          if (!storeFormInfo.pickDetail) {
            storeFormInfo.pickDetail = {
              duration: '',
              type: 1
            }
          }
          // 同城配送信息
          if (storeFormInfo.cityService == 1) {
            that.deliveryMessage = {
              deliveryArea: storeFormInfo.deliveryArea,
              deliveryPrice: storeFormInfo.deliveryPrice,
              deliveryPolicy: storeFormInfo.deliveryPolicy,
              deliveryTypeThird: storeFormInfo.deliveryTypeThird,
              deliveryTypeStore: storeFormInfo.deliveryTypeStore,
              regularDeliveryType: storeFormInfo.regularDeliveryType,
              regularTimeDetail: storeFormInfo.regularTimeDetail || {
                duration: '',
                type: 1
              }
            }
            that.selectedDistribution = storeFormInfo.cityAccountIds || []
          }
          that.storeFormInfo = storeFormInfo
        }
      })
      getDeliveryConfig().then(res => {
        if (res.error === 0) {
          this.deliverConfig = res.content
        } else {
          this.$message.error(res.message)
        }
      })
    },
    initGroup () {
      let that = this
      allStoreGroup().then(res => {
        if (res.error === 0) {
          that.storeGroups = res.content.storeGroups
          that.storeForm.storeId = res.content.storeId
        }
      })
    },
    // 初始化第三方配送接口
    // initDelivery () {
    //   getDeliverys().then(res => {
    //     if (res.error === 0) {
    //       this.thirdDistributionData = res.content
    //     } else {
    //       this.$message.error(res.message)
    //     }
    //   })
    // },
    // 刷新分组
    refreshGroups () {
      this.initGroup()
    },
    // 添加新分组
    addGroups () {
      this.$router.push({ name: 'group_manage' })
    },
    // 添加特色服务
    addServeHandler () {
      if (this.addService === '') {
        this.$message.error({
          message: this.$t('addStore.enterValidCharacter')
        })
      } else {
        let services = this.addService.split(',')
        let servicesMap = services.map(item => {
          return {
            label: item,
            checked: true
          }
        })
        this.storeService = this.storeService.concat(services)
        this.serveList = this.uniqObj(this.serveList.concat(servicesMap))
      }
      this.addService = ''
    },
    // 数组对象去重
    uniqObj (items) {
      var temp = [];
      items.forEach((item, i) => {
        var tag = temp.find(el => {
          return el.label === item.label
        })
        if (!tag) {
          temp.push(item)
        }
      })
      return temp;
    },
    // 区域选择
    areaChangeHandle (data) {
      let address = ''
      if (data.province) {
        for (const key in data) {
          if (data.hasOwnProperty(key)) {
            const area = data[key];
            if (area.name) {
              address += area.name
            }
          }
        }
      }
      this.storeFormInfo.provinceCode = data.province.id
      this.storeFormInfo.cityCode = data.city.id
      this.storeFormInfo.districtCode = data.district.id
      this.storeFormInfo = this.storeFormInfo
      this.address = address
    },
    handleAreaData (val) {
      this.storeFormInfo.provinceCode = val.province
      this.storeFormInfo.cityCode = val.city
      this.storeFormInfo.districtCode = val.district
      let areas = this.$refs.areaLink.areas
      let address = ''
      for (const key in areas) {
        if (areas.hasOwnProperty(key)) {
          const area = areas[key];
          if (area.name) {
            address += area.name
          }
        }
      }
      this.address = address
    },
    // 初始化地图
    initMap (latitude, longitude) {
      let that = this
      let center = new qq.maps.LatLng(39.916527, 116.397128)
      this.map = new qq.maps.Map(this.$refs.storemap, {
        center: center, // 地图的中心地理坐标
        zoom: 13 // 缩放等级
        // minZoom: 13,
        // maxZoom: 13,
        // zoomControl: false
      })
      // 地址解析
      this.geocoder = new qq.maps.Geocoder({
        complete: function (result) {
          console.log('result:', result)
          that.map.setCenter(result.detail.location)
          that.marker = new qq.maps.Marker({
            map: that.map,
            position: result.detail.location
          })
          that.$set(that.storeFormInfo, 'latitude', result.detail.location.lat)
          that.$set(that.storeFormInfo, 'longitude', result.detail.location.lng)
        }
      })
      qq.maps.event.addListener(this.map, 'click', function (e) {
        that.$refs['storeForm'].clearValidate('address')
        if (that.marker) {
          that.marker.setMap(null)
        }
        that.marker = new qq.maps.Marker({
          position: e.latLng,
          map: that.map
        })
      })
      if (latitude && longitude) {
        that.map.panTo(new qq.maps.LatLng(latitude, longitude))
        if (that.marker) {
          that.marker.setMap(null)
        }
        that.marker = new qq.maps.Marker({
          position: new qq.maps.LatLng(latitude, longitude),
          map: that.map
        })
      }
    },
    // 点击地图定位
    codeAddress () {
      this.$refs['storeForm'].clearValidate('address')
      if (!this.address) {
        this.$message.warning(this.$t('addStore.selectRegion'))
        return false
      }
      let address = this.address + this.storeFormInfo.address
      if (this.marker) {
        this.marker.setMap(null)
      }
      this.geocoder.getLocation(address)
    },
    //  添加图片点击事件，弹出图片选择组件
    addStoreImg () {
      this.selfImgDialogShow = !this.selfImgDialogShow
      this.$nextTick(() => this.$http.$emit('dtVisible'))
    },
    // 商品图片点击回调函数
    imgDialogSelectedCallback (imgObj) {
      if (this.storeFormInfo.storeImgs.length + imgObj.length > 5) {
        this.$message.warning(this.$t('addStore.max5'))
        return
      }
      let imgs = imgObj.map(item => item.imgPath)
      this.storeFormInfo.storeImgs = this.storeFormInfo.storeImgs.concat(imgs)
      this.$refs.storeForm.validateField('storeImgs')
    },
    // 删除店面图片
    deleteStoreImg (index) {
      this.storeFormInfo.storeImgs.splice(index, 1)
      this.$refs.storeForm.validateField('storeImgs')
    },
    // 下一步
    nextClickHandler () {
      let that = this
      // 校验 form
      this.$refs.storeForm.validate(function (valid) {
        if (valid) {
          that.stepData.currentStep = 1
        }
      })
    },
    // 上一步
    prevClickHandler () {
      this.stepData.currentStep = 0
    },
    // 保存
    saveClickHandler () {
      let that = this
      that.$refs.deliveryForm.validate((valid) => {
        if (valid) {
          let params = Object.assign({}, this.storeFormInfo, this.deliveryMessage)
          // 同城配送下拉列表对应id
          if (params.cityService === 1 && params.deliveryTypeThird) {
            params.cityAccountIds = this.selectedDistribution || []
          }
          params.storeImgs = params.storeImgs.map(item => {
            if (item.indexOf('//') > -1) {
              item = item.replace(/^http:\/\/[^/]+\//, "")
            } else if (item[0] === '/') {
              item = item.slice(1)
            }
            return item
          })
          params.storeImgs = JSON.stringify(params.storeImgs)
          console.log(params)
          if (!this.id) {
            addStore(params).then((res) => {
              if (res.error === 0) {
                this.$message.success({
                  message: res.message
                })
                this.$router.push({ name: 'store_list' })
              } else {
                this.$message.error(this.$t('addStore.addFaild'))
              }
            })
          } else {
            updateStore(params).then((res) => {
              if (res.error === 0) {
                this.$message.success({
                  message: res.message
                })
                this.$router.push({ name: 'store_list' })
              } else {
                this.$message.error(this.$t('addStore.addFaild'))
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
.storeWrap {
  padding: 10px 10px;
  overflow-y: auto;
}

.storeContent {
  background-color: white;
  padding: 10px 10px 100px 10px;
  position: relative;
}

.el-steps {
  cursor: pointer;
}
.el-input {
  width: 30%;
}
.el-select {
  width: 15%;
}
.el-date-editor {
  width: 45%;
}
.storeImgWrap {
  width: 80px;
  height: 80px;
  border: 1px solid #ccc;
  margin: 5px 5px;
  position: relative;
}
.storeImgWrap .deleteIcon {
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

.storeFooter {
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

.create_content {
  background: #fff;
  padding: 20px 0px;
}
.create_content .containter {
  display: flex;
  margin-bottom: 15px;
}
.content_left {
  margin-right: 20px;
}
.content_left,
.content_right {
  flex: 1;
  background: #f3f7ff;
  padding: 0 20px;
  height: 160px;
  position: relative;
}
.content_left img,
.content_right img {
  position: absolute;
  right: 0;
  bottom: 0;
}
.line1 {
  display: flex;
  align-items: center;
  margin-top: 30px;
  margin-bottom: 10px;
}
.line1 span {
  font-size: 16px;
  color: #333;
  font-weight: bold;
  margin-right: 40px;
}
.line1 a {
  font-size: 14px;
  color: #5a8bff;
  text-decoration: none;
}
.line2 {
  font-size: 14px;
  line-height: 24px;
  color: #999;
  height: 40px;
}
.line3 {
  position: absolute;
  bottom: 20px;
}

.deliveryMsg {
  margin-left: 30px;
}
.deliveryMsg .el-form-item {
  margin-bottom: 18px;
}
.store-map {
  width: 600px;
  height: 400px;
  border: 1px solid #ccc;
  margin-top: 5px;
}
.edit-wrap {
  width: 600px;
  height: 400px;
}
.pick-up-time {
  /deep/ .el-radio {
    margin-right: 0;
  }
}
/deep/ .tableClss th {
  box-sizing: border-box;
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  line-height: 1.4;
  padding: 8px 10px;
  color: #333;
}
</style>
