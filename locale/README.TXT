compc -locale=zh_CN -source-path=frameworks\projects\framework\bundles\zh_CN\src -include-resource-bundles=collections,containers,controls,core,effects,formatters,logging,SharedResources,skins,states,styles,utils,validators -output=frameworks/locale/zh_CN/framework_rb.swc

mxmlc -locale=en_US -source-path=locale/{locale} -include-resource-bundles=collections,containers,controls,core,effects,RegistrationForm,skins,styles -output=src/RegistrationForm_en_US.swf
mxmlc -locale=zh_CN -source-path=locale/{locale} -include-resource-bundles=collections,containers,controls,core,effects,RegistrationForm,skins,styles -output=src/RegistrationForm_zh_CN.swf

mxmlc -locale=en_US -source-path=locale/{locale} -include-resource-bundles=collections,containers,controls,core,effects,gcc,skins,styles -output=flex_src/gcc_en_US.swf
mxmlc -locale=zh_CN -source-path=locale/{locale} -include-resource-bundles=collections,containers,controls,core,effects,gcc,skins,styles -output=flex_src/gcc_zh_CN.swf
