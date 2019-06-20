#!/bin/bash

webdir=/data/webroot/jmpweb/
source=/data/svn_source/mp_java/web-vue
cd ${source}
npm run build
rsync -av ${source}/dist/ ${webdir}
