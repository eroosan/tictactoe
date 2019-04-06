#!/bin/bash

VERSION=`sed -n '/<version>/,/<version>/p' ./pom.xml | head -1 | cut -c 11-15`
VERSION=${VERSION}".nightly."$(date +%Y%m%d)
echo ${VERSION}
