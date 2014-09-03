@echo off
setlocal enabledelayedexpansion

for /R flex_src %%i in (*.mxml) do (
move /y "%%i" "tmp" > nul
"iconv.exe" -cs -f GBK -t UTF-8 "tmp" >> "%%i"
)

endlocal
