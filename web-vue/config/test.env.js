'use strict'
const merge = require('webpack-merge')
const devEnv = require('./dev.env')

module.exports = merge(devEnv, {
  NODE_ENV: '"testing"',
  FILE_NAME: '"test"',
  API_DOMAIN: '"localhost:8086"',
  IMAGE_DOMAIN: '"localhost:8080"'
})
