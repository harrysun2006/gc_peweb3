#!/bin/bash
for i in `find ./flex_src -name 'hr.mxml' -type f ` ; do
  if [ -s $i ]
  then
  	echo $i
  	./iconv.exe $i
  fi
done
