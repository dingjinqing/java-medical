'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // API_DOMAIN: '"locahost:8086"',
  API_DOMAIN: '"medicaldev.weipubao.cn"',
  IMAGE_DOMAIN: '"medicaldevimg.weipubao.cn"'
})
