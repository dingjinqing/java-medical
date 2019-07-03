<template>
  <div class="develop_container">
    <div class="container">
      <h2>{{$t('development.title')}}</h2>
      <ul>
        <li
          @mouseenter="enter(1)"
          @mouseleave="leave(1)"
        >
          <b></b>
          <div
            class="before"
            :class='left_active1'
          >
            2017
          </div>
          <div
            class="after"
            :class='right_active1'
          >
            {{$t('development.p_1_1')}}<br>
            {{$t('development.p_1_2')}}<br>
            {{$t('development.p_1_3')}}<br>
            {{$t('development.p_1_4')}}
          </div>
        </li>
        <li
          class="clearfix"
          style="height:150px;"
          @mouseenter="enter(2)"
          @mouseleave="leave(2)"
        >
          <b></b>
          <div
            class="before"
            :class='left_active2'
          >
            2016
          </div>
          <div
            class="after"
            :class='right_active2'
          >
            {{$t('development.p_2_1')}}<br>
            {{$t('development.p_2_2')}}<br>
            {{$t('development.p_2_3')}}<br>
            {{$t('development.p_2_4')}}
          </div>
        </li>
        <li
          class="clearfix"
          style="height:123px;"
          @mouseenter="enter(3)"
          @mouseleave="leave(3)"
        >
          <b></b>
          <div
            class="before"
            :class='left_active3'
          >
            2015
          </div>
          <div
            class="after"
            :class='right_active3'
          >
            {{$t('development.p_3_1')}}<br>
            {{$t('development.p_3_2')}}<br>
            {{$t('development.p_3_3')}}
          </div>
        </li>
        <li
          class="clearfix"
          style="height:128px;"
          @mouseenter="enter(4)"
          @mouseleave="leave(4)"
        >
          <b></b>
          <div
            class="before"
            :class='left_active4'
          >
            2014
          </div>
          <div
            class="after"
            :class='right_active4'
          >
            {{$t('development.p_4_1')}}<br>
            {{$t('development.p_4_2')}}<br>
            {{$t('development.p_4_3')}}
          </div>
        </li>
        <li
          class="clearfix"
          style="height:128px;"
          @mouseenter="enter(5)"
          @mouseleave="leave(5)"
        >
          <b></b>
          <div
            class="before"
            :class='left_active5'
          >
            2013
          </div>
          <div
            class="after"
            :class='right_active5'
          >
            {{$t('development.p_5_1')}}<br>
            {{$t('development.p_5_2')}}<br>
            {{$t('development.p_5_3')}}
          </div>
        </li>
        <li
          class="clearfix"
          @mouseenter="enter(6)"
          @mouseleave="leave(6)"
        >
          <b></b>
          <div
            class="before"
            :class='left_active6'
          >
            2012
          </div>
          <div
            class="after"
            :class='right_active6'
          >
            {{$t('development.p_6_1')}}<br>
            {{$t('development.p_6_2')}}
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      left_active1: '',
      right_active1: '',
      left_active2: '',
      right_active2: '',
      left_active3: '',
      right_active3: '',
      left_active4: '',
      right_active4: '',
      left_active5: '',
      right_active5: '',
      left_active6: '',
      right_active6: ''

    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {

    // 鼠标划入事件
    enter (index) {
      switch (index) {
        case 1:
          this.left_active1 = 'left_active'
          this.right_active1 = 'right_active'
          break
        case 2:
          this.left_active2 = 'left_active'
          this.right_active2 = 'right_active'
          break
        case 3:
          this.left_active3 = 'left_active'
          this.right_active3 = 'right_active'
          break
        case 4:
          this.left_active4 = 'left_active'
          this.right_active4 = 'right_active'
          break
        case 5:
          this.left_active5 = 'left_active'
          this.right_active5 = 'right_active'
          break
        case 6:
          this.left_active6 = 'left_active'
          this.right_active6 = 'right_active'
          break
      }
    },
    // 鼠标划出事件
    leave (index) {
      switch (index) {
        case 1:
          this.left_active1 = ''
          this.right_active1 = ''
          break
        case 2:
          this.left_active2 = ''
          this.right_active2 = ''
          break
        case 3:
          this.left_active3 = ''
          this.right_active3 = ''
          break
        case 4:
          this.left_active4 = ''
          this.right_active4 = ''
          break
        case 5:
          this.left_active5 = ''
          this.right_active5 = ''
          break
        case 6:
          this.left_active6 = ''
          this.right_active6 = ''
          break
      }
    }

  }
}
</script>
<style scoped>
.develop_container {
  background-color: #f7f8f9;
  text-align: center;
  padding-top: 70px;
}
.container {
  width: 1166px;
  margin: 0 auto;
  padding: 0 13px;
}
.container h2 {
  font-size: 22px;
  font-weight: normal;
}
.container ul {
  margin-top: 60px;
  margin-left: 160px;
  cursor: pointer;
}
.container ul li {
  border-left: 3px solid #70affe;
  position: relative;
  text-align: left;
  margin-left: -60px;
  width: 100%;
  height: 150px;
}
.container ul li:nth-of-type(6) {
  height: 80px;
  margin-bottom: 50px;
}
.container ul li b::before {
  content: "";
  position: absolute;
  left: -5px;
  top: -8px;
  width: 8px;
  height: 8px;
  border-radius: 5px;
  background-color: #70affe;
}
.before {
  position: absolute;
  color: #fff;
  top: -12px;
  left: -100px;
  width: 82px;
  height: 26px;
  background-color: #70affe;
  border-radius: 12px;
  line-height: 26px;
  padding-left: 15px;
}
.before:after {
  content: url(../../../assets/aboutUsImg/05.png);
  position: absolute;
  top: 4px;
  margin-left: 3px;
}
.after {
  position: absolute;
  color: #333;
  font-size: 14px;
  top: 0;
  left: 40px;
  line-height: 28px;
  background-color: #fff;
  width: 1028px;
  padding: 10px;
}
.left_active {
  background-color: #ffae00;
}
.right_active {
  background-color: #f2f9ff;
  border: 1px solid #94c5f3;
}
</style>
