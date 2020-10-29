#!/bin/bash

sed -i~ "/<servers>/ a\
<server>\
  <id>private-repo</id>\
  <username>$maven</username>\
  <password>$maven</password>\
</server>" /usr/share/maven/conf/settings.xml

sed -i "/<profiles>/ a\
<profile>\
  <id>private-repo</id>\
  <activation>\
    <activeByDefault>true</activeByDefault>\
  </activation>\
  <repositories>\
    <repository>\
      <id>private-repo</id>\
      <url>https://bitbucket.org/jcarlosreis/iadiproject2020/src/master/</url>\
    </repository>\
  </repositories>\
  <pluginRepositories>\
    <pluginRepository>\
      <id>private-repo</id>\
      <url>https://bitbucket.org/jcarlosreis/iadiproject2020/src/master/</url>\
    </pluginRepository>\
  </pluginRepositories>\
</profile>" /usr/share/maven/conf/settings.xml