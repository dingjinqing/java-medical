<template>
  <wrapper>
    <div class="content">
      <div class="main">
        <el-form
          :model="form"
          label-width="150px"
          labelPosition='right'
          :rules="formRules"
          ref="form"
        >
          <!-- 活动名称 -->
          <el-form-item
            label="活动名称"
            prop="actName"
          >
            <el-input
              size="small"
              placeholder="请输入活动名称"
              class="morelength"
              v-model="form.actName"
            ></el-input>
          </el-form-item>
          <!-- 选择人群 -->
          <el-form-item
            label="选择人群"
            prop="people"
          >
            <div class="gray">以下筛选条件为"或"的关系</div>
            <el-checkbox-group v-model="form.checkList">
              <el-checkbox label="加购人群"></el-checkbox>
              <div class="gray">30天内在本店内有加入购物车行为，但没有支付的用户</div><br>
              <el-checkbox label="购买指定商品人群"></el-checkbox><br>
              <el-checkbox label="持有 会员卡人群"></el-checkbox><br>
              <el-checkbox label="属于 标签人群"></el-checkbox><br>
              <el-checkbox label="选择指定的会员 已选择会员 0 人"></el-checkbox><br>
              <el-checkbox label="自定义"></el-checkbox><br>
            </el-checkbox-group>
          </el-form-item>
          <!-- 选择优惠券 -->
          <el-form-item
            label="选择优惠券"
            prop="coupon"
          >
            <div class="gray">最多添加5张优惠券，已过期和已停用的优惠券不能添加</div><br>
          </el-form-item>
          <!-- 发送时间 -->
          <el-form-item
            label="发送时间"
            prop=""
          >
            <el-radio
              v-model="form.sendAction"
              label="0"
            >立即发送</el-radio>
            <br>
            <el-radio
              v-model="form.sendAction"
              label="1"
            >定时发送</el-radio>
            <el-date-picker
              v-model="form.startTime"
              type="datetime"
              class="morelength"
              size="small"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <div class="footer">
        <el-button
          type="primary"
          size="small"
          @click="addAct"
        >确认发放</el-button>
      </div>
    </div>
  </wrapper>
</template>>

<script>
// import { mapActions } from 'vuex'
import wrapper from '@/components/admin/wrapper/wrapper'
// import choosingGoods from '@/components/admin/choosingGoods'
import { addActivity } from '@/api/admin/marketManage/couponGift.js'
// // import { updateCoupon } from '@/api/admin/marketManage/couponList.js'
// // import { selectGoodsApi } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods.js'
export default {
  components: {
    wrapper
    // choosingGoods,
    // AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  data () {
    return {
      // 表单
      form: {
        actName: '',
        checkList: [],
        sendAction: '',
        startTime: ''
      },
      // 表单约束
      formRules: {
        actName: [
          { required: true, message: '此处不能为空!', trigger: 'blur' }
        ],
        sendAction: [
          { required: true, message: '此处不能为空!', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    addAct () {
      let addParam = {
        'actName': this.form.actName,
        'sendAction': this.form.sendAction,
        'startTime': this.form.startTimes
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          addActivity(addParam).then(res => {
            console.log(res)
            if (res.error === 0) {
              alert('操作成功')
              this.$router.push({
                name: 'couponGift'
              })
            }
          }).catch(() => {
            this.$message.error('操作失败')
          })
        } else {
          this.$message.error('数据不符合要求')
          return false
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
.inputTip {
  color: #999;
  margin-left: 15px;
}
.coupon_info {
  display: flex;
  flex-direction: column;
  width: 200%;
  line-height: 25px;
  margin: 0 auto;
  margin-bottom: -10px;
  .coupon_item {
    margin-bottom: 10px;
    height: 50px;
    display: flex;
    > div {
      background-color: #fff;
      height: 100px;
    }
    .coupon_left {
      width: 100px;
      text-align: center;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      .coupon_price {
        color: #f66;
        margin-bottom: 10px;
        > span {
          font-size: 18px;
          font-weight: 600;
        }
      }
      .coupon_rule {
        color: #999;
        font-size: 14px;
      }
    }
    .coupon_middle {
      width: 25px;
      background: none;
      > img {
        width: 100%;
        height: 100%;
      }
    }
    .coupon_right {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: flex-start;
      padding-left: 10px;
      position: relative;
      overflow: hidden;
      .coupon_name {
        font-weight: bold;
        font-size: 14px;
        margin-bottom: 5px;
        width: 100%;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }
      .coupon_limits {
        margin-bottom: 15px;
        font-size: 13px;
      }
      .coupon_time {
        color: #999;
        font-size: 12px;
      }
      .coupon_icon {
        position: absolute;
        right: -15px;
        top: 8px;
        background: #fead2d;
        width: 64px;
        font-size: 12px;
        color: #fff;
        transform: rotate(40deg);
        text-align: center;
      }
    }
  }
}
.content {
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  padding-bottom: 50px;
  .main {
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    .el-form-item {
      margin-bottom: 16px;
    }
    .el-input {
      width: 90px;
    }
    .morelength {
      width: 200px;
    }
    .chooseGoods {
      width: 120px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      border: 1px solid #ccc;
    }
    .gray {
      color: #999;
    }
  }
}
.ImgWrap {
  width: 80px;
  height: 80px;
  border: 1px solid #ccc;
  margin: 5px 5px;
  position: relative;
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
  position: relative;
  top: -41px;
  right: -95px;
  cursor: pointer;
  opacity: 0.8;
}
.ImgWrap .moveIcon {
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
.ImgWrap:hover .moveIcon {
  display: block;
}
.selectedWrap {
  min-width: 70px;
  height: 22px;
  border: 1px solid #ccc;
  line-height: 22px;
  text-align: center;
  padding: 0px 5px;
  margin: 0px 5px;
  background-color: #fff;
  position: relative;
}
.footer {
  padding: 10px 0px 10px 0px;
  text-align: center;
  background: #f8f8f8;
  margin-top: 10px;
  position: fixed;
  bottom: 0;
  z-index: 1;
  width: 100%;
}
</style>
