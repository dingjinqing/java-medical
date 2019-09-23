<template>
  <div
    class="main"
    :class="{hide:!showStatus}"
  >
    <div
      class="view"
      @click="toogleShow"
    >{{showStatus ? '收起':'打开'}}</div>
    <div class="step_content">
      <div class="item_content">
        <div class="item_head">
          <div class="item_line"></div>
          <div class="item_text">1</div>
        </div>
        <div class="item_main">
          <div class="item_title">买家已下单</div>
          <div class="item_description">2019-09-18 09:27:50</div>
        </div>
      </div>
      <div class="item_content">
        <div class="item_head">
          <div class="item_line"></div>
          <div class="item_text">2</div>
        </div>
        <div class="item_main">
          <div class="item_title">买家已下单</div>
          <div class="item_description">2019-09-18 09:27:50</div>
        </div>
      </div>
      <div class="item_content">
        <div class="item_head">
          <div class="item_line"></div>
          <div class="item_text">2</div>
        </div>
        <div class="item_main">
          <div class="item_title">买家已下单</div>
          <div class="item_description">2019-09-18 09:27:50</div>
        </div>
      </div>
      <div class="item_content">
        <div class="item_head">
          <div class="item_line"></div>
          <div class="item_text">2</div>
        </div>
        <div class="item_main">
          <div class="item_title">买家已下单</div>
          <div class="item_description">2019-09-18 09:27:50</div>
        </div>
      </div>
      <div class="item_content is_end">
        <div class="item_head">
          <div class="item_line"></div>
          <div class="item_text">3</div>
        </div>
        <div class="item_main">
          <div class="item_title">买家已下单</div>
          <div class="item_description">2019-09-18 09:27:50</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      showStatus: true
    }
  },
  props: {
    orderSatus: {
      type: Object
    }
  },
  methods: {
    toogleShow () {
      this.showStatus = !this.showStatus
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  position: fixed;
  right: 0;
  width: 215px;
  top: 140px;
  bottom: 0;
  margin-bottom: 0 !important;
  display: flex;
  align-items: center;
  transition: right 0.3s ease-in;
  padding: 0;
  z-index: 2;
  &.hide {
    right: -180px;
  }
  .view {
    width: 35px;
    cursor: pointer;
  }
  .step_content {
    flex: 1;
    display: flex;
    flex-direction: column;
    height: 100%;
    background-color: #fff;
    padding: 10px 0 10px 10px;
    .item_content {
      flex-shrink: 1;
      flex-basis: 50%;
      display: flex;
      color: skyblue;
      &.is_end {
        flex-basis: auto !important;
        flex-shrink: 0;
        flex-grow: 0;
      }
      &:last-of-type {
        .item_line {
          display: none;
        }
      }
      .item_head {
        width: 24px;
        position: relative;
        .item_line {
          position: absolute;
          width: 2px;
          top: 0;
          bottom: 0;
          left: 11px;
          background-color: #c0c4cc;
        }
        .item_text {
          border-radius: 50%;
          border: 2px solid;
          border-color: inherit;
          position: relative;
          z-index: 1;
          display: inline-flex;
          justify-content: center;
          align-items: center;
          width: 24px;
          height: 24px;
          font-size: 14px;
          box-sizing: border-box;
          background: #fff;
          transition: 0.15s ease-out;
          font-weight: 600;
          line-height: 1;
          color: inherit;
        }
      }
      .item_main {
        flex: 1;
        padding-left: 10px;
        .item_title {
          line-height: 24px;
          padding-bottom: 8px;
        }
        .item_description {
          margin-top: -5px;
          font-size: 12px;
          line-height: 20px;
          font-weight: 400;
        }
      }
    }
  }
}
</style>
