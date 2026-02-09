@echo off
echo ========================================
echo 超市管理系统快速启动脚本
echo ========================================

echo.
echo 正在检查环境...

REM 检查Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 未找到Java，请先安装Java 17
    pause
    exit /b 1
)

REM 检查Node.js
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 未找到Node.js，请先安装Node.js 20.19.0+
    pause
    exit /b 1
)

echo [成功] 环境检查通过
echo.

echo 请选择要启动的服务：
echo 1. 启动后端服务 (Spring Boot)
echo 2. 启动前端服务 (Vue)
echo 3. 启动前后端服务
echo 4. 退出
echo.

set /p choice=请输入选择 (1-4): 

if "%choice%"=="1" goto start_backend
if "%choice%"=="2" goto start_frontend
if "%choice%"=="3" goto start_both
if "%choice%"=="4" goto exit
goto invalid_choice

:start_backend
echo.
echo 正在启动后端服务...
cd Supermarket
start "后端服务" cmd /k "mvn spring-boot:run"
echo [成功] 后端服务已启动，访问地址: http://localhost:4399
goto end

:start_frontend
echo.
echo 正在启动前端服务...
cd Sxvue
start "前端服务" cmd /k "npm run dev"
echo [成功] 前端服务已启动，访问地址: http://localhost:5173
goto end

:start_both
echo.
echo 正在启动前后端服务...
echo 启动后端服务...
cd Supermarket
start "后端服务" cmd /k "mvn spring-boot:run"
timeout /t 3 /nobreak >nul
echo 启动前端服务...
cd ..\Sxvue
start "前端服务" cmd /k "npm run dev"
echo [成功] 前后端服务已启动
echo 后端地址: http://localhost:4399
echo 前端地址: http://localhost:5173
goto end

:invalid_choice
echo.
echo [错误] 无效的选择，请输入 1-4
goto end

:end
echo.
echo 按任意键退出...
pause >nul

:exit 