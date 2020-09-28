<template>
  <div id="v-star">
    <div
      :style="mainStyle"
      class="main"
    >
      <div class="star_line">
        <span
          class="star"
          :style="starStyle"
          v-for="star in total"
          :key="star.index"
        >☆</span>
      </div>
      <div
        class="star_line"
        :style="blackStyle"
      >
      <div style="display:flex;height:auto;">
              <span
                class="star"
                :style="starStyle"
                v-for="star in total"
                :key="star.index"
              >★</span>
      </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    total: {
      default: 5
    },
    size: {
      default: 20
    },
    color: {
      default: '#ff6666'
    },
    value: {}
  },
  computed: {
    mainStyle () {
      return {
        width: this.size * this.total + 'px',
        height: this.size + 'px'
      }
    },
    starStyle () {
      return {
        width: this.size + 'px',
        height: this.size + 'px',
        fontSize: this.size + 'px',
        color: this.color
      }
    },
    blackStyle () {
      return {
        width: this.value / this.total * 100 + '%'
      }
    }
  },
  data () {
    return {
      score: 1
    }
  },

  methods: {

  }
}
</script>

<style lang="scss" scoped>
#v-star{
    display: flex;
    justify-content: center;
}
.main {
  position: relative;
}
.star_line {
  width-space: nowrap;
  overflow: hidden;
  position: absolute;
  display: flex;
  justify-content: flex-start;
}
.star {
  display: inline-block;
}
</style>
