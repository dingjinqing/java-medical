<template>
  <div class="storeWrap">
    <div
      v-if="reload"
      id="storeDiv"
      class="storeContent"
    >
      <!-- 头部导航 headerSteps-->
      <el-steps
        :active="stepData.currentStep"
        simple
      >
        <el-step
          :title="step1"
          icon="el-icon-edit"
        ></el-step>
        <el-step
          :title="step2"
          icon="el-icon-edit"
        ></el-step>
      </el-steps>

      <!-- 主要内容区 -->
      <el-form
        v-if="this.stepData.currentStep == 0"
        ref="storeForm"
        :model="storeFormInfo"
        :rules="storeFormRules"
        label-width="120px"
        style="margin-top: 20px;"
        size="small"
      >
        <el-form-item
          label="门店名称："
          prop="storeName"
        >
          <el-input
            v-model="storeFormInfo.storeName"
            placeholder="请输入门店名称"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="负责人："
          prop="manager"
        >
          <el-input
            v-model="storeFormInfo.manager"
            placeholder="请输入负责人"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="联系电话："
          prop="mobile"
        >
          <el-input
            v-model="storeFormInfo.mobile"
            placeholder="请输入联系电话"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="营业状态："
          prop="businessState"
        >
          <el-radio-group v-model="storeFormInfo.businessState">
            <el-radio :label="1">营业中</el-radio>
            <el-radio :label="0">关店</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="营业时间："
          prop="businessType"
        >
          <el-radio-group v-model="storeFormInfo.businessType">
            <el-radio :label="1">每天</el-radio>
            <el-radio :label="0">工作日</el-radio>
          </el-radio-group>
          <el-time-picker
            v-model="storeFormInfo.openingTime"
            placeholder="开始时间"
            style="width: 12%;margin-left: 20px;"
          ></el-time-picker>
          <span>-</span>
          <el-time-picker
            v-model="storeFormInfo.closeTime"
            placeholder="结束时间"
            style="width: 12%;"
          ></el-time-picker>
          <p style="margin-left: 172px; color: #a0a0a0;">24小时制，如 9:00-21:00</p>
        </el-form-item>
        <el-form-item
          label="所属分组："
          prop="storeGroups"
        >
          <el-select
            v-model="storeFormInfo.storeGroups"
            placeholder="请选择分组"
          >
            <el-option
              label="请选择所属分组"
              value=""
            ></el-option>
          </el-select>
          <el-button
            type="text"
            @click="refreshGroups"
          >刷新</el-button>
          <span>|</span>
          <el-button
            type="text"
            @click="addGroups"
          >添加新分组</el-button>
        </el-form-item>
        <el-form-item
          label="门店编号："
          prop="storeNum"
        >
          <el-input
            v-model="storeFormInfo.storeNum"
            placeholder="请输入门店编号"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="地理位置："
          prop="storeSite"
        >
          <div>
            <areaLinkage
              @areaData="handleAreaData"
              @areaChange="areaChangeHandle"
            />
          </div>
        </el-form-item>
        <el-form-item
          label="地图定位："
          prop="storePosition"
        >
          <el-input
            placeholder="输入详细的位置信息，请勿重复填写省市区"
            v-model="storeFormInfo.storePosition"
          ></el-input>
          <el-button
            type="text"
            @click="codeAddress"
          >地图定位</el-button>
          <div
            class="store-map"
            ref="storemap"
          ></div>
        </el-form-item>
        <el-form-item
          label="特色服务："
          prop="storeServe"
        >
          <el-checkbox-group v-model="storeFormInfo.storeServe">
            <el-checkbox
              v-for="(item, index) in serveList"
              :key="index"
              :label="item.label"
              name="type"
              :checked="item.checked"
            ></el-checkbox>
          </el-checkbox-group>
          <el-input
            placeholder="添加多个服务，用逗号隔开"
            v-model="storeFormInfo.service"
          ></el-input>
          <el-button
            type="text"
            @click="addServeHandler"
          >添加</el-button>
        </el-form-item>
        <el-form-item
          label="店面宣传照："
          prop="storePhoto"
        >
          <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
            <div
              v-for="(item,index) in storeFormInfo.storeImgs"
              :key="index"
              class="storeImgWrap"
            >
              <el-image
                fit="cover"
                :src="item.imgUrl"
                style="width: 78px; height: 78px;"
              ></el-image>
              <span
                class="deleteIcon"
                @click="deleteStoreImg(index)"
              >×</span>
            </div>
            <div
              class="storeImgWrap"
              @click="addStoreImg"
              v-if="storeFormInfo.storeImgs.length < 5"
            >
              <el-image
                fit="scale-down"
                :src="imgHost+'/image/admin/add_img.png'"
                style="width: 78px; height: 78px;cursor: pointer;"
              />
            </div>
            <span style="color: #999;">最多可上传6张图，每张大小不可超过5M，格式要求为jpg，png</span>
          </div>
        </el-form-item>
        <el-form-item
          label="店面详情："
          prop="storeDetail"
        >
          <TinymceEditor />
        </el-form-item>
      </el-form>
      <!--图片dialog-->
      <ImageDalog
        :tuneUp="selfImgDialogShow"
        pageIndex='pictureSpace'
        @handleSelectImg='imgDialogSelectedCallback'
      />

      <div
        v-if="this.stepData.currentStep == 1"
        class="create_content"
        style="display: block;"
      >
        <div class="containter">
          <div class="content_left">
            <img
              src="@/assets/image/admin/left_bg.png"
              alt=""
            >
            <div class="line1">
              <span>门店自提</span>
              <a href="javascript:void(0);">查看门店自提功能使用教程</a>
            </div>
            <div class="line2">开启后，用户在商城购买该门店的商品，可选择到当前门店自提。</div>
            <div class="line3">
              <el-switch
                v-model="switchLeft"
                active-color="#E6A23C"
                inactive-color="#ccc"
              ></el-switch>&nbsp;&nbsp;&nbsp;&nbsp;
              <span v-if="this.switchLeft == true">已开启</span>
              <span v-if="this.switchLeft == false">已关闭</span>
            </div>
          </div>
          <div class="content_right">
            <img
              src="@/assets/image/admin/right_bg.png"
              alt=""
            >
            <div class="line1">
              <span>同城配送</span>
              <a href="javascript:void(0);">查看门店同城配送使用教程</a>
            </div>
            <div class="line2">开启后，用户在商城购买该门店的商品，可选择同城配送，由商家（或第三方同城配送平台）提供上门配送服务。</div>
            <div class="line3">
              <el-switch
                v-model="switchRight"
                active-color="#E6A23C"
                inactive-color="#ccc"
              ></el-switch>&nbsp;&nbsp;&nbsp;&nbsp;
              <span v-if="this.switchRight == true">已开启</span>
              <span v-if="this.switchRight == false">已关闭</span>
            </div>
          </div>
        </div>
      </div>
      <!-- 同城配送信息 -->
      <el-form
        v-if="this.stepData.currentStep == 1 && this.switchRight == true"
        ref="deliveryForm"
        :model="deliveryMessage"
        :rules="deliveryFormRules"
        class="deliveryMsg"
      >
        <el-form-item label="取货地址：">
          <span>北京市北京市石景山区石景山</span><br />
          <span style="color: #999; font-size: 14px;margin-left: 6%;">用户下单后自提商品以及同城配送业务骑手取货地点，如需修改，请到【门店基础信息】页面进行编辑</span>
        </el-form-item>
        <el-form-item
          label="配送区域："
          prop="deliveryArea"
        >
          门店周边&nbsp;&nbsp;<el-input
            v-model="deliveryMessage.deliveryArea"
            style="width: 80px;"
          ></el-input>&nbsp;&nbsp;公里内可配送
        </el-form-item>
        <el-form-item
          label="配送价格："
          prop="deliveryPrice"
        >
          <el-input
            v-model="deliveryMessage.deliveryPrice"
            style="width: 80px;"
          ></el-input>&nbsp;&nbsp;元
        </el-form-item>
        <el-form-item
          label="包邮策略："
          prop="deliveryPolicy"
        >
          订单付款金额满&nbsp;&nbsp;<el-input
            v-model="deliveryMessage.deliveryPolicy"
            style="width: 80px;"
          ></el-input>&nbsp;&nbsp;元包邮
        </el-form-item>
        <el-form-item
          label="配送方式："
          prop="deliveryType"
        >
          <el-checkbox-group v-model="deliveryMessage.deliveryType">
            <el-checkbox name="deliveryType">商家自配送&nbsp;&nbsp;<span style="color: #999;">(由商家自行配送，系统无法追踪物流动态并及时同步给用户)</span></el-checkbox><br />
            <el-checkbox
              name="deliveryType"
              style="margin-left: 7%;"
            >第三方同城配送&nbsp;&nbsp;<span style="color: #999;">(前提：商家需先和配送公司已达成合作关系，已签约账号才可使用第三方同城配送服务)</span></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>

      <!-- 底部按钮组件 -->
      <div class="storeFooter">
        <el-button
          size="small"
          v-if="this.stepData.currentStep == 0"
          @click="nextClickHandler"
        >下一步</el-button>
        <el-button
          size="small"
          v-if="this.stepData.currentStep == 1"
          @click="prevClickHandler"
        >上一步</el-button>
        <el-button
          type="primary"
          size="small"
          v-if="this.stepData.currentStep == 1"
          @click="saveClickHandler"
        >保存</el-button>
      </div>

    </div>
  </div>
</template>
<!-- 腾讯地图 -->
<script charset="utf-8" src="https://map.qq.com/api/js?v=2.exp&key=YPOBZ-DNIKF-Y6KJM-NDW7D-VYIFZ-QEBIO"></script>
<script>
import { addStore } from '@/api/admin/storeManage/store'
/* 组件导入 */

export default {
  components: {
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue'),
    ImageDalog: () => import('@/components/admin/imageDalog'),
    TinymceEditor: () => import('@/components/admin/imageDalog')
  },
  data() {
    return {
      reload: true,
      stepData: {
        currentStep: 0
      },
      step1: '门店基础信息',
      step2: '门店配送信息',
      storeFormInfo: {
        storeName: '',
        manager: '',
        mobile: '',
        businessState: '',
        businessType: '',
        openingTime: '',
        closeTime: '',
        storeGroups: [],
        storeNum: '',
        storeSite: '',
        storePosition: '',// 地图定位详细地址
        storeServe: [],
        service: '',
        storePhoto: '',
        storeImgs: [],
        storeDetail: ''
      },
      storeFormRules: {
        storeName: [{ required: true, message: '请输入门店名称', trigger: 'blur' }],
        manager: [{ required: true, message: '请输入负责人', trigger: 'blur' }],
        mobile: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
        businessType: [{ required: true, message: '请选择营业时间', trigger: 'change' }],
        storeNum: [{ required: true, message: '请输入门店编号', trigger: 'blur' }],
        storeSite: [{ required: true, message: '请输入门店编号', trigger: 'blur' }],
        storePosition: [{ required: true, message: '请输入定位信息', trigger: 'blur' }],
        storePhoto: [{ required: true }]
      },
      selfImgDialogShow: false,
      // 特色服务列表
      serveList: [{
        label: 'WIFI',
        checked: false
      }, {
        label: '停车位',
        checked: false
      }, {
        label: '无烟区',
        checked: false
      }, {
        label: '茶水小食',
        checked: false
      }, {
        label: '包厢',
        checked: false
      }],
      imgHost: `${this.$imageHost}`,
      // 配送信息按钮
      switchLeft: false,
      switchRight: false,
      // 同城配送信息
      deliveryMessage: {
        deliveryArea: '',
        deliveryPrice: '',
        deliveryPolicy: '',
        deliveryType: []
      },
      // 同城配置信息校验
      deliveryFormRules: {
        deliveryArea: [{ required: true, message: '请输入配送区域', trigger: 'blur' }],
        deliveryPrice: [{ required: true, message: '请输入配送价格', trigger: 'blur' }],
        deliveryType: [{ required: true, message: '请选择配送方式', trigger: 'change' }]
      },
      map: null,
      geocoder: null,
      marker: null,
      markersArray: [],
      address: ''
    }
  },
  mounted() {
    this.initMap()
    console.log(this.$refs)
  },
  methods: {
    // 刷新分组
    refreshGroups() {

    },
    // 添加新分组
    addGroups() {
      this.$router.push({ name: 'group_manage' })
    },
    // 添加特色服务
    addServeHandler() {
      if (this.storeFormInfo.service === '') {
        this.$message.error({
          message: '请输入有效的字符！'
        })
      } else {
        console.log(this.storeFormInfo.service)
        this.serveList.push({
          label: this.storeFormInfo.service,
          checked: true
        })
      }
      this.storeFormInfo.service = ''
    },
    // 区域选择
    handleAreaData(data) {
      console.log(data)
    },
    areaChangeHandle(data) {
      console.log(data)
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
      console.log(address)
      this.address = address
    },
    // 初始化地图
    initMap() {
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
          console.log('2222', result)
          that.map.setCenter(result.detail.location)
          that.marker = new qq.maps.Marker({
            map: that.map,
            position: result.detail.location
          })
        }
      })
      console.log(this.geocoder)
      qq.maps.event.addListener(this.map, 'click', function (e) {
        if (that.marker) {
          that.marker.setMap(null)
        }
        that.marker = new qq.maps.Marker({
          position: e.latLng,
          map: that.map
        })
      })
    },
    // 点击地图定位
    codeAddress() {
      if (!this.address) {
        this.$message.warning('请选择地区')
        return false
      }
      let address = this.address + this.storeFormInfo.storePosition
      console.log(address)
      console.log(this.marker)
      if (this.marker) {
        this.marker.setMap(null)
      }
      this.geocoder.getLocation(address)
    },
    //  添加图片点击事件，弹出图片选择组件
    addStoreImg() {
      this.selfImgDialogShow = !this.selfImgDialogShow
      this.$nextTick(() => this.$http.$emit('dtVisible'))
    },
    // 商品图片点击回调函数
    imgDialogSelectedCallback(imgObj) {
      if (this.storeFormInfo.storeImgs.length >= 5) {
        return
      }
      this.storeFormInfo.storeImgs.push({ imgPath: imgObj.imgPath, imgUrl: imgObj.imgUrl })
    },
    // 删除店面图片
    deleteStoreImg(index) {
      this.storeFormInfo.storeImgs.splice(index, 1)
    },
    // 下一步
    nextClickHandler() {
      this.stepData.currentStep = 1
    },
    // 上一步
    prevClickHandler() {
      this.stepData.currentStep = 0
    },
    // 保存
    saveClickHandler() {
      let params = Object.assign({}, this.storeFormInfo)
      addStore(params).then((res) => {
        if (res.error === 0) {
          this.$message.success({
            message: res.message
          })
          this.$router.push({ name: 'store_list' })
        }
      })
    }
  }
}
</script>
<style scoped>
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
.tinymceEditor {
  width: 70% !important;
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
  margin-bottom: 10px;
}
.store-map {
  width: 600px;
  height: 400px;
  border: 1px solid #ccc;
  margin-top: 5px;
  margin-bottom: 20px;
}
</style>
