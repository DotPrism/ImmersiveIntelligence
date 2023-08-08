@echo off
echo.
set optipng="oxipng.exe"

for /R "src\main\resources\assets\immersiveintelligence\textures" %%f in (*.png) do %optipng% %%f --zc 9 -f 0-5 --nc --strip all