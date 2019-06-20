#!/bin/bash

webdir=/data/webroot/jmpweb/
source=/data/svn_source/mp_java/web-vue
cd ${source}
npm install
npm run build
rsync -av ${source}/dist/ ${webdir}
